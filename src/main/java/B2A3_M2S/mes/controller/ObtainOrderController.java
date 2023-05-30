package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.ObtainOrderFormDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    PurchaseOrderService purchaseOrderService;
    @Autowired
    ObtainOrderService obtainOrderService;

    @Autowired
    ProductionCalService productionService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private CalculatorService service;

    @Autowired
    CalculatorServiceImpl calculatorService;


    @GetMapping("/obtainOrder")
    public String ObtainOrder(Model model) {
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);
        for(ObtainOrderDto obtainOrderDto : obtainOrderDtoList){
              obtainOrderDto.setOrderUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", obtainOrderDto.getOrderUnit()));
        }
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("OBTAIN_STATE"));
        model.addAttribute("obtainOrderList", obtainOrderDtoList);
        return "obtainOrderPage";
    }

    @ResponseBody
    @GetMapping("/obtainOrder/detail")
    public String obtainOrderDetail(@RequestParam String orderCd, Model model) {
        Gson gson = new Gson();
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findByOrderCd(orderCd);
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);
        for(ObtainOrderDto obtainOrderDto : obtainOrderDtoList){
            obtainOrderDto.setOrderStateNm(CodeServiceImpl.getCodeNm("OBTAIN_STATE", obtainOrderDto.getOrderState()));
        }

        String json = gson.toJson(obtainOrderDtoList);
        return json;
    }

    @PostMapping("/obtainOrder")
    public String obtainOrderWrite(ObtainOrderFormDto obtainOrderFormDto, String companyNm, String itemNm, Model model){
        ObtainOrderFormDto result = obtainOrderService.writeObtainOrder(obtainOrderFormDto, companyNm, itemNm);
        ObtainOrder obtainOrder = new ObtainOrder();
        obtainOrder = result.createObtainOrder();
        obtainOrderRepository.save(obtainOrder);

        LocalDateTime time = purchaseOrderService.purchaseOrderCreate(itemNm,(int)(long)obtainOrderFormDto.getQty());
        // 입고예정시간 중 가장 늦은 시간 = time
        LocalDateTime start = calculatorService.getDeliveryDate(time, ObtainOrderDto.of(obtainOrder));
//        LocalDateTime startTime = LocalDateTime.now();
//        int qty = purchaseOrderService.needQty(itemNm,(int)(long)obtainOrderFormDto.getQty());
        obtainOrder.setDueDate(start);
        obtainOrderRepository.save(obtainOrder);
//        productionService.calculate(itemRepository.findByItemNm(itemNm).getItemCd()
//                , startTime, obtainOrderFormDto.getOrderCd(), qty);

//        service.getDeliveryDate();

        return "redirect:/obtainOrder";
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





}
