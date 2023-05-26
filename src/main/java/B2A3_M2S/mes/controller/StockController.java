package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService service;

    @RequestMapping("/list")
    public String stockList(Model model) {

       // model.addAttribute("stockList" ,  service.getStockList());

        return "stock";
    }

}
