package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.StaffInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Optional;

@Mapper
public interface SelfMapper {
    String getAvatarFileName(Long authenticationId, String username);

    Integer updateAvatarFileName(Long authenticationId, String username, String newFileName);

    Optional<StaffInfo> getInfo(Long authenticationId, String username);

    Integer updateInfo(Long staffInfoId, String dueUpdateKey, String dueUpdateValue);
}
