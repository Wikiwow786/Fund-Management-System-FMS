
package com.fms.service.impl;

import com.fms.entities.Bank;
import com.fms.entities.QBank;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.BankMapper;
import com.fms.models.BankModel;
import com.fms.repositories.BankRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.BankService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final UserRepository userRepository;
    private final BankMapper bankMapper;

    @Override
    public BankModel getBank(Long bankId) {
        return new BankModel(bankRepository.findById(bankId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<BankModel> getAllBanks(String bankName, Bank.BankStatus status, LocalDate startDate, LocalDate endDate, String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();

        if(StringUtils.isNotBlank(search)){
            BooleanExpression searchCondition =
                    QBank.bank.bankName.containsIgnoreCase(search)
                            .or(QBank.bank.status.stringValue().equalsIgnoreCase(search));
            filter.and(searchCondition);
        }
        if(status != null){
            filter.and(QBank.bank.status.eq(status));

        }
        if(startDate != null){
            filter.and(QBank.bank.createdAt.goe(startDate.atStartOfDay().atOffset(ZoneOffset.UTC)));
        }
        if(endDate != null){
            filter.and(QBank.bank.createdAt.loe(endDate.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC)));
        }
        return bankRepository.findAll(filter, pageable).map(BankModel::new);
    }

    @Override
    public BankModel createOrUpdate(BankModel bankModel, Long bankId, SecurityUser securityUser) {
       return new BankModel(bankRepository.save(assemble(bankModel,bankId,securityUser)));

    }

    public Bank assemble(BankModel bankModel,Long bankId,SecurityUser securityUser){
        Bank bank;
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != bankId) {
            bank = bankRepository.findById(bankId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            bank.setUpdatedBy(user);
        }
        else {
            bank = new Bank();
            bank.setCreatedBy(user);
        }
       bankMapper.toEntity(bankModel,bank);
        return bank;
    }
}
