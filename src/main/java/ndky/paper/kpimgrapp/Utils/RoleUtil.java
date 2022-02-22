package ndky.paper.kpimgrapp.Utils;

import ndky.paper.kpimgrapp.Mappers.AuthMapper;
import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Response.AccessDeniedResponse;
import ndky.paper.kpimgrapp.Security.Jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class RoleUtil {
    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * @param username username to check
     * @param targetRoles which role scope should be checked existence, can pass multiple, check with "OR"
     * @return true for permission accepted, false for denied
     */
    public boolean checkIfPermissionExists(String username, RoleScope... targetRoles) {
        // 1. 通过用户名获取权限集
        List<RoleScope> roleScopes = authMapper.selectRoleScopeByUsername(username);
        // 2. 检查所属权限是否能够访问此接口
        for (RoleScope scope : roleScopes)
            for (RoleScope target : targetRoles)
                if (target.equals(scope))
                    return true;
        return false;
    }

    public AuthMapper getAuthMapper() {
        return authMapper;
    }

    public String getUsernameFromRequest(HttpServletRequest request) {
        return jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwtFromRequest(request));
    }

    public ResponseEntity<?> getForbiddenResponseEntity(HttpServletRequest request) {
        return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body(new AccessDeniedResponse(request.getServletPath()));
    }
}
