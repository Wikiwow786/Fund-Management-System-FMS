package com.fms.service.impl;

import com.fms.entities.*;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.UnclaimedAmountMapper;
import com.fms.models.TotalUnclaimedAmountModel;
import com.fms.models.TransactionModel;
import com.fms.models.UnclaimedAmountModel;
import com.fms.repositories.*;
import com.fms.security.SecurityUser;
import com.fms.service.TransactionService;
import com.fms.service.UnclaimedAmountService;
import com.querydsl.core.BooleanBuilder;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
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
public class UnclaimedAmountServiceImpl implements UnclaimedAmountService {

    private final UnclaimedAmountRepository unclaimedAmountRepository;
    private final UserRepository userRepository;
    private final UnclaimedAmountMapper unclaimedAmountMapper;
    private final BankRepository bankRepository;

    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final CustomerRepository customerRepository;

    private final RevenueAccountRepository revenueAccountRepository;

    @Override
    public UnclaimedAmountModel getUnclaimedAmount(Long unclaimedAmountId) {
        return new UnclaimedAmountModel(unclaimedAmountRepository.findById(unclaimedAmountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public TotalUnclaimedAmountModel getAllUnclaimedAmounts(
            String search, BigDecimal amount, Date dateFrom, Date dateTo,
            LocalTime timeFrom, LocalTime timeTo, String status,
            String createdBy, String updatedBy, Pageable pageable) {
        BigDecimal totalUnclaimedAmount = unclaimedAmountRepository.getTotalUnclaimedAmount();
        BooleanBuilder filter = new BooleanBuilder();

        if (StringUtils.isNotBlank(search)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.bank.bankName.containsIgnoreCase(search));
        }

        if (amount != null) {
            filter.and(QUnclaimedAmount.unclaimedAmount.amount.eq(amount));
        }

        if (!ObjectUtils.isEmpty(dateFrom)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.transactionDate.goe(dateFrom));
        }
        if (!ObjectUtils.isEmpty(dateTo)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.transactionDate.loe(dateTo));
        }

        if (!ObjectUtils.isEmpty(timeFrom) && !ObjectUtils.isEmpty(timeTo)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.transactionTime.goe(Time.valueOf(timeFrom))
                    .and(QUnclaimedAmount.unclaimedAmount.transactionTime.loe(Time.valueOf(timeTo))));
        }

        if (StringUtils.isNotBlank(status)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.status.stringValue().equalsIgnoreCase(status));
        }

        if (StringUtils.isNotBlank(createdBy)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.createdBy.name.containsIgnoreCase(createdBy));
        }

        if (StringUtils.isNotBlank(updatedBy)) {
            filter.and(QUnclaimedAmount.unclaimedAmount.updatedBy.name.containsIgnoreCase(updatedBy));
        }


        Page<UnclaimedAmountModel> unclaimedAmounts = unclaimedAmountRepository.findAll(filter, pageable)
                .map(UnclaimedAmountModel::new);
        TotalUnclaimedAmountModel response = new TotalUnclaimedAmountModel();
        response.setUnclaimedAmounts(unclaimedAmounts);
        response.setTotalUnclaimedAmount(totalUnclaimedAmount);

        return response;
    }


    @Override
    public UnclaimedAmountModel createOrUpdate(UnclaimedAmountModel unclaimedAmountModel, Long unclaimedAmountId, SecurityUser securityUser) {
        return unclaimedAmountMapper.toModel((unclaimedAmountRepository.save(assemble(unclaimedAmountModel,unclaimedAmountId,securityUser))));
    }

    public UnclaimedAmount assemble(UnclaimedAmountModel unclaimedAmountModel, Long unclaimedAmountId, SecurityUser securityUser){
        UnclaimedAmount unclaimedAmount;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != unclaimedAmountId) {
            unclaimedAmount = unclaimedAmountRepository.findById(unclaimedAmountId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            unclaimedAmount.setUpdatedBy(user);
        }
        else {
            unclaimedAmount = new UnclaimedAmount();
            unclaimedAmount.setCreatedBy(user);
        }
        //unclaimedAmountMapper.toEntity(unclaimedAmountModel,unclaimedAmount);
        if(null != unclaimedAmountModel.getBankId()){
            unclaimedAmount.setBank(bankRepository.findById(unclaimedAmountModel.getBankId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
            TransactionModel transactionModel = createTransactionForUnclaimedAmount(unclaimedAmountModel, unclaimedAmountId, securityUser);
            if (null != transactionModel && null != transactionModel.getTransactionId()) {
                unclaimedAmount.setTransaction(transactionRepository.findById(transactionModel.getTransactionId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            }
            if (null != unclaimedAmountModel.getCustomerId()) {
                unclaimedAmount.setCustomer(customerRepository.findById(unclaimedAmountModel.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
               unclaimedAmount.setClaimedBy(customerRepository.findById(unclaimedAmountModel.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            }
        unclaimedAmountMapper.toEntity(unclaimedAmountModel,unclaimedAmount);
        return unclaimedAmount;
    }

    private TransactionModel createTransactionForUnclaimedAmount(UnclaimedAmountModel unclaimedAmountModel, Long unclaimedAmountId, SecurityUser securityUser){
        TransactionModel transactionModel = buildTransactionModelForUnclaimedAmount(unclaimedAmountModel,unclaimedAmountId,securityUser);
        if(!unclaimedAmountModel.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.CLAIMED)) {
            return transactionService.createOrUpdate(transactionModel, null, false,securityUser);
        }
        return null;
    }

    private TransactionModel buildTransactionModelForUnclaimedAmount(UnclaimedAmountModel unclaimedAmountModel,Long unclaimedAmountId,SecurityUser securityUser) {
        TransactionModel transactionModel = new TransactionModel();
        if(null != unclaimedAmountId) {
            updateUnclaimedAmount(unclaimedAmountId,unclaimedAmountModel,transactionModel,securityUser);
        }
        else{
            transactionModel.setTransactionDate(new Date());
            transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));
            transactionModel.setBankId(unclaimedAmountModel.getBankId());
            transactionModel.setAmount(unclaimedAmountModel.getAmount());
            transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
            if(unclaimedAmountModel.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.UNCLAIMED)) {
                transactionModel.setTransactionType(Transaction.TransactionType.FUND_IN);
            }
           }

        return transactionModel;
    }

    private void updateUnclaimedAmount(Long unclaimedAmountId,UnclaimedAmountModel unclaimedAmountModel,TransactionModel transactionModel,SecurityUser securityUser){
        UnclaimedAmount unclaimedAmount = unclaimedAmountRepository.findById(unclaimedAmountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        transactionModel.setTransactionDate(new Date());
        transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));
        transactionModel.setBankId(unclaimedAmount.getBank().getBankId());
        transactionModel.setAmount(unclaimedAmount.getAmount());
        transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
        if(unclaimedAmountModel.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.CLAIMED) || unclaimedAmountModel.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.VOIDED)) {
            transactionModel.setTransactionType(Transaction.TransactionType.FUND_OUT);
            processCustomerClaimedAmount(unclaimedAmountModel.getStatus(),unclaimedAmount,unclaimedAmountModel,securityUser);
        }
    }

    private void processCustomerClaimedAmount(UnclaimedAmount.UnclaimedAmountStatus status, UnclaimedAmount unclaimedAmount,UnclaimedAmountModel unclaimedAmountModel, SecurityUser securityUser) {

        Customer customer = null;

        if(unclaimedAmount.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.UNCLAIMED) && status == UnclaimedAmount.UnclaimedAmountStatus.VOIDED){
            return;
        }

        if (status == UnclaimedAmount.UnclaimedAmountStatus.CLAIMED ||
                status == UnclaimedAmount.UnclaimedAmountStatus.VOIDED) {

            Long customerId = status == UnclaimedAmount.UnclaimedAmountStatus.CLAIMED
                    ? unclaimedAmountModel.getCustomerId()
                    : unclaimedAmount.getCustomer().getCustomerId();

            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        }

        RevenueAccount feeAccount = revenueAccountRepository.findByName("Fee");
        RevenueAccount revenueAccount = null != customer ? customer.getRevenueAccount() : null;

        if (revenueAccount == null) {
            throw new ResourceNotFoundException("Customer is not tied to a revenue account");
        }

        BigDecimal amount = unclaimedAmount.getAmount();
        Double feePct = customer.getFundInFeePct();
        Double commissionPct = customer.getFundInCommissionPct();

        BigDecimal fee = amount.multiply(BigDecimal.valueOf(feePct / 100));
        BigDecimal commission = amount.multiply(BigDecimal.valueOf(commissionPct / 100));
        BigDecimal netToCustomer = amount.subtract(fee).subtract(commission);

        if (status.equals(UnclaimedAmount.UnclaimedAmountStatus.CLAIMED)) {
            customer.setBalance(customer.getBalance().add(netToCustomer));
            feeAccount.setBalance(feeAccount.getBalance().add(fee));
            revenueAccount.setBalance(revenueAccount.getBalance().add(commission));

            Transaction transaction = new Transaction();
            transaction.setCreatedBy(userRepository.findById(securityUser.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            transaction.setTransactionDate(new Date());
            transaction.setTransactionTime(new Time(System.currentTimeMillis()));
            transaction.setAmount(amount);
            transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
            transaction.setCustomer(customer);
            transaction.setTransactionType(Transaction.TransactionType.FUND_IN);
            transactionRepository.save(transaction);
        }
        if (status.equals(UnclaimedAmount.UnclaimedAmountStatus.VOIDED)) {
            customer.setBalance(customer.getBalance().subtract(netToCustomer));
            feeAccount.setBalance(feeAccount.getBalance().subtract(fee));
            revenueAccount.setBalance(revenueAccount.getBalance().subtract(commission));
        }
        customerRepository.save(customer);
        revenueAccountRepository.save(feeAccount);
        revenueAccountRepository.save(revenueAccount);
    }



}
