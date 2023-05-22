package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ObtainOrderFormDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.service.ObtainOrderService;
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
import java.util.List;

@Controller
public class ObtainOrderController {
    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    ObtainOrderService obtainOrderService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ItemRepository itemRepository;

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
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);

        model.addAttribute("obtainOrderList", obtainOrderDtoList);
        return "obtainOrderPage";
    }

    @ResponseBody
    @GetMapping("/obtainOrder/detail")
    public String obtainOrderDetail(@RequestParam String orderCd, Model model) {

        Gson gson = new Gson();
        System.out.println("-------------------------------------------");
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findByOrderCd(orderCd);
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);

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
        String json = gson.toJson(obtainOrderDtoList);
        return json;
    }

    @GetMapping("/obtainOrder/search")
    public String obtainOrderSearch(@RequestParam String companyCd,
                                @RequestParam String companyNm,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @RequestParam String orderState,
                                @RequestParam String itemCd,
                                @RequestParam String itemNm,
                                @RequestParam String orderCd,
                                Model model) {

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null) {
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
        }

        List<ObtainOrder> obtainOrderList = obtainOrderService.searchObtainOrder(companyCd, companyNm, startDateTime, endDateTime, orderState, itemCd, itemNm, orderCd);
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);

        model.addAttribute("obtainOrderList", obtainOrderDtoList);

        return "obtainOrderPage";
    }

    @GetMapping("/obtainOrder/autoComplete")
    @ResponseBody
    public String obtainOrderAutoComplete(@RequestParam("text") String text) {

        Gson gson = new Gson();
        System.out.println(text);
        System.out.println("-------------------------------------------");
        List<Company> obtainOrderList = companyRepository.findByCompanyNmContaining(text);
        System.out.println(obtainOrderList);

        String json = gson.toJson(obtainOrderList);
        return json;
    }

    @GetMapping("/obtainOrder/autoComplete2")
    @ResponseBody
    public String obtainOrderAutoComplete2(@RequestParam("text") String text) {

        Gson gson = new Gson();
        System.out.println(text);
        System.out.println("-------------------------------------------");
        List<Item> obtainOrderList = itemRepository.findByItemNmContainingAndItemCdContaining(text, "P");
        System.out.println(obtainOrderList);

        String json = gson.toJson(obtainOrderList);
        return json;
    }

}
