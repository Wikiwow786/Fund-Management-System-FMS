package com.fms.service;

import com.fms.models.DashboardResponseModel;
import com.fms.models.RolePermissionModel;
import com.fms.models.UserModel;
import com.fms.security.SecurityUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface UserService {
    UserModel getUser(Long userId);

    Page<UserModel> getAllUsers(String search,String status,String roleName, Pageable pageable);

    UserModel createOrUpdate(UserModel userModel, Long userId, SecurityUser securityUser);

    UserModel updateLanguage(UserModel userModel,SecurityUser securityUser);

    UserModel updatePassword(UserModel userModel,SecurityUser securityUser);

    List<RolePermissionModel> getCurrentUserPermissions(Long userId);

    void setProfilePicture(String userName,SecurityUser securityUser, MultipartFile file);

    DashboardResponseModel getDashboardData(List<Long> bankIds, List<Long> customerIds);
}
