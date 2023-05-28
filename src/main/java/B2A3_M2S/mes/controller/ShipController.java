package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ShipDto;
import B2A3_M2S.mes.entity.Ship;
import B2A3_M2S.mes.repository.ShipRepository;
import B2A3_M2S.mes.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ShipController {
    @Autowired
    ShipRepository shipRepository;

    @Autowired
    ShipService shipService;

    @GetMapping("/ship")
    public String ship(Model model){
        List<Ship> ships = shipRepository.findAll();
        List<ShipDto> shipList = ShipDto.of(ships);
        model.addAttribute("shipList", shipList);
        return "shipPage";
    }

    @GetMapping("/ship/search")
    public String searchShip(@RequestParam String companyCd,
                             @RequestParam String companyNm,
                             @RequestParam String obtainOrderCd,
                             @RequestParam String shipNo,
                             @RequestParam String itemCd,
                             @RequestParam String itemNm,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startOrderDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endOrderDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startShipDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endShipDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDueDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDueDate,
                             Model model){

        List<ShipDto> shipList = ShipDto.of(shipService.searchShip(shipNo, companyCd, companyNm, obtainOrderCd, itemCd, itemNm, startOrderDate, endOrderDate, startShipDate, endShipDate, startDueDate, endDueDate));
        model.addAttribute("shipList", shipList);
        return "shipPage";
    }
}
