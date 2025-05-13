package com.fms.service.impl;

import com.fms.entities.QUser;
import com.fms.entities.Role;
import com.fms.entities.User;
import com.fms.exception.FmsException;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.UserMapper;
import com.fms.models.RolePermissionModel;
import com.fms.models.UserModel;
import com.fms.repositories.RolePermissionRepository;
import com.fms.repositories.RoleRepository;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.UserService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private final RolePermissionRepository rolePermissionRepository;

    @Override
    public UserModel getUser(Long userId) {
        return new UserModel(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<UserModel> getAllUsers(String search,String status, String roleName, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();

        if(StringUtils.isNotBlank(search)){
           filter.and(QUser.user.name.equalsIgnoreCase(search));
        }
        if(StringUtils.isNotBlank(status)){
            filter.and(QUser.user.status.stringValue().equalsIgnoreCase(status));
        }
        if(StringUtils.isNotBlank(roleName)){
            filter.and(QUser.user.role.roleName.equalsIgnoreCase(roleName));
        }
        return userRepository.findAll(filter,pageable).map(UserModel::new);
    }

    @Override
    public UserModel createOrUpdate(UserModel userModel, Long userId, SecurityUser securityUser) {
        return new UserModel(userRepository.save(assemble(userModel,userId,securityUser)));
    }

    @Override
    public UserModel updateLanguage(UserModel userModel, SecurityUser securityUser) {
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        user.setLanguage(userModel.getLanguage());
       return new UserModel(userRepository.save(user));

    }

    @Override
    public UserModel updatePassword(UserModel userModel, SecurityUser securityUser) {
        User user =  userRepository.findById(securityUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            if (!validateCurrentPassword(user, userModel.getCurrentPassword())) {
                throw new FmsException("Current password is incorrect");
            }
        user.setPassword(encodePassword(userModel.getPassword()));
        return new UserModel(userRepository.save(user));
    }

    @Override
    public List<RolePermissionModel> getCurrentUserPermissions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
       return rolePermissionRepository.findAllByRole(user.getRole()).stream().map(RolePermissionModel::new).toList();
    }

    @Override
    public void setProfilePicture(String userName,SecurityUser securityUser, MultipartFile file) {
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
        if(StringUtils.isNotBlank(userName)){
            user.setName(userName);
        }

        try {
            user.setProfilePicture(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading profile picture", e);
        }
        userRepository.save(user);
    }


    public User assemble(UserModel userModel, Long userId, SecurityUser securityUser){

        User user;

        User loginUser = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

      /*  if (null != userRepository.findUserByEmailIgnoreCase(userModel.getEmail())) {
            throw new FmsException("Email already exists");
        }*/

        if (null != userId) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));
            user.setUpdatedBy(loginUser);
            user.setRole(roleRepository.findById(userModel.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
            userMapper.toEntity(userModel, user);
            if (StringUtils.isNotBlank(userModel.getPassword()) && !validateCurrentPassword(user, userModel.getPassword())) {
                    user.setPassword(encodePassword(userModel.getPassword()));
            }
        }
        else {
            user = new User();
            user.setCreatedBy(loginUser);
            userMapper.toEntity(userModel, user);
            Optional<Role> role = roleRepository.findById(userModel.getRoleId());
            role.ifPresent(user::setRole);
            user.setPassword(encodePassword(userModel.getPassword()));
        }

        return user;
    }

    private String encodePassword(String password) {
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder.encode(password);
    }

    private boolean validateCurrentPassword(User user, String password){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder.matches(password, user.getPassword());

    }
}
