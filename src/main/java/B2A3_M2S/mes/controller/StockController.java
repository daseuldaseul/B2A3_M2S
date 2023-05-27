package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.StockDto;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/stock")
public class StockController {

    @Autowired
    StockService service;

    @RequestMapping("/list")
    public String stockList(Model model) {

        List<Stock>  stockList = service.getStockList();

        List<StockDto> stockDtoList = StockDto.of(stockList);
        for(StockDto stock : stockDtoList) {
            stock.getItem().setItemUnitValue(CodeServiceImpl.getCodeNm("UNIT_TYPE" , stock.getItem().getItemUnit()));
        }


        model.addAttribute("stockList" , stockDtoList );

        return "stock";
    }

}
