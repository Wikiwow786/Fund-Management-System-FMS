package com.fms.listener;


import java.time.OffsetDateTime;

public interface DbChangeHandler
{
    void postChange (Object entity, String action, OffsetDateTime dateTime);

}
