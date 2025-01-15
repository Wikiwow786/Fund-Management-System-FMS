package com.fms.service;

import com.fms.models.UnclaimedAmountModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnclaimedAmountService {
    UnclaimedAmountModel getUnclaimedAmount(Long unclaimedAmountId);

    Page<UnclaimedAmountModel> getAllUnclaimedAmounts(String search, Pageable pageable);

    UnclaimedAmountModel createOrUpdate(UnclaimedAmountModel unclaimedAmountModel, Long unclaimedAmountId, SecurityUser securityUser);
}
