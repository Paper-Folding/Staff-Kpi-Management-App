package ndky.paper.kpimgrapp.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * For table response
 * contain header and rows
 */
public class TableResponse extends QueryResponse {
    public TableResponse(List<String> header, Object rows, long total) {
        super(null, total);
        Map<String, Object> result = new HashMap<>();
        result.put("header", header);
        result.put("rows", rows);
        super.result = result;
    }
}
