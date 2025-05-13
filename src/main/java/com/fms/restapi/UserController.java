package com.fms.restapi;


import com.fms.entities.User;
import com.fms.models.RolePermissionModel;
import com.fms.models.UserModel;
import com.fms.repositories.UserRepository;
import com.fms.security.SecurityUser;
import com.fms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserModel>> fetchAll(@RequestParam(required = false) String search,@RequestParam(required = false) String status,
                                                    @RequestParam(required = false) String roleName,
                                                    Pageable pageable) {

        return ResponseEntity.ok(userService.getAllUsers(search,status,roleName, pageable));
    }

    @GetMapping(value = "/user-permissions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RolePermissionModel>> fetchCurrentUserPermissions(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(userService.getCurrentUserPermissions(user.getUserId()));
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> fetch(@PathVariable(value = "userId") final Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> save(@RequestBody UserModel userModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(userService.createOrUpdate(userModel, null, securityUser));
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> update(@PathVariable(value = "userId") final Long userId,
                                            @RequestBody UserModel userModel,
                                            @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(userService.createOrUpdate(userModel, userId, securityUser));
    }

    @PatchMapping(value = "/language", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> updateLanguage(@RequestBody UserModel userModel,
                                            @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(userService.updateLanguage(userModel, securityUser));
    }

    @PatchMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserModel> updatePassword(@RequestBody UserModel userModel,
                                                    @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(userService.updatePassword(userModel, securityUser));
    }

    @PutMapping(value = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> setProfilePicture(@RequestParam(required = false)String userName, @RequestParam("file") MultipartFile file, @AuthenticationPrincipal SecurityUser securityUser) {
        try {
            userService.setProfilePicture(userName,securityUser, file);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/profile-picture", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getProfilePicture(@AuthenticationPrincipal SecurityUser securityUser) {
        User user = userRepository.findById(securityUser.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        byte[] image = user.getProfilePicture();
        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        String base64Image = Base64.getEncoder().encodeToString(image);
        Map<String, String> response = new HashMap<>();
        response.put("base64Image", base64Image);

        return ResponseEntity.ok(response);
    }



}
