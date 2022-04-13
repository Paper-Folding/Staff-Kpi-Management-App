package ndky.paper.kpimgrapp.Controllers;

import ndky.paper.kpimgrapp.Mappers.StatisticsMapper;
import ndky.paper.kpimgrapp.Response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @GetMapping("/contest")
    public ResponseEntity<?> contest() {
        var result = new HashMap<String, Object>();
        result.put("type", statisticsMapper.contestType());
        result.put("level", statisticsMapper.contestLevel());
        return new QueryResponse(result, 2).responseEntity();
    }

    @GetMapping("/staff")
    public ResponseEntity<?> staff() {
        var result = new HashMap<String, Object>();
        result.put("gender", statisticsMapper.staffGender());
        result.put("age", statisticsMapper.staffAge());
        return new QueryResponse(result, 2).responseEntity();
    }
}
