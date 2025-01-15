package com.fms.service;

import com.fms.models.ExceptionListModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ExceptionListService {

    void handleDailyCheckOut(ExceptionListModel exceptionListModel, SecurityUser securityUser);

    ExceptionListModel getExceptionList(Long exceptionListId);

    Page<ExceptionListModel> getAllExceptionLists(String search, LocalDate dateFrom, LocalDate dateTo , Pageable pageable);

    ExceptionListModel createOrUpdate(ExceptionListModel exceptionListModel, Long exceptionListId, SecurityUser securityUser);
}
