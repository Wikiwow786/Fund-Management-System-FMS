package com.fms.listener;

public abstract class PropertyChangeListenerBase implements PropertyChangeListener
{
    @Override
    public Object postChange(Object entity, Object previousValue, Object newValue, boolean update, ChangeListenerType changeListenerType) {
        return newValue;
    }

}
