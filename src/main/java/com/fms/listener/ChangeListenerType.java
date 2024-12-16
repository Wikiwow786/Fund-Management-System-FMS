package com.fms.listener;

import lombok.Data;
@Data
public final class ChangeListenerType
{
    private String listener;
    private boolean changedOnly;
    private String property;
    private String operation;
    private String symbolicText;
    private String lookup;
    private String eventCode;
    private String type;
    private String finalisationPoint;
    private String destination;
}
