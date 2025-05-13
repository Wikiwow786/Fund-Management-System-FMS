package com.fms.service.impl;

import com.fms.entities.*;
import com.fms.exception.FmsException;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.ExceptionListMapper;
import com.fms.models.ExceptionListModel;
import com.fms.repositories.BankRepository;
import com.fms.repositories.ExceptionListRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.ExceptionListService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ExceptionListServiceImpl implements ExceptionListService {

    private final ExceptionListRepository exceptionListRepository;

    private final BankRepository bankRepository;

    private final UserRepository userRepository;
    private final ExceptionListMapper exceptionListMapper;
    @Override
    public void handleDailyCheckOut(ExceptionListModel exceptionListModel, SecurityUser securityUser) {
        Bank bank = bankRepository.findById(exceptionListModel.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        BigDecimal systemBalance = bank.getBalance();
        BigDecimal imbalanceAmount = systemBalance.subtract(exceptionListModel.getInputBalance()).abs();

        if (imbalanceAmount.compareTo(BigDecimal.ZERO) > 0) {
            exceptionListModel.setCause("Imbalance detected during daily check-out");
            exceptionListModel.setStatus(ExceptionList.ExceptionStatus.UNEXPLAINED);
            exceptionListModel.setImbalanceAmount(imbalanceAmount);
            createOrUpdate(exceptionListModel, null, securityUser);
            throw new FmsException("Imbalance detected. Exception created.");
        }

    }

    @Override
    public ExceptionListModel getExceptionList(Long exceptionListId) {
        return new ExceptionListModel(exceptionListRepository.findById(exceptionListId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<ExceptionListModel> getAllExceptionLists(String search,String status,String createdBy, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QExceptionList.exceptionList.bank.bankName.equalsIgnoreCase(search));

        }
        if(StringUtils.isNotBlank(status)){
            filter.and(QExceptionList.exceptionList.status.stringValue().equalsIgnoreCase(status));
        }

        if(StringUtils.isNotBlank(createdBy)){
            filter.and(QExceptionList.exceptionList.createdBy.name.equalsIgnoreCase(createdBy));

        }

        if(!ObjectUtils.isEmpty(dateFrom)){
            filter.and(QExceptionList.exceptionList.createdAt.goe(dateFrom.atStartOfDay().atOffset(ZoneOffset.UTC)));
        }

        if(!ObjectUtils.isEmpty(dateTo)){
            filter.and(QExceptionList.exceptionList.createdAt.loe(dateTo.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC)));
        }

        return exceptionListRepository.findAll(filter, pageable).map(ExceptionListModel::new);
    }

    @Override
    public ExceptionListModel createOrUpdate(ExceptionListModel exceptionListModel, Long exceptionListId, SecurityUser securityUser) {
        return exceptionListMapper.toModel(exceptionListRepository.save(assemble(exceptionListModel,exceptionListId,securityUser)));
    }

    public ExceptionList assemble(ExceptionListModel exceptionListModel,Long exceptionListId,SecurityUser securityUser){

        ExceptionList exceptionList;

        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != exceptionListId) {
            exceptionList = exceptionListRepository.findById(exceptionListId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        }
        else {
            exceptionList = new ExceptionList();
            exceptionList.setCreatedBy(user);
            exceptionList.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        }
        exceptionListMapper.toEntity(exceptionListModel,exceptionList);
        if(null != exceptionListModel.getBankId()){
            Bank bank = bankRepository.findById(exceptionListModel.getBankId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            exceptionList.setBank(bank);
            exceptionList.setSystemBalance(bank.getBalance());
        }
        return exceptionList;

    }
}
