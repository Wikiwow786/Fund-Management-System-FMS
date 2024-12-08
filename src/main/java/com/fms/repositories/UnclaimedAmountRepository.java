package com.fms.repositories;

import com.fms.entities.UnclaimedAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnclaimedAmountRepository extends JpaRepository<UnclaimedAmount,Long> {
}
