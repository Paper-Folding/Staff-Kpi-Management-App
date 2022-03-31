package ndky.paper.kpimgrapp.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller to upload avatar, alter self info, etc...
 */
@RestController
public class SelfController {
    @Autowired
    PasswordEncoder passwordEncoder;
}
