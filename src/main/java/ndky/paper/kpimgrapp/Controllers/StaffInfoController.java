package ndky.paper.kpimgrapp.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/staffInfo")
public class StaffInfoController {
//    @PostMapping("/get")
//    public ResponseEntity<?> getStaffInfoList(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
//
//    }

//    @Autowired
//    private StaffInfoMapper staffInfoMapper;
//
//    @Autowired
//    private AuthorizationUtil authorizationUtil;
//
//    @Autowired
//    private UtilMapper utilMapper;

//    @PostMapping("/get")
//    public ResponseEntity<?> getStaffInfo(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
//        if (staffInfoRequest.getRole() == null || staffInfoRequest.getRole().equals(""))
//            return new ErrorResponse("No role is provided!").responseEntity();
//        String username = roleUtil.getUsernameFromRequest(request);
//        // 1. check if logged user has provided role
//        if (!utilMapper.userExistsRole(null, username, staffInfoRequest.getRole()))
//            return roleUtil.getForbiddenResponseEntity(request);
//        List<String> allowedFields;
//        // 2. back door for admin and officer
//        if (staffInfoRequest.getRole().equals("admin") || staffInfoRequest.getRole().equals("officer"))
//            allowedFields = List.of("*");
//        else {
//            // 3. get user permitted role scopes
//            List<RoleScope> roleScopeList = roleUtil.getUserRoleScopes(roleUtil.getUsernameFromRequest(request), Constants.SELECT);
//            if (roleScopeList.size() == 0)
//                return roleUtil.getForbiddenResponseEntity(request);
//            // 4. extract permitted fields
//            allowedFields = utilMapper.selectOperationObject(roleScopeList.stream().map(RoleScope::getObjectId).collect(Collectors.toList()), "staff_info")
//                    .stream().map(OperationObject::getColumnName).collect(Collectors.toList());
//            if (allowedFields.contains("*"))
//                allowedFields = List.of("*");
//            else
//                allowedFields.add("id");
//        }
//        // 5. select data and response
//        List<StaffInfo> staffInfoList = staffInfoMapper.selectStaffInfo(staffInfoRequest.getStartPos(), staffInfoRequest.getCount(), staffInfoRequest.getQuery(), staffInfoRequest.getQueryStr(), allowedFields);
//        Integer total = staffInfoMapper.selectStaffInfoTotal();
//        return new TableResponse(allowedFields, staffInfoList, total).responseEntity();
//    }
}
