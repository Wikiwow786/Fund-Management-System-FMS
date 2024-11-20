package com.fms.fund_management_system.listener;


public interface PropertyChangeListener
{
    Object postChange (Object entity, Object previousValue, Object newValue, boolean update, ChangeListenerType changeListenerType);

}
