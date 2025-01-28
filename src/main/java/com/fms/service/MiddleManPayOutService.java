package com.fms.service;

import com.fms.models.MiddleManPayOutModel;
import com.fms.security.SecurityUser;

public interface MiddleManPayOutService {

    void handleMiddleManPayOut(MiddleManPayOutModel middleManPayOutModel, SecurityUser securityUser);
}
