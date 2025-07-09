package com.fms.service.impl;

import com.fms.entities.*;
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
    public Page<ManualEntryModel> getAllManualEntries(String search, String entryType,String createdBy, Date dateFrom, Date dateTo, LocalTime timeFrom, LocalTime timeTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QManualEntry.manualEntry.bank.bankName.containsIgnoreCase(search));
        }
        if(StringUtils.isNotBlank(createdBy)){
            filter.and(QManualEntry.manualEntry.createdBy.name.containsIgnoreCase(createdBy));
        }

        if(entryType != null){
            filter.and(QManualEntry.manualEntry.entryType.stringValue().containsIgnoreCase(entryType));
        }
        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QManualEntry.manualEntry.entryDate.goe(dateFrom));
        }
        if(!ObjectUtils.isEmpty(dateTo)){
            filter.and(QManualEntry.manualEntry.entryDate.loe(dateTo));

        }
        if (!ObjectUtils.isEmpty(timeFrom)) {
           filter.and(QManualEntry.manualEntry.entryTime.after(Time.valueOf(timeFrom)));
        }
        if(!ObjectUtils.isEmpty(timeTo)){
           filter.and(QManualEntry.manualEntry.entryTime.before(Time.valueOf(timeTo)));

        }

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
            if(manualEntryModel.getStatus().equals(ManualEntry.ManualEntryStatus.VOIDED)){
                processVoidedManualEntry(manualEntry,manualEntryModel,securityUser);
            }
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
        if(manualEntryModel.getEntryType() != null) {
            TransactionModel transactionModel = createTransactionForManualEntry(manualEntryModel, manualEntryId, securityUser);
            if(null != transactionModel.getTransactionId()){
                manualEntry.setTransaction(transactionRepository.findById(transactionModel.getTransactionId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            }
        }
        return manualEntry;
    }

    private TransactionModel createTransactionForManualEntry(ManualEntryModel manualEntryModel,Long manualEntryId,SecurityUser securityUser){
        TransactionModel transactionModel = buildTransactionModelForManualEntry(manualEntryModel,manualEntryId);
        return transactionService.createOrUpdate(transactionModel,null,false,securityUser);
    }

    private TransactionModel buildTransactionModelForManualEntry(ManualEntryModel manualEntryModel, Long manualEntryId) {
        TransactionModel transactionModel = new TransactionModel();
        BigDecimal amount;

        if (manualEntryId != null) {
            ManualEntry manualEntry = manualEntryRepository.findById(manualEntryId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            amount = manualEntry.getAmount();
            transactionModel.setTransactionDate(new Date());
            transactionModel.setTransactionTime(new Time(System.currentTimeMillis()));
            transactionModel.setBankId(manualEntry.getBank().getBankId());
        } else {
            amount = manualEntryModel.getAmount();
            transactionModel.setTransactionDate(manualEntryModel.getEntryDate());
            transactionModel.setTransactionTime(manualEntryModel.getEntryTime());
            transactionModel.setBankId(manualEntryModel.getBankId());
        }

        ManualEntry.ManualEntryType entryType = manualEntryModel.getEntryType();

        if (entryType == ManualEntry.ManualEntryType.bank_interest) {
            transactionModel.setTransactionType(Transaction.TransactionType.FUND_OUT);
        } else if (entryType == ManualEntry.ManualEntryType.expense) {
            transactionModel.setTransactionType(Transaction.TransactionType.FUND_OUT);
        } else if (entryType == ManualEntry.ManualEntryType.others) {
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                transactionModel.setTransactionType(Transaction.TransactionType.FUND_IN);
            } else {
                transactionModel.setTransactionType(Transaction.TransactionType.FUND_OUT);
            }
        } else {
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                transactionModel.setTransactionType(Transaction.TransactionType.FUND_OUT);
            } else {
                transactionModel.setTransactionType(Transaction.TransactionType.FUND_IN);
            }
        }
        transactionModel.setAmount(amount.abs());
        transactionModel.setStatus(Transaction.TransactionStatus.COMPLETED);
        return transactionModel;
    }



    private void processVoidedManualEntry(ManualEntry manualEntry, ManualEntryModel manualEntryModel, SecurityUser securityUser){
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setStatus(Transaction.TransactionStatus.VOIDED);
        transactionService.createOrUpdate(transactionModel,manualEntry.getTransaction().getTransactionId(), false,securityUser);

    }

}
