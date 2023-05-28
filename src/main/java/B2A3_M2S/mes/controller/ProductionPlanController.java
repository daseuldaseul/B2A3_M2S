package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductionPlanController {
    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

//    @GetMapping("/productionPlan")
//    public String productionPlan(Model model){
//        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
//        List<Production> productionPlan = productionRepository.findByObtainOrder(obtainOrderList);
//        List<ProductionDTO> productionDtoList = ProductionDTO.of(productionPlan);
//
//        if(!productionDtoList.isEmpty()) {
//            ProductionDTO firstProductionDto = productionDtoList.get(0);
//            ProductionDTO lastProductionDto = productionDtoList.get(productionDtoList.size() - 1);
//
//            // 가져온 객체를 모델에 추가
//            model.addAttribute("firstProductionDto", firstProductionDto);
//            model.addAttribute("lastProductionDto", lastProductionDto);
//
//        }
//
//            model.addAttribute("productionList", productionDtoList);
//
//        model.addAttribute("obtainOrderList", obtainOrderList);
//        return "productionPlanPage";
//    }

}
