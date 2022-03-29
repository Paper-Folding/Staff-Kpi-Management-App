package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.StaffInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
}
