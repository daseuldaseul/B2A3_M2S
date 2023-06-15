package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class MainController {

    @Autowired
    MainService mainService;


    @GetMapping("/main")
    public String home(Model model){




        model.addAttribute("orderList",mainService.getObtainOrderDtoList());
        model.addAttribute("purchaseList",mainService.getPurchaseOrderDtoList());
        model.addAttribute("processes" , mainService.getProcessesPercent());
        model.addAttribute("dailyProduction" , mainService.getDailyProduction());
        model.addAttribute("monthlyProduction", mainService.getMonthlyProduction());

        System.out.println("들어옴12321 " + mainService.getProcessesPercent());

        return "main";
    }
}
