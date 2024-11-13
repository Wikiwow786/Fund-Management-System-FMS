package com.fms.fund_management_system.resolver;

import com.fms.fund_management_system.models.AuthModel;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import java.util.Objects;

public class AuthPrincipalResolver implements HandlerMethodArgumentResolver {

    public static final String USER_KEY = "X-USER-ID";
    public static final String EMAIL_KEY = "X-EMAIL";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthPrincipal.class) != null;
    }
    
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
        throws Exception {

        AuthPrincipal annotation = parameter.getParameterAnnotation(AuthPrincipal.class);
        AuthModel authModel = new AuthModel();
        String userId = null;
        String email = null;

        if (StringUtils.hasText(webRequest.getParameter(annotation.userId()))) {
            userId = webRequest.getParameter(annotation.userId());
        } else if (StringUtils.hasText(webRequest.getHeader(annotation.userId()))) {
            userId = webRequest.getHeader(annotation.userId());
        } else if (StringUtils.hasText(webRequest.getParameter(USER_KEY))) {
            userId = webRequest.getParameter(annotation.userId());
        } else if (StringUtils.hasText(webRequest.getHeader(USER_KEY))) {
            userId = webRequest.getHeader(annotation.userId());
        }

        if (StringUtils.hasText(webRequest.getParameter(annotation.email()))) {
            email = webRequest.getParameter(annotation.email());
        } else if (StringUtils.hasText(webRequest.getHeader(annotation.email()))) {
            email = webRequest.getHeader(annotation.email());
        } else if (StringUtils.hasText(webRequest.getParameter(EMAIL_KEY))) {
            email = webRequest.getParameter(annotation.email());
        } else if (StringUtils.hasText(webRequest.getHeader(EMAIL_KEY))) {
            email = webRequest.getHeader(annotation.email());
        }

        authModel.setUserId(Objects.nonNull(userId) ? Long.parseLong(userId) : null);
        authModel.setEmail(Objects.nonNull(email) ? email : null);
        return authModel;
    }
    
}
