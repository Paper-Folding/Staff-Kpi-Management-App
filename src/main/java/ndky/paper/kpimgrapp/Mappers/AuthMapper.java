package ndky.paper.kpimgrapp.Mappers;

import ndky.paper.kpimgrapp.Models.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface AuthMapper {
    Boolean existsUser(String username, String password);

    Optional<User> selectUserByUserName(String username);
}
