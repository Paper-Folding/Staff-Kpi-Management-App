package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

// authentication makes user authenticate
@Mapper
public interface AuthenticationMapper {
    Boolean existsUser(String username);

    Optional<User> selectUserByUserName(String username);

}
