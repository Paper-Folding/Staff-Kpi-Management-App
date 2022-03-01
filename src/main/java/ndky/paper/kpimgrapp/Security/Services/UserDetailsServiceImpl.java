package ndky.paper.kpimgrapp.Security.Services;

import ndky.paper.kpimgrapp.Mappers.AuthenticationMapper;
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
    AuthenticationMapper authenticationMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authenticationMapper.selectUserByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Cannot find user with username: " + username));
        return UserDetailsImpl.build(user);
    }
}
