package jinTeam.medinyangServer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class WebController {

    @GetMapping("/")
    public String home(){
        return "redirect:/index.html";
    }

    @GetMapping("/url")
    public String dynamicPage(Model model){
        model.addAttribute("키 이름", "데이터, 값");
        return "파일이름"; // templates.파일이름.html을 렌더링
    }
}
