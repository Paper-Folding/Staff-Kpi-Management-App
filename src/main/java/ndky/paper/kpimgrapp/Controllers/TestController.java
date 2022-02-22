package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Utils.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {
    @Autowired
    private RoleUtil roleUtil;

    // 固定权限的api
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    // 非固定权限的api，需要api自行检查是否满足权限
    @PutMapping("/updatesth")
    public ResponseEntity<?> updatesth(HttpServletRequest request) {
        // 1. 通过用户名获取权限
        if (roleUtil.checkIfPermissionExists(roleUtil.getUsernameFromRequest(request), new RoleScope(4, 3),new RoleScope(4,8)))
            return ResponseEntity.ok("yeah");
        else
            return roleUtil.getForbiddenResponseEntity(request);
    }
}
