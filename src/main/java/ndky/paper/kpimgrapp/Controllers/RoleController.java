package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.RoleMapper;
import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Response.ModifyResponse;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller for manage role
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleMapper roleMapper;

    @GetMapping
    public ResponseEntity<?> getRole(@RequestBody Role role) {
        var result = roleMapper.selectRole(role);
        if (result.isPresent())
            return new QueryResponse(result.get(), 1).responseEntity();
        else
            return new QueryResponse(null, 0).responseEntity();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles() {
        var result = roleMapper.selectAllRoles();
        return new QueryResponse(result, result.size()).responseEntity();
    }

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody Role role) {
        if (roleMapper.existsRole(role.getName()))
            return new ModifyResponse(ModifyResponse.DUPLICATE, 0, "Duplicate role detected.").responseEntity();
        int afflicted = roleMapper.addRole(role);
        long lastInsertId = roleMapper.selectLastInsertId();
        if (role.getRoleScopes() != null && role.getRoleScopes().size() > 0)
            afflicted += roleMapper.addRoleScopesForRole(lastInsertId, role.getRoleScopes());
        return new ModifyResponse(afflicted, "Done").responseEntity();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRole(@RequestBody Role role) {
        return new ModifyResponse(roleMapper.deleteRole(role), "Done").responseEntity();
    }

    @PutMapping
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        if (role.getName() != null && !roleMapper.existsRoleStrict(role.getId(), role.getName()) && roleMapper.existsRole(role.getName()))
            return new ModifyResponse(ModifyResponse.DUPLICATE, 0, "Trying to rename to a duplicate role.").responseEntity();
        int afflicted = roleMapper.updateRole(role);
        if (role.getRoleScopes() == null)
            return new ModifyResponse(afflicted, "Done").responseEntity();
        afflicted += roleMapper.deleteRoleScopesForRole(role.getId());
        if (role.getRoleScopes().size() > 0)
            afflicted += roleMapper.addRoleScopesForRole(role.getId(), role.getRoleScopes());
        return new ModifyResponse(afflicted, "Done").responseEntity();
    }
}
