package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.AuthorizationMapper;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Response.QueryResponse;
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
    private AuthorizationMapper authorizationMapper;

    /**
     * Used for collect user permission info
     */
    @PostMapping("/get/permission")
    public ResponseEntity<?> queryUserPermission(@RequestBody UserPermissionRequest userPermissionRequest) {
        List<UserPermission> userPermissions = authorizationMapper.queryPermissions(userPermissionRequest);
        return new QueryResponse(userPermissions, userPermissions.size()).responseEntity();
    }
}
