package com.fms.restapi;


import com.fms.models.UserModel;
import com.fms.security.SecurityUser;
import com.fms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserModel>> fetchAll(@RequestParam(required = false) String search,
                                                    Pageable pageable) {

        return ResponseEntity.ok(userService.getAllUsers(search, pageable));
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
}
