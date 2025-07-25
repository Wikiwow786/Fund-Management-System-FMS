package com.fms.security;

import com.fms.entities.User;
import com.fms.exception.FmsException;
import com.fms.repositories.RolePermissionRepository;
import com.fms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
@Service
public class UserAccountService implements UserDetailsService {

    private final UserRepository userAccountRepository;

    private final RolePermissionRepository rolePermissionRepository;

    @Autowired
    public UserAccountService(UserRepository userAccountRepository, RolePermissionRepository rolePermissionRepository) {
        this.userAccountRepository = userAccountRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    @Transactional
    @Cacheable
    public UserDetails loadUserByUsername(String username) {
        User user = validateEmailAddress(username);
          return processLogin(user);

    }

    public UserDetails loadUserById(Long userId) {
        User user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new SecurityUser(user.getUserId(), user.getName(), user.getPassword(),
                user.getStatus().equals(User.UserStatus.ACTIVE),
                true, true, true,
                rolePermissionRepository.findAllByRole(user.getRole()).stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission().getPermissionCode()))
                        .collect(Collectors.toList()),user.getLanguage());
    }



    public User validateEmailAddress(String userName){
      User user =  userAccountRepository.findByNameIgnoreCase(userName);

        if(user == null ){
            throw new FmsException("Your email/password is incorrect");

        }
        if(!user.getStatus().equals(User.UserStatus.ACTIVE)){
            throw new FmsException("The account is currently inactive,kindly activate your account and request a password reset");
        }
        return user;
    }

    private UserDetails processLogin(User user){
        return new SecurityUser(user.getUserId(), user.getName(), user.getPassword(),
                user.getStatus().equals(User.UserStatus.ACTIVE),
                true, true, true,
                rolePermissionRepository.findAllByRole(user.getRole()).stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission().getPermissionCode()))
                        .collect(Collectors.toList()),user.getLanguage());

    }

}
