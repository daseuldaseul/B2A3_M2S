package B2A3_M2S.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping(value = "/")
    public String new1() {
        return "data";
    }
}
