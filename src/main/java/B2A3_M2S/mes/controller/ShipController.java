package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.dto.ShipDto;
import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ShipRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ShipService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ShipController {
    @Autowired
    ShipRepository shipRepository;

    @Autowired
    ShipService shipService;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @GetMapping("/ship")
    public String ship(Model model){
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);

        model.addAttribute("obtainOrderList", obtainOrderDtoList);
        return "shipPage";
    }

    @GetMapping("/ship/search")
    public String searchShip(@RequestParam String companyCd,
                             @RequestParam String companyNm,
                             @RequestParam String obtainOrderCd,
                             @RequestParam String itemCd,
                             @RequestParam String itemNm,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startOrderDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endOrderDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDueDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDueDate,
                             Model model){

        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(shipService.searchShip(companyCd, companyNm, obtainOrderCd, itemCd, itemNm, startOrderDate, endOrderDate, startDueDate, endDueDate));
        model.addAttribute("obtainOrderList", obtainOrderDtoList);
        return "shipPage";
    }

    @ResponseBody
    @GetMapping("/ship/detail")
    public String shipDetail(@RequestParam String orderCd, Model model) {

        Gson gson = new Gson();

        ObtainOrder obtainOrder = obtainOrderRepository.findSingleByOrderCd(orderCd);
        List<Ship> shipList = shipRepository.findByObtainOrder(obtainOrder);
        List<ShipDto> shipDtoList = ShipDto.of(shipList);

        String json = gson.toJson(shipDtoList);
        return json;
    }
}
