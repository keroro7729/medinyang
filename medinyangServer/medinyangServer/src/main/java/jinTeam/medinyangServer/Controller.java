package jinTeam.medinyangServer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Controller {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
