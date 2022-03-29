package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.RoleMapper;
import ndky.paper.kpimgrapp.Request.RoleRequest;
import ndky.paper.kpimgrapp.Response.ErrorResponse;
import ndky.paper.kpimgrapp.Response.ModifyResponse;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * controller for manage role
 */
@RestController
@PreAuthorize("hasAuthority('admin') or hasAuthority('officer')")
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    // if user are requesting with a role other than admin or officer, deny them. Other requests in RoleController are the same.
    // Thus, this method got true for role guaranteed while false otherwise.
    private Boolean ifRoleFieldPresent(RoleRequest roleRequest) {
        return roleRequest.getRole() != null && ("admin".equals(roleRequest.getRole()) || "officer".equals(roleRequest.getRole()));
    }

    private Boolean guaranteeCreatorModify(RoleRequest roleRequest, HttpServletRequest request) {
        if ("officer".equals(roleRequest.getRole())) {
            // check if they are the creator of current selected role
            String requestedUsername = authorizationUtil.getUsernameFromRequest(request);
            return roleMapper.verifyRoleCreator(requestedUsername, roleRequest.getId());
        }
        return true;
    }

    // Browser does not support send get request with request body.
    @PostMapping("/get")
    public ResponseEntity<?> getRole(@RequestBody RoleRequest roleRequest, HttpServletRequest request) {
        if (!ifRoleFieldPresent(roleRequest))
            return authorizationUtil.getForbiddenResponseEntity(request);
        var result = roleMapper.selectRole(roleRequest);
        if (result.isPresent())
            return new QueryResponse(result.get(), 1).responseEntity();
        else
            return new QueryResponse(null, 0).responseEntity();
    }

    @PostMapping("/get/all")
    public ResponseEntity<?> getAllRoles(@RequestBody RoleRequest roleRequest, HttpServletRequest request) {
        if (!ifRoleFieldPresent(roleRequest))
            return authorizationUtil.getForbiddenResponseEntity(request);
        var result = roleMapper.selectAllRoles(roleRequest.getStartPos(), roleRequest.getCount(), roleRequest.getQuery());
        var total = roleMapper.selectRoleTotal();
        return new QueryResponse(result, total).responseEntity();
    }

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody RoleRequest roleRequest, HttpServletRequest request) {
        if (!ifRoleFieldPresent(roleRequest))
            return authorizationUtil.getForbiddenResponseEntity(request);
        if (roleMapper.existsRole(roleRequest.getId(), roleRequest.getName()))
            return new ModifyResponse(ModifyResponse.DUPLICATE, 0, "Duplicate role detected.").responseEntity();
        int afflicted = roleMapper.addRole(roleRequest);
        long lastInsertId = roleMapper.selectLastInsertId();
        if (roleRequest.getRoleScopes() != null && roleRequest.getRoleScopes().size() > 0)
            afflicted += roleMapper.addRoleScopesForRole(lastInsertId, roleRequest.getRoleScopes());
        return new ModifyResponse(afflicted).responseEntity();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRole(@RequestBody RoleRequest roleRequest, HttpServletRequest request) {
        if (!ifRoleFieldPresent(roleRequest))
            return authorizationUtil.getForbiddenResponseEntity(request);
        if ("admin".equals(roleRequest.getName()) || "officer".equals(roleRequest.getName()) || "initial".equals(roleRequest.getName()))
            return new ErrorResponse(roleRequest.getName() + " is not deletable!").responseEntity();
        if (!guaranteeCreatorModify(roleRequest, request))
            return new ErrorResponse("您没有权限删除此角色，因为您不是该角色的创建者。").responseEntity();
        return new ModifyResponse(roleMapper.deleteRole(roleRequest)).responseEntity();
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody RoleRequest roleRequest, HttpServletRequest request) {
        if (!ifRoleFieldPresent(roleRequest))
            return authorizationUtil.getForbiddenResponseEntity(request);
        if ("admin".equals(roleRequest.getName()) || "officer".equals(roleRequest.getName()) || "initial".equals(roleRequest.getName()))
            return new ErrorResponse(roleRequest.getName() + " is not updatable!").responseEntity();
        if (!guaranteeCreatorModify(roleRequest, request))
            return new ErrorResponse("您没有权限修改此角色，因为您不是该角色的创建者。").responseEntity();
        if (roleRequest.getId() != null && roleRequest.getName() != null && !roleMapper.existsRole(roleRequest.getId(), roleRequest.getName()) && roleMapper.existsRole(null, roleRequest.getName()))
            return new ModifyResponse(ModifyResponse.DUPLICATE, 0, "Trying to rename to a duplicate role.").responseEntity();
        int afflicted = roleMapper.updateRole(roleRequest);
        if (roleRequest.getRoleScopes() != null) {
            afflicted += roleMapper.deleteRoleScopesForRole(roleRequest.getId());
            if (roleRequest.getRoleScopes().size() > 0)
                afflicted += roleMapper.addRoleScopesForRole(roleRequest.getId(), roleRequest.getRoleScopes());
        }
        return new ModifyResponse(afflicted).responseEntity();
    }
}
