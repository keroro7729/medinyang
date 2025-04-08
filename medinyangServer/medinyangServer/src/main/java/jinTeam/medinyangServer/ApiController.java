package jinTeam.medinyangServer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/chat")
    public String hello(){
        return "hello";
    }
}
