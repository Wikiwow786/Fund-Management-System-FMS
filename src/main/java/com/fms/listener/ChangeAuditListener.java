package com.fms.listener;

import com.fms.entities.ActivityLog;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.repositories.ActivityLogRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component("changeAuditListener")
@RequiredArgsConstructor
public class ChangeAuditListener extends PropertyChangeListenerBase {

    protected final ActivityLogRepository activityLogRepository;
    protected final Environment environment;
    private final UserRepository userRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object postChange(Object entity, Object previousValue, Object newValue, boolean update, ChangeListenerType changeListenerType) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setDateTime(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        activityLog.setAction(!update ? changeListenerType.getSymbolicText() : changeListenerType.getSymbolicText() + " " + (previousValue == null ? "" : previousValue) + " to " + newValue);
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityLog.setUser(userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        activityLogRepository.save(activityLog);
        return newValue;
    }
    

}
