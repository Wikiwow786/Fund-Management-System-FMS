package com.fms.repositories;

import com.fms.entities.BalanceTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransferRepository extends JpaRepository<BalanceTransfer,Long> {
}
