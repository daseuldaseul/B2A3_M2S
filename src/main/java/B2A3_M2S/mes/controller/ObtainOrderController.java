package B2A3_M2S.mes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ObtainOrderController {

    @GetMapping("/obtainOrder")
    public String ObtainOrder(Model model) {



        return "obtainOrderPage";
    }
}
