package B2A3_M2S.mes.Service;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.PurchaseOrderDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.PurchaseOrderRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MainService {

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;





    public List<ObtainOrderDto> getObtainOrderDtoList() {

        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);
        for(ObtainOrderDto obtainOrderDto : obtainOrderDtoList){
            obtainOrderDto.setOrderUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", obtainOrderDto.getOrderUnit()));
            obtainOrderDto.setOrderStateNm(CodeServiceImpl.getCodeNm("OBTAIN_STATE" , obtainOrderDto.getOrderState()));
            obtainOrderDto.setProgressPercent(progressPercent(obtainOrderDto.getOrderDate() , obtainOrderDto.getDueDate()));
        }


        return obtainOrderDtoList;
    }

    public List<PurchaseOrderDto> getPurchaseOrderDtoList(){

        List<PurchaseOrderDto> purchaseOrderList = PurchaseOrderDto.of(purchaseOrderRepository.findAll());

        for(PurchaseOrderDto orders : purchaseOrderList){
            orders.setPurchaseStateNm(CodeServiceImpl.getCodeNm("PURCHASE_STATE", orders.getPurchaseState()));




        }

        return purchaseOrderList;

    }

    public int progressPercent(LocalDateTime startDate , LocalDateTime dueDate ){


        System.out.println(startDate);
        System.out.println(dueDate);
        LocalDateTime currentDate = LocalDateTime.now();  // 현재 날짜 가져오기

        // 시작날짜부터 종료날짜까지의 총 일수 계산
        long totalDays = ChronoUnit.DAYS.between(startDate, dueDate);
        System.out.println("total days" + totalDays);
        // 시작날짜부터 현재 날짜까지의 경과 일수 계산

        currentDate = currentDate.plusDays(2);
        long elapsedDays = ChronoUnit.DAYS.between(startDate, currentDate);

        // 진행된 백분율 계산
        double progressPercent = (double) elapsedDays / totalDays * 100;

        int progressPercentInt = (int) Math.round(progressPercent);

        return  progressPercentInt;

    }



}
