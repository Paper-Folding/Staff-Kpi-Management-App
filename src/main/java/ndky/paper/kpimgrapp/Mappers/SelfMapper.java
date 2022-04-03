package ndky.paper.kpimgrapp.Mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SelfMapper {
    String getAvatarFileName(Long authenticationId, String username);

    Integer updateAvatarFileName(Long authenticationId, String username, String newFileName);
}
