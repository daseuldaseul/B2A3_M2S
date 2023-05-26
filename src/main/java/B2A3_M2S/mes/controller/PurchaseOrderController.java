package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.CompanyDto;
import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.PurchaseOrderDto;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.PurchaseOrder;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.repository.PurchaseOrderRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.PurchaseOrderService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
public class PurchaseOrderController {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @GetMapping("/purchaseOrder")
    public String purchaseOrder(Model model){
        List<PurchaseOrderDto> purchaseOrderList = PurchaseOrderDto.of(purchaseOrderRepository.findAll());

        for(PurchaseOrderDto orders : purchaseOrderList){
            orders.setPurchaseStateNm(CodeServiceImpl.getCodeNm("PURCHASE_STATE", orders.getPurchaseState()));
        }
        model.addAttribute("purchaseOrderList", purchaseOrderList);
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PURCHASE_STATE"));
        return "purchaseOrderPage";
    }

    @GetMapping("/purchaseOrder/search")
    public String purchaseOrderSearch(@RequestParam String companyCd,
                                      @RequestParam String companyNm,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                      @RequestParam String purchaseState,
                                      @RequestParam String itemCd,
                                      @RequestParam String itemNm,
                                      @RequestParam String orderNo,
                                      Model model){
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null) {
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
        }

        List<PurchaseOrder> purchaseOrders = purchaseOrderService.searchPurchaseOrder(companyCd, companyNm, purchaseState, itemCd, itemNm, orderNo, startDateTime, endDateTime);
        List<PurchaseOrderDto> purchaseOrderList = PurchaseOrderDto.of(purchaseOrders);

        for(PurchaseOrderDto orders : purchaseOrderList){
            orders.setPurchaseStateNm(CodeServiceImpl.getCodeNm("PURCHASE_STATE", orders.getPurchaseState()));
        }
        model.addAttribute("purchaseOrderList", purchaseOrderList);
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PURCHASE_STATE"));
        return "purchaseOrderPage";

    }

    @ResponseBody
    @GetMapping("/purchaseOrder/detail")
    public String purchaseOrderDetail(@RequestParam String orderNo, Model model) {

        Gson gson = new Gson();
        PurchaseOrderDto purchaseOrderDto = PurchaseOrderDto.of(purchaseOrderRepository.findByOrderNo(orderNo));
        purchaseOrderDto.setPurchaseStateNm(CodeServiceImpl.getCodeNm("PURCHASE_STATE", purchaseOrderDto.getPurchaseState()));
        String json = gson.toJson(purchaseOrderDto);
        return json;
    }



}
