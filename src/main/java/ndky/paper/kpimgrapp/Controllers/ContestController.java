package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.ContestMapper;
import ndky.paper.kpimgrapp.Request.ContestRequest;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Response.TableResponse;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import ndky.paper.kpimgrapp.Utils.Maid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    /**
     * query, process and generate allowed fields
     *
     * @param contestRequest contestRequest
     * @param username       target user's username
     * @param operationName  'select' or 'update'
     * @return List<String> allowed fields, null for no permission
     */
    private List<String> generateAllowedFields(ContestRequest contestRequest, String username, String operationName) {
        List<String> allowedFields;
        // backdoor for admin or officer
        if ("admin".equals(contestRequest.getRole()) || "officer".equals(contestRequest.getRole())) {
            allowedFields = new ArrayList<>(List.of("contest.*"));
        } else {
            // query user permission scope for current context(select for contest)
            var permissionList = authorizationUtil.queryPermissions(new UserPermissionRequest(null, username, null, contestRequest.getRole(), null, operationName, "contest"));
            if (permissionList.size() == 0)
                return null;
            // extract permitted fields
            allowedFields = permissionList.stream().map(ele -> ele.getTableName() + "." + ele.getColumnName()).collect(Collectors.toList());
            if (allowedFields.contains("contest.*"))
                allowedFields = new ArrayList<>(List.of("contest.*"));
            else {
                allowedFields.add("contest.id");
                allowedFields.add("contest.type");
            }
        }
        // attach foreign keys
        if (allowedFields.contains("contest.tutor_staff_info_id") || allowedFields.contains("contest.*")) {
            allowedFields.add("a.no as tutor_no");
            allowedFields.add("a.name as tutor_name");
        }
        if (allowedFields.contains("contest.add_staff_info_id") || allowedFields.contains("contest.*")) {
            allowedFields.add("b.no as adder_no");
            allowedFields.add("b.name as adder_name");
        }
        return allowedFields;
    }

    @PostMapping("/get/all")
    public ResponseEntity<?> getContestList(@RequestBody ContestRequest contestRequest, HttpServletRequest request) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(contestRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request);
        List<String> allowedFields = generateAllowedFields(contestRequest, username, "select");
        if (allowedFields == null)
            return authorizationUtil.getForbiddenResponseEntity(request);
        // query for response
        return new TableResponse(allowedFields.stream().map(ele -> {
            String temp = Maid.mapUnderCoreStringToCamelCase(ele, false);
            return temp.contains(" as ") ? temp.substring(temp.indexOf(" as ") + 4) : temp.substring(temp.indexOf(".") + 1);
        }).collect(Collectors.toList()), contestMapper.selectList(contestRequest.getStartPos(), contestRequest.getCount(), contestRequest.getQuery(), allowedFields), contestMapper.selectListTotal(contestRequest.getQuery())).responseEntity();
    }

    @PostMapping("/get")
    public ResponseEntity<?> getOneContest(@RequestBody ContestRequest contestRequest, HttpServletRequest request) {
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(contestRequest, request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request);
        List<String> allowedFields = generateAllowedFields(contestRequest, username, "select");
        if (allowedFields == null)
            return authorizationUtil.getForbiddenResponseEntity(request);
        // query for response
        return new QueryResponse(contestMapper.selectOne(contestRequest.getId(), allowedFields), 1).responseEntity();
    }
}
