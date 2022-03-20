package ndky.paper.kpimgrapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndky.paper.kpimgrapp.Mappers.UtilMapper;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class StaffKpiManagementAppBackApplicationTests {
    private final ObjectMapper json = new ObjectMapper();

    @Test
    void testJson() throws JsonProcessingException {
        final Map<String, Object> map = new HashMap<>();
        map.put("ok", "ya");
        map.put("ko", "ay");
        System.out.println(json.writeValueAsString(map));
    }

    @Autowired
    private UtilMapper utilMapper;

    @Test
    void testMyBatis() throws JsonProcessingException {
        List<UserPermission> userPermissions = utilMapper.selectUserPermissionBy(new UserPermissionRequest(null, "bb", "", ""));
        System.out.println(json.writeValueAsString(userPermissions));
    }
}
