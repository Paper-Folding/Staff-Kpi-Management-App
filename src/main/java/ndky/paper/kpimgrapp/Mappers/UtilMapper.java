package ndky.paper.kpimgrapp.Mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UtilMapper {
    /**
     * get the latest auto-generated id for insert, note that this is thread safe
     *
     * @return the id auto-generated
     */
    @Select("select LAST_INSERT_ID()")
    long selectLastInsertId();

//    /**
//     * get operation objects by given id list and table name
//     */
//    List<OperationObject> selectOperationObject(List<Long> ids, String tableName);

//    /**
//     * select role list for user
//     *
//     * @param authorizationId authorization id
//     * @param username        username
//     * @return List<Role>
//     */
//    List<Role> selectRoleForUser(Long authorizationId, String username);
}
