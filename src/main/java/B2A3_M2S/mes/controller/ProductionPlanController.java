package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductionPlanController {
    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @GetMapping("/productionPlan")
    public String productionPlan(Model model){
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ProductionDTO> productionDtoList = new ArrayList<>();

        for(ObtainOrder obtainOrder : obtainOrderList) {
            List<Production> productionPlan = productionRepository.findByObtainOrderOrderCd(obtainOrder.getOrderCd());
            productionDtoList.addAll(ProductionDTO.of(productionPlan));
        }


//        if (!productionDtoList.isEmpty()) {
//            List<ProductionDTO> firstProductionDto = new ArrayList<>();
//            firstProductionDto.add(productionDtoList.get(0));
//
//            List<ProductionDTO> lastProductionDto = new ArrayList<>();
//            lastProductionDto.add(productionDtoList.get(productionDtoList.size() - 1));
//
//            model.addAttribute("firstProductionDto", firstProductionDto);
//            model.addAttribute("lastProductionDto", lastProductionDto);
//        }

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
}
