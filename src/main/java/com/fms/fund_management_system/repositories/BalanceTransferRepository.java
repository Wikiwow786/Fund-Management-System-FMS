package com.fms.fund_management_system.repositories;

import com.fms.fund_management_system.entities.BalanceTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransferRepository extends JpaRepository<BalanceTransfer,Long> {
}
