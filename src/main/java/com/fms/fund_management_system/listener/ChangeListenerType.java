package com.fms.fund_management_system.listener;

import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: Waqar Khan
 * To change this template use File | Settings | File Templates.
 */

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
