package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ProductionService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductionPlanController {
    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    ProductionService productionService;

    @GetMapping("/production")
    public String productionPlan(Model model){
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ProductionDTO> productionDtoList = new ArrayList<>();

        for(ObtainOrder obtainOrder : obtainOrderList) {
            List<Production> productionPlan = productionRepository.findByObtainOrderOrderCd(obtainOrder.getOrderCd());
            productionDtoList.addAll(ProductionDTO.of(productionPlan));
        }

        model.addAttribute("productionList", productionDtoList);
        model.addAttribute("obtainOrderList", obtainOrderList);
        return "productionPlanPage";
    }



    @ResponseBody
    @GetMapping("/production/detail")
    public String productionDetail(@RequestParam String orderCd, Model model) {

        Gson gson = new Gson();
        ObtainOrder obtainOrder = obtainOrderRepository.findSingleByOrderCd(orderCd);
        List<Production> production = productionRepository.findByObtainOrder(obtainOrder);
        List<ProductionDTO> productionDTOList = ProductionDTO.of(production);



        String json = gson.toJson(productionDTOList);
        return json;
    }


    @GetMapping("/production/search")
    public String productionSearch( @RequestParam(required = false) String orderCd,
                                    @RequestParam(required = false) String itemNm,
                                    @RequestParam(required = false) String itemCd,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueStartDate,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueEndDate,
                                    Model model) {

        LocalDateTime dueStartDateTime = null;
        LocalDateTime dueEndDateTime = null;

        if(dueStartDate != null && dueEndDate != null) {
            dueStartDateTime = LocalDateTime.of(dueStartDate, LocalTime.MIN);
            dueEndDateTime = LocalDateTime.of(dueEndDate, LocalTime.MAX);
        }

        List<ObtainOrder> obtainOrderList = productionService.searchProduction(orderCd, itemNm, itemCd, dueStartDateTime, dueEndDateTime);
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);

        model.addAttribute("obtainOrderList", obtainOrderDtoList);

        return "productionPlanPage";
    }

}
