package com.fms.fund_management_system.listener;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Waqar Khan
 * To change this template use File | Settings | File Templates.
 */

@NoArgsConstructor
public final class ChangeListenerMapper
{
    private final List<ChangeListener> listener = new ArrayList<>();

    public List<ChangeListener> getListener() {

        return Collections.unmodifiableList(listener);
    }


}
