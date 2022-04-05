package ndky.paper.kpimgrapp.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndky.paper.kpimgrapp.Mappers.ContestMapper;
import ndky.paper.kpimgrapp.Request.BaseQueryRequest;
import ndky.paper.kpimgrapp.Request.ContestRequest;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import ndky.paper.kpimgrapp.Response.ErrorResponse;
import ndky.paper.kpimgrapp.Response.ModifyResponse;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Response.TableResponse;
import ndky.paper.kpimgrapp.Storage.CertificateStorageImpl;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import ndky.paper.kpimgrapp.Utils.Maid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @Autowired
    private CertificateStorageImpl certificateStorage;

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
        var contest = contestMapper.selectOne(contestRequest.getId(), allowedFields);
        if (contest.isPresent())
            return new QueryResponse(contest.get(), 1).responseEntity();
        else
            return new ErrorResponse(contestRequest.getId() + " is not present!").responseEntity();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, String> getCertificateFromDataBase(Long contestId) {
        var certOp = contestMapper.selectOne(contestId, Arrays.asList("contest.id", "contest.certificate"));
        if (certOp.isEmpty())
            return null;
        String certificate = certOp.get().getCertificate();
        if (certificate == null || "".equals(certificate))
            return null;
        try {
            return objectMapper.readValue(certificate, new TypeReference<>() {
            });
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }

    @PostMapping("/uploadCert")
    public ResponseEntity<?> uploadCertificate(MultipartFile cert, @RequestParam String role, @RequestParam Long contestId, HttpServletRequest request) {
        if (cert == null || cert.getSize() == 0 || "".equals(cert.getOriginalFilename()))
            return new ErrorResponse("Certificate is empty file!").responseEntity();
        if (!Maid.validFileExtension(cert.getOriginalFilename(), ".doc", ".docx", ".pdf", ".jpg", ".jpeg", ".gif", ".png", ".bmp", ".tif"))
            return new ErrorResponse("File does not have a valid extension!").responseEntity();
        ResponseEntity<?> res = authorizationUtil.ensureRoleIsValidForRequest(new BaseQueryRequest().setRole(role), request);
        if (res != null)
            return res;
        String username = authorizationUtil.getUsernameFromRequest(request);
        boolean allowed = "admin".equals(role) || "officer".equals(role) || authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, role, null, "update", "contest", "certificate")) || authorizationUtil.userHasPermission(new UserPermissionRequest(null, username, null, role, null, "insert", "contest"));
        if (!allowed)
            return authorizationUtil.getForbiddenResponseEntity(request);
        var oldCertificate = getCertificateFromDataBase(contestId);
        if (oldCertificate == null)
            return new ErrorResponse("Contest with id:" + contestId + " is not present!").responseEntity();
        if (!"".equals(oldCertificate.get("store")))
            certificateStorage.deleteOneFile(oldCertificate.get("store"));
        String newFileName = Maid.getUniqueString() + Maid.getFilenameExt(cert.getOriginalFilename());
        certificateStorage.storeFile(cert, newFileName);
        Map<String, String> newCertificate = new HashMap<>();
        newCertificate.put("store", newFileName);
        newCertificate.put("ori", cert.getOriginalFilename());
        try {
            return new ModifyResponse(contestMapper.updateContest(contestId, "certificate", objectMapper.writeValueAsString(newCertificate))).responseEntity();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ErrorResponse("Server error!").responseEntity();
    }

    @GetMapping("/cert/{contestId}")
    public ResponseEntity<?> serveCertificate(@PathVariable Long contestId) {
        if (contestId == null)
            return new ErrorResponse("File is not found for contest with id:" + contestId).responseEntity();
        var certificate = getCertificateFromDataBase(contestId);
        if (certificate == null)
            return new ErrorResponse("File is not found for contest with id:" + contestId).responseEntity();
        Resource resource = certificateStorage.loadFileAsResource(certificate.get("store"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(certificate.get("ori"), StandardCharsets.UTF_8) + "\"")
                .body(resource);
    }
}
