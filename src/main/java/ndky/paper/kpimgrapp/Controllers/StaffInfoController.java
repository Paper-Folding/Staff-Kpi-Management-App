package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.StaffInfoMapper;
import ndky.paper.kpimgrapp.Mappers.UtilMapper;
import ndky.paper.kpimgrapp.Models.OperationObject;
import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Models.StaffInfo;
import ndky.paper.kpimgrapp.Request.StaffInfoRequest;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Response.TableResponse;
import ndky.paper.kpimgrapp.Utils.Constants;
import ndky.paper.kpimgrapp.Utils.RoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/staffInfo")
public class StaffInfoController {
    @Autowired
    private StaffInfoMapper staffInfoMapper;

    @Autowired
    private RoleUtil roleUtil;

    @Autowired
    private UtilMapper utilMapper;

    @PostMapping("/get")
    public ResponseEntity<?> getStaffInfo(@RequestBody StaffInfoRequest staffInfoRequest, HttpServletRequest request) {
        // 1. get user permitted role scopes
        List<RoleScope> roleScopeList = roleUtil.getUserRoleScopes(roleUtil.getUsernameFromRequest(request), Constants.SELECT);
        if (roleScopeList.size() == 0)
            return new QueryResponse(null, 0).responseEntity();
        // 2. extract permitted fields
        List<String> allowedFields = utilMapper.selectOperationObject(roleScopeList.stream().map(RoleScope::getObjectId).collect(Collectors.toList()), "staff_info")
                .stream().map(OperationObject::getColumnName).collect(Collectors.toList());
        if (allowedFields.contains("*"))
            allowedFields = List.of("*");
        // 3. select data and response
        List<StaffInfo> staffInfoList = staffInfoMapper.selectStaffInfo(staffInfoRequest.getStartPos(), staffInfoRequest.getCount(), staffInfoRequest.getQuery(), staffInfoRequest.getQueryStr(), allowedFields);
        Integer total = staffInfoMapper.selectStaffInfoTotal();
        return new TableResponse(allowedFields, staffInfoList, total).responseEntity();
    }
}
