package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.StaffInfoMapper;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.BaseQueryRequest;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * Staff Info mgr
 */
@RestController
@RequestMapping("/staffInfo")
public class StaffInfoController {
    private static final Logger logger = Logger.getLogger(StaffInfoController.class.getName());

    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    private ResponseEntity<?> preCheck(String username, BaseQueryRequest staffInfoRequest) {
        // 1. check if role field is present, this is used for returning role permitted fields
        if (staffInfoRequest.getRole() == null || "".equals(staffInfoRequest.getRole())) {
            logger.log(Level.WARNING, "No role is provided.");
            return new ErrorResponse("No role is provided.").responseEntity();
        }
        // 2. check if logged user has provided role
        if (!authorizationUtil.userHasRole(null, username, null, staffInfoRequest.getRole())) {
            logger.log(Level.WARNING, "Requesting user does not have such a role.");
            return new ErrorResponse("Requesting user does not have such a role.").responseEntity();
        }
        return null;
    }

    private ResponseEntity<?> getMapping(StaffInfoRequest staffInfoRequest, HttpServletRequest request, String type) {
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        ResponseEntity<?> res = preCheck(username, staffInfoRequest);
        if (res != null)
            return res;
        List<String> allowedFields;
        // 3. open backdoor for admin or officer
        if ("admin".equals(staffInfoRequest.getRole()) || "officer".equals(staffInfoRequest.getRole())) {
            allowedFields = List.of("*");
        } else {
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
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        ResponseEntity<?> res = preCheck(username, staffInfoRequest);
        if (res != null)
            return res;
        // 3. open backdoor
        if (!"admin".equals(staffInfoRequest.getRole()) && !"officer".equals(staffInfoRequest.getRole())) {
            // 4. query user permission scope for current context(delete for staff_info)
            boolean allowed = authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, staffInfoRequest.getRole(), null, "delete", "staff_info"));
            if (!allowed)
                return authorizationUtil.getForbiddenResponseEntity(request);
        }
        // 5. perform transaction
        return new ModifyResponse(staffInfoMapper.deleteStaff(staffInfoRequest)).responseEntity();
    }

    @PutMapping
    public ResponseEntity<?> updateStaffInfo(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        ResponseEntity<?> res = preCheck(username, staffInfoRequest);
        if (res != null)
            return res;
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
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        ResponseEntity<?> res = preCheck(username, staffInfoImportRequest);
        if (res != null)
            return res;
        if (!"admin".equals(staffInfoImportRequest.getRole()) && !"officer".equals(staffInfoImportRequest.getRole())) {
            boolean allowed = authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, staffInfoImportRequest.getRole(), null, "import", "staff_info"));
            if (!allowed)
                return authorizationUtil.getForbiddenResponseEntity(request);
        }
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
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        ResponseEntity<?> res = preCheck(username, staffInfoRequest);
        if (res != null)
            return res;
        Long authId = authorizationUtil.getAuthenticationIdByStaffInfoId(staffInfoRequest.getId());
        return new ModifyResponse(staffInfoMapper.attachRolesForUser(authId, staffInfoRequest.getRoles())).responseEntity();
    }
}
