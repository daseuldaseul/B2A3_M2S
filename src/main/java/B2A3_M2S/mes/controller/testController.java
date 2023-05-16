package B2A3_M2S.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("/main")
    public String home(){

        return "main";
    }

    @GetMapping("/test")
    public String test(){

        return "data";
    }
}
