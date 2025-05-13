package com.fms.repositories.impl;

import com.fms.entities.QUnclaimedAmount;
import com.fms.entities.UnclaimedAmount;
import com.fms.repositories.CustomUnclaimedAmountRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
@Repository
@RequiredArgsConstructor
public class CustomUnclaimedAmountRepositoryImpl implements CustomUnclaimedAmountRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public BigDecimal getTotalUnclaimedAmount() {
        QUnclaimedAmount unclaimed = QUnclaimedAmount.unclaimedAmount;

        BigDecimal totalUnclaimed = queryFactory
                .select(unclaimed.amount.sum())
                .from(unclaimed)
                .where(unclaimed.status.eq(UnclaimedAmount.UnclaimedAmountStatus.UNCLAIMED))
                .fetchOne();

        return totalUnclaimed != null ? totalUnclaimed : BigDecimal.ZERO;
    }
}
