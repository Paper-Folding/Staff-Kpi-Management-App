package ndky.paper.kpimgrapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndky.paper.kpimgrapp.Mappers.AuthenticationMapper;
import ndky.paper.kpimgrapp.Mappers.UtilMapper;
import ndky.paper.kpimgrapp.Models.OperationObject;
import ndky.paper.kpimgrapp.Models.RoleScope;
import ndky.paper.kpimgrapp.Utils.Constants;
import ndky.paper.kpimgrapp.Utils.RoleUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class StaffKpiManagementAppBackApplicationTests {
    private ObjectMapper json = new ObjectMapper();

    @Test
    void testJson() throws JsonProcessingException {
        final Map<String, Object> map = new HashMap<>();
        map.put("ok", "ya");
        map.put("ko", "ay");
        System.out.println(json.writeValueAsString(map));
    }

    @Autowired
    private RoleUtil roleUtil;

    @Test
    void testMyBatis() throws JsonProcessingException {
        List<RoleScope> rc = roleUtil.getUserRoleScopes("bb", Constants.SELECT);
        System.out.println(json.writeValueAsString(rc));
    }
}
