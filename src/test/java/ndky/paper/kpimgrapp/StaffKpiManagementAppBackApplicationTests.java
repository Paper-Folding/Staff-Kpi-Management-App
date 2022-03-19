package ndky.paper.kpimgrapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndky.paper.kpimgrapp.Mappers.AuthenticationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

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
    private AuthenticationMapper mapper;

    @Test
    void testMyBatis() throws JsonProcessingException {
        var user = mapper.selectUserByUserName("aa");
        if (user.isPresent()) {
            var uu = user.get();
            System.out.println(json.writeValueAsString(uu));
        }
    }
}
