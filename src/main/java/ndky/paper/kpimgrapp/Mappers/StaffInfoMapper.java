package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.Role;
import ndky.paper.kpimgrapp.Models.StaffInfo;
import ndky.paper.kpimgrapp.Request.StaffInfoRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffInfoMapper extends UtilMapper {
    /**
     * select staff info list
     *
     * @param from              start index
     * @param length            how many records should be returned
     * @param query             ambiguous query
     * @param selectFieldsNames which fields should be cast
     * @return List<StaffInfo>
     */
    List<StaffInfo> selectStaffInfoList(int from, int length, String query, List<String> selectFieldsNames);

    Long selectStaffInfoTotal(String query);

    Integer deleteStaff(StaffInfoRequest staffInfoRequest);

    Integer updateStaffInfo(StaffInfoRequest staffInfoRequest, List<Map<String, String>> allowedFieldsNames);

    Integer attachRolesForUser(Long authenticationId, List<Role> roles);
}
