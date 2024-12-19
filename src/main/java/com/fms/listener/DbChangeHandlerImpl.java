package com.fms.listener;

import com.fms.entities.ActivityLog;
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

@Component("dbChangeHandler")
@RequiredArgsConstructor
public class DbChangeHandlerImpl implements DbChangeHandler {

    protected final ActivityLogRepository activityLogRepository;
    protected final Environment environment;
    private final UserRepository userRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void postChange(Object entity, String action, OffsetDateTime dateTim) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setAction(generateActionMessage(entity,action));
        activityLog.setDateTime(OffsetDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()));
        SecurityUser user = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityLog.setUser(userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
        activityLogRepository.save(activityLog);
    }

    public String generateActionMessage(Object entity, String action){
        String entityName = entity.getClass().getSimpleName();

        switch (action) {
            case "INSERT":
                return entityName + " has been created";

            case "UPDATE":
                return entityName + " has been updated";

            default:
                return action + " performed on " + entityName;
        }


    }
    

}
