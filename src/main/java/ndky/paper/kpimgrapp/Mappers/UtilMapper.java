package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.OperationObject;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UtilMapper {
    /**
     * get the latest auto-generated id for insert, note that this is thread safe
     *
     * @return the id auto-generated
     */
    @Select("select LAST_INSERT_ID()")
    long selectLastInsertId();

    List<OperationObject> selectOperationObject(List<Long> ids, String tableName);
}
