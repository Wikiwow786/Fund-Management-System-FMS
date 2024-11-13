package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.UnclaimedAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnclaimedAmountRepository extends JpaRepository<UnclaimedAmount,Long> {
}
