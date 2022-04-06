package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.StaffInfoMapper;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.StaffInfoImportRequest;
import ndky.paper.kpimgrapp.Request.StaffInfoRequest;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Response.ErrorResponse;
import ndky.paper.kpimgrapp.Response.ModifyResponse;
import ndky.paper.kpimgrapp.Response.TableResponse;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import ndky.paper.kpimgrapp.Utils.Maid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Staff Info mgr
 */
@RestController
@RequestMapping("/staffInfo")
public class StaffInfoController {
    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    private ResponseEntity<?> getMapping(StaffInfoRequest staffInfoRequest, HttpServletRequest request, String type) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(staffInfoRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        List<String> allowedFields;
        // 3. open backdoor for admin or officer
        if ("admin".equals(staffInfoRequest.getRole()) || "officer".equals(staffInfoRequest.getRole())) {
            allowedFields = List.of("*");
        } else {
            if (type.equals("export") && !authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, staffInfoRequest.getRole(), null, "export", "staff_info")))
                return authorizationUtil.getForbiddenResponseEntity(request);
            // 4. query user permission scope for current context(select for staff_info)
            var permissionList = authorizationUtil.queryPermissions(new UserPermissionRequest(null, username, null, staffInfoRequest.getRole(), null, "select", "staff_info"));
            if (permissionList.size() == 0)
                return authorizationUtil.getForbiddenResponseEntity(request);
            // 5. extract permitted fields
            allowedFields = permissionList.stream().map(UserPermission::getColumnName).collect(Collectors.toList());
            if (allowedFields.contains("*"))
                allowedFields = List.of("*");
            else
                allowedFields.add("id");
        }
        // 6. select data and response
        var result = staffInfoMapper.selectStaffInfoList(type.equals("select") ? staffInfoRequest.getStartPos() : 0, type.equals("select") ? staffInfoRequest.getCount() : 0, staffInfoRequest.getQuery(), allowedFields);
        Long total = staffInfoMapper.selectStaffInfoTotal(staffInfoRequest.getQuery());
        return new TableResponse(allowedFields.stream().map(ele -> Maid.mapUnderCoreStringToCamelCase(ele, false)).collect(Collectors.toList()), result, total).responseEntity();
    }

    @PostMapping("/get/all")
    public ResponseEntity<?> getStaffInfoList(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        return getMapping(staffInfoRequest, request, "select");
    }

    @PostMapping("/export")
    public ResponseEntity<?> exportStaffInfoList(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        return getMapping(staffInfoRequest, request, "export");
    }

    @DeleteMapping
    public ResponseEntity<?> removeStaff(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(staffInfoRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        // query user permission scope for current context(delete for staff_info) (open back door for admin or officer)
        boolean allowed = "admin".equals(staffInfoRequest.getRole()) || "officer".equals(staffInfoRequest.getRole()) || authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, staffInfoRequest.getRole(), null, "delete", "staff_info"));
        if (!allowed)
            return authorizationUtil.getForbiddenResponseEntity(request);
        // perform transaction
        return new ModifyResponse(staffInfoMapper.deleteStaff(staffInfoRequest)).responseEntity();
    }

    @PutMapping
    public ResponseEntity<?> updateStaffInfo(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(staffInfoRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        List<String> allowedFields;
        if ("admin".equals(staffInfoRequest.getRole()) || "officer".equals(staffInfoRequest.getRole())) {
            allowedFields = List.of("*");
        } else {
            var permissionList = authorizationUtil.queryPermissions(new UserPermissionRequest(null, username, null, staffInfoRequest.getRole(), null, "update", "staff_info"));
            if (permissionList.size() == 0)
                return authorizationUtil.getForbiddenResponseEntity(request);
            allowedFields = permissionList.stream().map(UserPermission::getColumnName).collect(Collectors.toList());
        }
        if (allowedFields.contains("*"))
            allowedFields = authorizationUtil.getFieldsForTable("staff_info", "id", "disabled");
        Integer afflicted = staffInfoMapper.updateStaffInfo(staffInfoRequest, Maid.batchMapUnderCoreStringToCamelCase(allowedFields, false));
        return new ModifyResponse(afflicted).responseEntity();
    }

    @PostMapping("/import")
    public ResponseEntity<?> importStaffList(@RequestBody StaffInfoImportRequest staffInfoImportRequest, HttpServletRequest request) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(staffInfoImportRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        boolean allowed = "admin".equals(staffInfoImportRequest.getRole()) || "officer".equals(staffInfoImportRequest.getRole()) || authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, staffInfoImportRequest.getRole(), null, "import", "staff_info"));
        if (!allowed)
            return authorizationUtil.getForbiddenResponseEntity(request);
        for (StaffInfoRequest each : staffInfoImportRequest.getList()) {
            if (staffInfoMapper.checkIfNoDuplicate(each.getNo())) {
                return new ErrorResponse("存在重复的编号，所有用户均未被导入！").responseEntity();
            }
        }
        return new ModifyResponse(staffInfoMapper.batchAddStaff(staffInfoImportRequest.getList())).responseEntity();
    }

    @PreAuthorize("hasAuthority('admin') or hasAuthority('officer')")
    @PutMapping("/attachRole")
    public ResponseEntity<?> attachRoleForStaff(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(staffInfoRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        boolean allowed = "admin".equals(staffInfoRequest.getRole()) || "officer".equals(staffInfoRequest.getRole()) || authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, staffInfoRequest.getRole(), null, "attachRole", "staff_info"));
        if (!allowed)
            return authorizationUtil.getForbiddenResponseEntity(request);
        Long authId = authorizationUtil.getAuthenticationIdByStaffInfoId(staffInfoRequest.getId());
        staffInfoMapper.detachRolesForUser(authId);
        return new ModifyResponse(staffInfoMapper.attachRolesForUser(authId, staffInfoRequest.getRoles())).responseEntity();
    }
}
