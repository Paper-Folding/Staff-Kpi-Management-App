package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Request.UserRoleRequest;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthorizationUtil authorizationUtil;

    @PostMapping("/get/role")
    public ResponseEntity<?> queryUserRole(@RequestBody UserRoleRequest userRoleRequest) {
        List<Role> list = authorizationUtil.queryRoles(userRoleRequest);
        return new QueryResponse(list, list.size()).responseEntity();
    }

    @PostMapping("/get/role/all")
    public ResponseEntity<?> queryAllRoles() {
        var result = authorizationUtil.queryAllRoles();
        return new QueryResponse(authorizationUtil.queryAllRoles(), result.size()).responseEntity();
    }

    /**
     * Used for collect user permission info
     */
    @PostMapping("/get/permission")
    public ResponseEntity<?> queryUserPermission(@RequestBody UserPermissionRequest userPermissionRequest) {
        List<UserPermission> userPermissions = authorizationUtil.queryPermissions(userPermissionRequest);
        return new QueryResponse(userPermissions, userPermissions.size()).responseEntity();
    }
}
