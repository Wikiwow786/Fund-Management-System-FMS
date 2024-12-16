package com.fms.listener;


public interface PropertyChangeListener
{
    Object postChange (Object entity, Object previousValue, Object newValue, boolean update, ChangeListenerType changeListenerType);

}
