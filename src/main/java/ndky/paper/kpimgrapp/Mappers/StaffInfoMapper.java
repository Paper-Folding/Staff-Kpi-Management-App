package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.StaffInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffInfoMapper {
    List<StaffInfo> selectStaffInfo(int from, int length, StaffInfo staffInfo, List<String> selectFieldsNames);

    Integer selectStaffInfoTotal();
}
