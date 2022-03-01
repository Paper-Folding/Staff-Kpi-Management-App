package ndky.paper.kpimgrapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndky.paper.kpimgrapp.Mappers.RoleMapper;
import ndky.paper.kpimgrapp.Models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private RoleMapper roleMapper;

    @Test
    void testMyBatis() {
        Optional<Role> role = roleMapper.selectRole(new Role(null, "admin", null, null));
        if (role.isPresent()) {
            System.out.println(role.get());
        }
    }
}
