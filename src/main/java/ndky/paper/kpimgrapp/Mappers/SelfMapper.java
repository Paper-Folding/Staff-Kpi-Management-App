package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.StaffInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface SelfMapper {
    String getAvatarFileName(Long authenticationId, String username);

    Integer updateAvatarFileName(Long authenticationId, String username, String newFileName);

    Optional<StaffInfo> getInfo(Long authenticationId, String username);

    /**
     * update self info by specified field and specified value
     *
     * @param staffInfoId    target staff
     * @param dueUpdateKey   specified field
     * @param dueUpdateValue specified value
     */
    Integer updateInfo(Long staffInfoId, String dueUpdateKey, String dueUpdateValue);

    Integer updatePassword(Long authenticationId, String username, String newPassword);
}
