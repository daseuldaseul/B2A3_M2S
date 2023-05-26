package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.entity.Stock;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class StockController {

    @RequestMapping("stock/list")
    public String stockList() {




        return "stock";
    }

}
