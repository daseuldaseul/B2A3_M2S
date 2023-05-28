package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.service.ProductionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class testController {
    @Autowired
    ProductionServiceImpl productionService;

    @GetMapping("/testtest")
    public String testControlerrrr(Model model) {
        LocalDateTime startTime = LocalDateTime.now();
        String obtainOrderCd = "SO-230527-00003";
//        productionService.cabbageCalculator(startTime, obtainOrderCd);
//        productionService.blackGarlicCalculator();
//        productionService.stickCalculator();

        return "test";
    }
}
