package com.fms.service;

import com.fms.models.UserModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    UserModel getUser(Long userId);

    Page<UserModel> getAllUsers(String search, Pageable pageable);

    UserModel createOrUpdate(UserModel userModel, Long userId, SecurityUser securityUser);
}
