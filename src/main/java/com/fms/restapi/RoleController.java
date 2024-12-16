package com.fms.restapi;

import com.fms.models.RoleModel;
import com.fms.security.SecurityUser;
import com.fms.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RoleModel>> fetchAll(@RequestParam(required = false) String search,
                                                    Pageable pageable) {

        return ResponseEntity.ok(roleService.getAllRoles(search, pageable));
    }

    @GetMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleModel> fetch(@PathVariable(value = "roleId") final Long roleId) {
        return ResponseEntity.ok(roleService.getRole(roleId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleModel> save(@RequestBody RoleModel roleModel, @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(roleService.createOrUpdate(roleModel, null));
    }

    @PutMapping(value = "/{roleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleModel> update(@PathVariable(value = "roleId") final Long roleId,
                                            @RequestBody RoleModel roleModel,
                                            @AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok(roleService.createOrUpdate(roleModel, roleId));
    }
}
