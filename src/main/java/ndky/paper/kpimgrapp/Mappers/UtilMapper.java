package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.OperationObject;
import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.UserPermission;
import ndky.paper.kpimgrapp.Request.UserPermissionRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UtilMapper {
    /**
     * get the latest auto-generated id for insert, note that this is thread safe
     *
     * @return the id auto-generated
     */
    @Select("select LAST_INSERT_ID()")
    long selectLastInsertId();

    /**
     * get operation objects by given id list and table name
     */
    List<OperationObject> selectOperationObject(List<Long> ids, String tableName);

    /**
     * Collect user role scope
     */
    List<UserPermission> selectUserPermissionBy(UserPermissionRequest userPermissionRequest);

    /**
     * select role list for user
     *
     * @param authorizationId authorization id
     * @param username        username
     * @return List<Role>
     */
    List<Role> selectRoleForUser(Long authorizationId, String username);
}
