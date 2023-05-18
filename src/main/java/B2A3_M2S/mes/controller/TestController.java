package B2A3_M2S.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/test")
    public String ex(){
        return "main";
    }

    @GetMapping("/test2")
    public String ex2(){
        return "data";
    }
}
