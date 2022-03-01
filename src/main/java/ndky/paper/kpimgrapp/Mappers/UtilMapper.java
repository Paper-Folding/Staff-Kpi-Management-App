package ndky.paper.kpimgrapp.Mappers;

import org.apache.ibatis.annotations.Select;

public interface UtilMapper {

    /**
     * get the latest auto-generated id for insert, note that this is thread safe
     *
     * @return the id auto-generated
     */
    @Select("select LAST_INSERT_ID()")
    long selectLastInsertId();
}
