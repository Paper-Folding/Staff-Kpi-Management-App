package ndky.paper.kpimgrapp.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
