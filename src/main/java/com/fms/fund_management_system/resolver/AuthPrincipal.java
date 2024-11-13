package com.fms.fund_management_system.resolver;



import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPrincipal
{
    String userId() default "userId";
    String email() default "email";
}
