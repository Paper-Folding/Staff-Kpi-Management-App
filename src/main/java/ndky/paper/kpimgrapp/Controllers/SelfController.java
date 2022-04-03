package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.SelfMapper;
import ndky.paper.kpimgrapp.Response.ErrorResponse;
import ndky.paper.kpimgrapp.Response.ModifyResponse;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import ndky.paper.kpimgrapp.Storage.AvatarStorageImpl;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * A controller to upload avatar, alter self info, etc...
 */
@RestController
@RequestMapping("/me")
public class SelfController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SelfMapper selfMapper;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @Autowired
    private AvatarStorageImpl avatarStorage;

    @PostMapping("/updateAvatar")
    public ResponseEntity<?> updateAvatar(MultipartFile avatar, HttpServletRequest request) {
        String requestUsername = authorizationUtil.getUsernameFromRequest(request);
        // make sure requested user exists
        if (requestUsername == null || "".equals(requestUsername))
            return new ModifyResponse(0).responseEntity();
        // if old avatar is present, delete file
        String oldAvatarFileName = selfMapper.getAvatarFileName(null, requestUsername);
        if (oldAvatarFileName != null && !"".equals(oldAvatarFileName.trim())) {
            avatarStorage.deleteOneFile(oldAvatarFileName);
        }
        // reset avatar to default
        if (avatar == null || avatar.getSize() == 0 || "".equals(avatar.getOriginalFilename())) {
            selfMapper.updateAvatarFileName(null, requestUsername, "");
            return new ModifyResponse(-1).responseEntity();
        }
        // upload avatar and update avatar filename
        String newFileName = UUID.randomUUID().toString() + new Date().getTime() + avatar.getOriginalFilename().substring(avatar.getOriginalFilename().lastIndexOf("."));
        avatarStorage.storeFile(avatar, newFileName);
        return new ModifyResponse(selfMapper.updateAvatarFileName(null, requestUsername, newFileName)).responseEntity();
    }

    @GetMapping("/avatar/{username}")
    public ResponseEntity<Resource> serveAvatar(@PathVariable String username) {
        // make sure requested user exists
        Resource resource;
        if (username == null || "".equals(username)) {
            resource = avatarStorage.loadFileAsDefaultResource();
        } else {
            String avatarFileName = selfMapper.getAvatarFileName(null, username);
            resource = avatarStorage.loadFileAsResource(avatarFileName);
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<?> getInfo(HttpServletRequest request) {
        String requestUsername = authorizationUtil.getUsernameFromRequest(request);
        var me = selfMapper.getInfo(null, requestUsername);
        if (me.isPresent())
            return new QueryResponse(me.get(), 1).responseEntity();
        return new ErrorResponse("No such user").responseEntity();
    }

    @PutMapping
    public ResponseEntity<?> updateInfo(@RequestBody Map<String, String> data, HttpServletRequest request) {
        if (data.keySet().size() == 0)
            return new ModifyResponse(0).responseEntity();
        String key = data.keySet().iterator().next(), value = data.get(key);
        String requestUsername = authorizationUtil.getUsernameFromRequest(request);
        Long sid = authorizationUtil.getStaffInfoIdByAuthentication(null, requestUsername);
        return new ModifyResponse(selfMapper.updateInfo(sid, key, value)).responseEntity();
    }
}
