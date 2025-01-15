package com.fms.service.impl;

import com.fms.entities.Transaction;
import com.fms.entities.UnclaimedAmount;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.UnclaimedAmountMapper;
import com.fms.models.TransactionModel;
import com.fms.models.UnclaimedAmountModel;
import com.fms.repositories.*;
import com.fms.security.SecurityUser;
import com.fms.service.TransactionService;
import com.fms.service.UnclaimedAmountService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Time;
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

    @Override
    public UnclaimedAmountModel getUnclaimedAmount(Long unclaimedAmountId) {
        return new UnclaimedAmountModel(unclaimedAmountRepository.findById(unclaimedAmountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<UnclaimedAmountModel> getAllUnclaimedAmounts(String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        return unclaimedAmountRepository.findAll(filter, pageable).map(UnclaimedAmountModel::new);
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
        unclaimedAmountMapper.toEntity(unclaimedAmountModel,unclaimedAmount);
        if(null != unclaimedAmountModel.getBankId()){
            unclaimedAmount.setBank(bankRepository.findById(unclaimedAmountModel.getBankId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        if(unclaimedAmountModel.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.CLAIMED) || unclaimedAmountModel.getStatus().equals(UnclaimedAmount.UnclaimedAmountStatus.VOIDED)) {
            TransactionModel transactionModel = createTransactionForUnclaimedAmount(unclaimedAmountModel, unclaimedAmountId, securityUser);
            if (null != transactionModel.getTransactionId()) {
                unclaimedAmount.setTransaction(transactionRepository.findById(transactionModel.getTransactionId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            }
            if (null != unclaimedAmountModel.getCustomerId()) {
                unclaimedAmount.setCustomer(customerRepository.findById(unclaimedAmountModel.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
               unclaimedAmount.setClaimedBy(customerRepository.findById(unclaimedAmountModel.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            }
        }
        return unclaimedAmount;
    }

    private TransactionModel createTransactionForUnclaimedAmount(UnclaimedAmountModel unclaimedAmountModel, Long unclaimedAmountId, SecurityUser securityUser){
        TransactionModel transactionModel = buildTransactionModelForUnclaimedAmount(unclaimedAmountModel,unclaimedAmountId);
        return transactionService.createOrUpdate(transactionModel,null,securityUser);
    }

    private TransactionModel buildTransactionModelForUnclaimedAmount(UnclaimedAmountModel unclaimedAmountModel,Long unclaimedAmountId) {
        TransactionModel transactionModel = new TransactionModel();
        if(null != unclaimedAmountId) {
            UnclaimedAmount unclaimedAmount = unclaimedAmountRepository.findById(unclaimedAmountId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            transactionModel.setTransactionDate(new Date());
            transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));
            transactionModel.setBankId(unclaimedAmount.getBank().getBankId());
            transactionModel.setAmount(unclaimedAmount.getAmount());
            transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
            transactionModel.setTransactionType(Transaction.TransactionType.OTHER);
            transactionModel.setCustomerId(unclaimedAmountModel.getCustomerId());
        }
        return transactionModel;
    }
}
