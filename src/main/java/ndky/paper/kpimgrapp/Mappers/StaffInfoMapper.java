package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.StaffInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaffInfoMapper {
    List<StaffInfo> selectStaffInfo(int from, int length, List<String> selectFieldsNames);

    Integer selectStaffInfoTotal();
}
