package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ObtainOrderFormDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.service.ObtainOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ObtainOrderController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ObtainOrderService obtainOrderService;

    @GetMapping("/obtainOrder")
    public String ObtainOrder(Model model) {
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
//        int x = 20;    //박스 수
//        double min = 3060 + 6.1 * (double)x;
//        min = Math.ceil(min);
//        System.out.println("시간!!!!!!!!!!!!!!!!!!!!!!"+min);
//        LocalDateTime result = now.plusMinutes((int)min);
//
//        System.out.println(result);
//        //3060 + 6.1 * 박스수

        return "obtainOrderPage";

    }

    @PostMapping("/obtainOrder")
    public String obtainOrderWrite(ObtainOrderFormDto obtainOrderFormDto, String companyNm, String itemNm, Model model){
//        obtainOrderFormDto.setCompany(companyRepository.findByCompanyNm(companyNm));
//        obtainOrderFormDto.setItem(itemRepository.findByItemNm(itemNm));
//        obtainOrderFormDto.setOrderCd("code_" + itemNm + obtainOrderRepository.count());
//        double min = 3060 + 6.1 * (double) obtainOrderFormDto.getQty();
//        min = Math.ceil(min);
//        obtainOrderFormDto.setOrderDate(LocalDateTime.now());
//        obtainOrderFormDto.setDueDate(obtainOrderFormDto.getOrderDate().plusMinutes((int)min));
        ObtainOrderFormDto result = obtainOrderService.writeObtainOrder(obtainOrderFormDto, companyNm, itemNm);
        ObtainOrder obtainOrder = new ObtainOrder();

        obtainOrder = result.createObtainOrder();

        obtainOrderRepository.save(obtainOrder);

        return "obtainOrderPage";
    }
}
