package com.fms.fund_management_system.listener;

import com.fms.fund_management_system.entities.ActivityLog;
import com.fms.fund_management_system.exception.ResourceNotFoundException;
import com.fms.fund_management_system.repositories.ActivityLogRepository;
import com.fms.fund_management_system.repositories.UserRepository;
import com.fms.fund_management_system.util.BeanUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component("changeAuditListener")
@RequiredArgsConstructor
public class ChangeAuditListener extends PropertyChangeListenerBase {

    protected final ActivityLogRepository activityLogRepository;
    protected final Environment environment;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object postChange(Object entity, Object previousValue, Object newValue, boolean update, ChangeListenerType changeListenerType) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setDateTime(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        activityLog.setAction(!update ? changeListenerType.getSymbolicText() : changeListenerType.getSymbolicText() + " " + (previousValue == null ? "" : previousValue) + " to " + newValue);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader("userId");
        activityLog.setUser(BeanUtil.getBean(UserRepository.class).findById(Long.valueOf(userId))
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        activityLogRepository.save(activityLog);
        return newValue;
    }
    

}
