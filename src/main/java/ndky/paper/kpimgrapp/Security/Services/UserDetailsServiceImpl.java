package ndky.paper.kpimgrapp.Security.Services;

import ndky.paper.kpimgrapp.Mappers.AuthMapper;
import ndky.paper.kpimgrapp.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AuthMapper authMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authMapper.selectUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
