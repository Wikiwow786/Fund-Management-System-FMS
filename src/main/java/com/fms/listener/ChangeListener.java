package com.fms.listener;

import lombok.Data;

import java.util.Collections;
import java.util.List;

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
