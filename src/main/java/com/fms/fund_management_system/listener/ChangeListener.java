package com.fms.fund_management_system.listener;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Waqar Khan
 * To change this template use File | Settings | File Templates.
 */

@Data
final class ChangeListener
{
    private  String entityClass;
    private  List<ChangeListenerType> fields;

    ChangeListener()
    {
    }

    String getEntityClass()
    {
        return entityClass;
    }

    public List<ChangeListenerType> getFields() {
        return Collections.unmodifiableList(fields);
    }
}
