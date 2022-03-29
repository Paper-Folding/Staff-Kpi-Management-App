package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.StaffInfoMapper;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.StaffInfoRequest;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Response.ErrorResponse;
import ndky.paper.kpimgrapp.Response.TableResponse;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/get")
    public ResponseEntity<?> getStaffInfoList(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        // 1. check if role field is present, this is used for returning role permitted fields
        if (staffInfoRequest.getRole() == null || "".equals(staffInfoRequest.getRole())) {
            logger.log(Level.WARNING, "No role is provided.");
            return new ErrorResponse("No role is provided.").responseEntity();
        }
        String username = authorizationUtil.getUsernameFromRequest(request); // user who is requesting
        // 2. check if logged user has provided role
        if (!authorizationUtil.userHasRole(null, username, null, staffInfoRequest.getRole())) {
            logger.log(Level.WARNING, "Requesting user does not have such a role.");
            return new ErrorResponse("Requesting user does not have such a role.").responseEntity();
        }
        List<String> allowedFields;
        // 3. open backdoor for admin or officer
        if ("admin".equals(staffInfoRequest.getRole()) || "office".equals(staffInfoRequest.getRole())) {
            allowedFields = List.of("*");
        }
        else {
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
        var result = staffInfoMapper.selectStaffInfoList(staffInfoRequest.getStartPos(), staffInfoRequest.getCount(), staffInfoRequest.getQuery(), allowedFields);
        Long total = staffInfoMapper.selectStaffInfoTotal(staffInfoRequest.getQuery());
        return new TableResponse(allowedFields, result, total).responseEntity();
    }
}
