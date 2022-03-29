package ndky.paper.kpimgrapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ndky.paper.kpimgrapp.Mappers.StaffInfoMapper;
import ndky.paper.kpimgrapp.Request.StaffInfoRequest;
import ndky.paper.kpimgrapp.Utils.AuthorizationUtil;
import ndky.paper.kpimgrapp.Utils.Maid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
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
    private StaffInfoMapper staffInfoMapper;

    @Test
    void testMyBatis() {
        var re = new StaffInfoRequest();
        re.setId(1L);
        re.setGender("男");
        re.setIdcard("fucking");
        re.setNation("China");
        re.setDepartment("balu");
        re.setJobAlias("gua");
        List<String> allowed = Arrays.asList("gender", "idcard", "nation", "department", "job_alias");
        staffInfoMapper.updateStaffInfo(re, Maid.batchMapUnderCoreStringToCamelCase(allowed, false));
    }

    @Autowired
    private AuthorizationUtil authorizationUtil;

    @Test
    void whatever() {
        System.out.println(authorizationUtil.getFieldsForTable("staff_info", "id", "birth"));
    }
}
