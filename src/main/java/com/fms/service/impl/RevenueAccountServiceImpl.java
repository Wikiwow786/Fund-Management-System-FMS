
package com.fms.service.impl;

import com.fms.entities.QRevenueAccount;
import com.fms.entities.RevenueAccount;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.RevenueAccountMapper;
import com.fms.models.RevenueAccountModel;
import com.fms.repositories.RevenueAccountRepository;
import com.fms.service.RevenueAccountService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RevenueAccountServiceImpl implements RevenueAccountService {
    private final RevenueAccountRepository revenueAccountRepository;
    private final RevenueAccountMapper revenueAccountMapper;
    private final JPAQueryFactory queryFactory;

    @Override
    public RevenueAccountModel getRevenueAccount(Long revenueAccountId) {
        return new RevenueAccountModel(revenueAccountRepository.findById(revenueAccountId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<RevenueAccountModel> getAllRevenueAccounts(String revenueAccountName,
                                                           RevenueAccount.RevenueAccountStatus status,
                                                           LocalDate startDate,
                                                           LocalDate endDate,
                                                           String search,
                                                           Pageable pageable) {
        QRevenueAccount q = QRevenueAccount.revenueAccount;
        BooleanBuilder filter = new BooleanBuilder();

        if (StringUtils.isNotBlank(search)) {
            filter.and(q.name.containsIgnoreCase(search));
        }

        if (status != null) {
            filter.and(q.status.eq(status));
        }

        if (startDate != null) {
            filter.and(q.startDate.goe(startDate));
        }

        if (endDate != null) {
            filter.and(q.startDate.loe(endDate));
        }

        List<RevenueAccountModel> results = queryFactory
                .selectFrom(q)
                .where(filter)
                .orderBy(
                        new CaseBuilder()
                                .when(q.name.eq("Fee")).then(0)
                                .otherwise(1)
                                .asc(),
                        q.name.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(RevenueAccountModel::new)
                .toList();
        long total = queryFactory
                .select(q.count())
                .from(q)
                .where(filter)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }



    @Override
    public RevenueAccountModel createOrUpdate(RevenueAccountModel revenueAccountModel,Long revenueAccountId) {
        return new RevenueAccountModel(revenueAccountRepository.save(assemble(revenueAccountModel,revenueAccountId)));
    }

    public RevenueAccount assemble(RevenueAccountModel revenueAccountModel, Long revenueAccountId){

        RevenueAccount revenueAccount;

        if (null != revenueAccountId) {
            revenueAccount = revenueAccountRepository.findById(revenueAccountId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        }
        else {
            revenueAccount = new RevenueAccount();
        }
        revenueAccountMapper.toEntity(revenueAccountModel, revenueAccount);
        return revenueAccount;
    }
}

