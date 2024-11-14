package com.fms.fund_management_system.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthModel {
    private Long userId;
    private String email;
}
