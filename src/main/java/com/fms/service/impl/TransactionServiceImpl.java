package com.fms.service.impl;

import com.fms.entities.*;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.TransactionMapper;
import com.fms.models.TransactionModel;
import com.fms.repositories.*;
import com.fms.security.SecurityUser;
import com.fms.service.TransactionService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final RevenueAccountRepository revenueAccountRepository;

    @Override
    public TransactionModel getTransaction(Long transactionId) {
        return new TransactionModel(transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<TransactionModel> getAllTransactions(String search, String customerName, String bankName, Transaction.TransactionType transactionType, Long transactionId, BigDecimal amount, Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QTransaction.transaction.createdBy.name.equalsIgnoreCase(search));
        }

        if(StringUtils.isNotBlank(customerName)){
            filter.and(QTransaction.transaction.customer.customerName.equalsIgnoreCase(customerName));
        }

        if (StringUtils.isNotBlank(bankName)){
            filter.and(QTransaction.transaction.bank.bankName.equalsIgnoreCase(bankName));
        }
        if(null != transactionType){
            filter.and(QTransaction.transaction.transactionType.eq(transactionType));
        }
        if(null != amount){
            filter.and(QTransaction.transaction.amount.gt(amount));
        }
        if(null != transactionId){
            filter.and(QTransaction.transaction.transactionId.eq(transactionId));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QTransaction.transaction.transactionDate.goe(dateFrom));
        }
        if(!ObjectUtils.isEmpty(dateTo)){
            filter.and(QTransaction.transaction.transactionDate.loe(dateTo));

        }
        if(!ObjectUtils.isEmpty(timeFrom) && !ObjectUtils.isEmpty(timeTo)){
            filter.and(QTransaction.transaction.transactionTime.after(Time.valueOf(timeFrom)))
                    .and(QTransaction.transaction.transactionTime.before(Time.valueOf(timeTo)));
        }
        return transactionRepository.findAll(filter, pageable).map(TransactionModel::new);
    }

    @Override
    public TransactionModel createOrUpdate(TransactionModel transactionModel, Long transactionId,boolean isStandard, SecurityUser securityUser) {
        return new TransactionModel(transactionRepository.save(assemble(transactionModel,transactionId,isStandard,securityUser)));
    }

    public Transaction assemble(TransactionModel transactionModel, Long transactionId, boolean isStandard, SecurityUser securityUser) {
        Transaction transaction;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (transactionId != null) {
            transaction = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

            if (Transaction.TransactionStatus.VOIDED.equals(transactionModel.getStatus())) {
                processVoidedTransactions(transaction, transactionModel);
            }

            transaction.setUpdatedBy(user);
        } else {
            transaction = new Transaction();
            transaction.setCreatedBy(user);
        }
        transactionMapper.toEntity(transactionModel, transaction);

        if (transactionModel.getBankId() != null) {
            Bank bank = bankRepository.findById(transactionModel.getBankId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            transaction.setBank(bank);
            handleBankBalance(transactionModel,bank);
        }
        if (transactionModel.getCustomerId() != null) {
            Customer customer = customerRepository.findById(transactionModel.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            transaction.setCustomer(customer);
            handleCustomerBalance(transactionModel,customer,isStandard);
        }

        return transaction;
    }


    public void processFundInForCustomer(Customer customer, TransactionModel transactionModel) {
        RevenueAccount feeAccount = revenueAccountRepository.findByName("Fee");

        RevenueAccount revenueAccount = customer.getRevenueAccount();
        if (revenueAccount == null) {
            throw new ResourceNotFoundException("Customer is not tied to a revenue account");
        }

        Double feePct = customer.getFundInFeePct();
        Double commissionPct = customer.getFundInCommissionPct();

        BigDecimal amount = transactionModel.getAmount();
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(feePct / 100));
        BigDecimal commission = amount.multiply(BigDecimal.valueOf(commissionPct / 100));
        BigDecimal netToCustomer = amount.subtract(fee).subtract(commission);

        customer.setBalance(customer.getBalance().add(netToCustomer));
        feeAccount.setBalance(feeAccount.getBalance().add(fee));
        revenueAccount.setBalance(revenueAccount.getBalance().add(commission));

        customerRepository.save(customer);
        revenueAccountRepository.save(feeAccount);
        revenueAccountRepository.save(revenueAccount);
    }



    private void processFundOutForCustomer(Customer customer, TransactionModel transactionModel) {
        RevenueAccount feeAccount = revenueAccountRepository.findByName("Fee");

        RevenueAccount revenueAccount = customer.getRevenueAccount();
        if (revenueAccount == null) {
            throw new ResourceNotFoundException("Customer is not tied to a revenue account");
        }

        Double feePct = customer.getFundOutFeePct();
        Double commissionPct = customer.getFundOutCommissionPct();

        BigDecimal amount = transactionModel.getAmount();
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(feePct / 100));
        BigDecimal commission = amount.multiply(BigDecimal.valueOf(commissionPct / 100));
        BigDecimal totalDeduct = amount.add(fee).add(commission);
        customer.setBalance(customer.getBalance().subtract(totalDeduct));
        feeAccount.setBalance(feeAccount.getBalance().add(fee));
        revenueAccount.setBalance(revenueAccount.getBalance().add(commission));

        customerRepository.save(customer);
        revenueAccountRepository.save(feeAccount);
        revenueAccountRepository.save(revenueAccount);
    }




    private void processVoidedTransactions(Transaction transaction, TransactionModel transactionModel) {
        if (!Transaction.TransactionStatus.VOIDED.equals(transactionModel.getStatus())) {
            return;
        }

        BigDecimal amount = transaction.getAmount();
        Transaction.TransactionType type = transaction.getTransactionType();
        Bank bank = transaction.getBank();
        Customer customer = transaction.getCustomer();

        if (bank != null) {
            if (type == Transaction.TransactionType.FUND_IN) {
                bank.setBalance(bank.getBalance().subtract(amount));
            } else if (type == Transaction.TransactionType.FUND_OUT) {
                bank.setBalance(bank.getBalance().add(amount));
            }
            bankRepository.save(bank);
        }

        if (customer != null) {
            RevenueAccount revenueAccount = customer.getRevenueAccount();
            RevenueAccount feeAccount = revenueAccountRepository.findByName("Fee");

            if (revenueAccount == null || feeAccount == null) {
                throw new ResourceNotFoundException("Revenue or Fee account not found during void");
            }

            Double feePct = (type == Transaction.TransactionType.FUND_IN)
                    ? customer.getFundInFeePct()
                    : customer.getFundOutFeePct();
            Double commissionPct = (type == Transaction.TransactionType.FUND_IN)
                    ? customer.getFundInCommissionPct()
                    : customer.getFundOutCommissionPct();

            BigDecimal fee = amount.multiply(BigDecimal.valueOf(feePct / 100));
            BigDecimal commission = amount.multiply(BigDecimal.valueOf(commissionPct / 100));

            if (type == Transaction.TransactionType.FUND_IN) {
                BigDecimal netToCustomer = amount.subtract(fee).subtract(commission);
                customer.setBalance(customer.getBalance().subtract(netToCustomer));
                feeAccount.setBalance(feeAccount.getBalance().subtract(fee));
                revenueAccount.setBalance(revenueAccount.getBalance().subtract(commission));
            } else if (type == Transaction.TransactionType.FUND_OUT) {
                BigDecimal totalDeducted = amount.add(fee).add(commission);
                customer.setBalance(customer.getBalance().add(totalDeducted));
                feeAccount.setBalance(feeAccount.getBalance().subtract(fee));
                revenueAccount.setBalance(revenueAccount.getBalance().subtract(commission));
            }

            customerRepository.save(customer);
            revenueAccountRepository.save(feeAccount);
            revenueAccountRepository.save(revenueAccount);
        }
    }


    private void handleBankBalance(TransactionModel transactionModel, Bank bank) {
        if (transactionModel.getTransactionType().equals(Transaction.TransactionType.FUND_IN)) {
            bank.setBalance(bank.getBalance().add(transactionModel.getAmount()));
        } else if (transactionModel.getTransactionType().equals(Transaction.TransactionType.FUND_OUT)) {
            bank.setBalance(bank.getBalance().subtract(transactionModel.getAmount()));
        }
        bankRepository.save(bank);
    }

    private void handleCustomerBalance(TransactionModel transactionModel, Customer customer, boolean isStandard) {
        if (transactionModel.getTransactionType() == Transaction.TransactionType.FUND_IN) {
            if (isStandard) {
                processFundInForCustomer(customer, transactionModel);
            } else {
                customer.setBalance(customer.getBalance().add(transactionModel.getAmount()));
                customerRepository.save(customer);
            }
        } else if (transactionModel.getTransactionType() == Transaction.TransactionType.FUND_OUT) {
            if (isStandard) {
                processFundOutForCustomer(customer, transactionModel);
            } else {
                customer.setBalance(customer.getBalance().subtract(transactionModel.getAmount()));
                customerRepository.save(customer);
            }
        }

    }

}


