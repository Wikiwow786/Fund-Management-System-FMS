package com.fms.fund_management_system.listener;

/**
 * Created by IntelliJ IDEA.
 * User: Waqar Khan
 * To change this template use File | Settings | File Templates.
 */


public abstract class PropertyChangeListenerBase implements PropertyChangeListener
{
    @Override
    public Object postChange(Object entity, Object previousValue, Object newValue, boolean update, ChangeListenerType changeListenerType) {
        return newValue;
    }

}
