package ndky.paper.kpimgrapp.Controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
//    @Autowired
//    private AuthorizationUtil roleUtil;
//
//    // 固定权限的api
//    @PreAuthorize("hasAuthority('admin')")
//    @GetMapping("/hello")
//    public String hello() {
//        return "hello";
//    }

//    // 非固定权限的api，需要api自行检查是否满足权限
//    @PutMapping("/updatesth")
//    public ResponseEntity<?> updatesth(HttpServletRequest request) {
//        // 1. 通过用户名获取权限
//        if (roleUtil.checkIfPermissionExists(roleUtil.getUsernameFromRequest(request), new RoleScope(4, 3, null, null), new RoleScope(4, 8, null, null)))
//            return ResponseEntity.ok("yeah");
//        else
//            return roleUtil.getForbiddenResponseEntity(request);
//    }
}
