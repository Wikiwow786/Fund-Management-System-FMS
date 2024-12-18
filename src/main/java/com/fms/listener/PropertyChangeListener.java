package com.fms.listener;


import java.time.OffsetDateTime;

public interface PropertyChangeListener
{
    void postChange (Object entity, String action, OffsetDateTime dateTime);

}
