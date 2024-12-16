package com.fms.listener;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@NoArgsConstructor
public final class ChangeListenerMapper
{
    private final List<ChangeListener> listener = new ArrayList<>();

    public List<ChangeListener> getListener() {

        return Collections.unmodifiableList(listener);
    }


}
