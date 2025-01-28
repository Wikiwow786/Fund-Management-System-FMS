package com.fms.service.impl;

import com.fms.entities.ManualEntry;
import com.fms.entities.Transaction;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.ManualEntryMapper;
import com.fms.models.ManualEntryModel;
import com.fms.models.TransactionModel;
import com.fms.repositories.BankRepository;
import com.fms.repositories.ManualEntryRepository;
import com.fms.repositories.TransactionRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.ManualEntryService;
import com.fms.service.TransactionService;
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
public class ManualEntryServiceImpl implements ManualEntryService {
    private final ManualEntryRepository manualEntryRepository;
    private final UserRepository userRepository;
    private final ManualEntryMapper manualEntryMapper;
    private final BankRepository bankRepository;
    private final TransactionService transactionService;

    private final TransactionRepository transactionRepository;

    @Override
    public ManualEntryModel getManualEntry(Long manualEntryId) {
        return new ManualEntryModel(manualEntryRepository.findById(manualEntryId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<ManualEntryModel> getAllManualEntries(String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        return manualEntryRepository.findAll(filter, pageable).map(ManualEntryModel::new);
    }

    @Override
    public ManualEntryModel createOrUpdate(ManualEntryModel manualEntryModel, Long manualEntryId, SecurityUser securityUser) {
        return new ManualEntryModel(manualEntryRepository.save(assemble(manualEntryModel,manualEntryId,securityUser)));

    }

    public ManualEntry assemble(ManualEntryModel manualEntryModel,Long manualEntryId,SecurityUser securityUser){
        ManualEntry manualEntry;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != manualEntryId) {
            manualEntry = manualEntryRepository.findById(manualEntryId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            manualEntry.setUpdatedBy(user);
        }
        else {
            manualEntry = new ManualEntry();
            manualEntry.setCreatedBy(user);
        }
        manualEntryMapper.toEntity(manualEntryModel,manualEntry);
        if(null != manualEntryModel.getBankId()){
            manualEntry.setBank(bankRepository.findById(manualEntryModel.getBankId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
       TransactionModel transactionModel = createTransactionForManualEntry(manualEntryModel,manualEntryId,securityUser);
        if(null != transactionModel.getTransactionId()){
            manualEntry.setTransaction(transactionRepository.findById(transactionModel.getTransactionId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        }
        return manualEntry;
    }

    private TransactionModel createTransactionForManualEntry(ManualEntryModel manualEntryModel,Long manualEntryId,SecurityUser securityUser){
        TransactionModel transactionModel = buildTransactionModelForManualEntry(manualEntryModel,manualEntryId);
        return transactionService.createOrUpdate(transactionModel,null,securityUser);
    }

    private TransactionModel buildTransactionModelForManualEntry(ManualEntryModel manualEntryModel,Long manualEntryId) {
        TransactionModel transactionModel = new TransactionModel();
        if(null != manualEntryId){
           ManualEntry manualEntry = manualEntryRepository.findById(manualEntryId)
                   .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            transactionModel.setTransactionDate(new Date());
            transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));
            transactionModel.setBankId(manualEntry.getBank().getBankId());
            transactionModel.setAmount(manualEntry.getAmount());
        }
        else {
            transactionModel.setAmount(manualEntryModel.getAmount());
            transactionModel.setTransactionDate(manualEntryModel.getEntryDate());
            transactionModel.setTransactionTime(manualEntryModel.getEntryTime());
            transactionModel.setBankId(manualEntryModel.getBankId());
        }
        if(manualEntryModel.getEntryType().equals(ManualEntry.ManualEntryType.BANK_INTEREST)){
            transactionModel.setTransactionType(Transaction.TransactionType.FUND_IN);
        }
        if(manualEntryModel.getEntryType().equals(ManualEntry.ManualEntryType.EXPENSE)){
            transactionModel.setTransactionType(Transaction.TransactionType.FUND_OUT);
        }
        transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
        return transactionModel;
    }
}
