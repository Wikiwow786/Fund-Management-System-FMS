package com.fms.service.impl;

import com.fms.entities.QUser;
import com.fms.entities.User;
import com.fms.exception.ResourceNotFoundException;
import com.fms.mapper.UserMapper;
import com.fms.models.UserModel;
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

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;


    @Override
    public UserModel getUser(Long userId) {
        return new UserModel(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase())));
    }

    @Override
    public Page<UserModel> getAllUsers(String search, Pageable pageable) {
        BooleanBuilder filter = new BooleanBuilder();
        if(StringUtils.isNotBlank(search)){
            filter.and(QUser.user.name.containsIgnoreCase(search))
                    .or(QUser.user.role.roleName.containsIgnoreCase(search));
        }
        return userRepository.findAll(filter,pageable).map(UserModel::new);
    }

    @Override
    public UserModel createOrUpdate(UserModel userModel, Long userId, SecurityUser securityUser) {
        return new UserModel(userRepository.save(assemble(userModel,userId,securityUser)));
    }


    public User assemble(UserModel userModel, Long userId, SecurityUser securityUser){

        User user;

        User loginUser = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND.getReasonPhrase()));

        if (null != userRepository.findUserByEmailIgnoreCase(userModel.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

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
        return passwordEncoder.matches(user.getPassword(), password);

    }
}
