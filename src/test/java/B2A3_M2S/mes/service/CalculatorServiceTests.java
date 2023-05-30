package B2A3_M2S.mes.service;

import B2A3_M2S.mes.Service.MainService;
import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class CalculatorServiceTests {
    @Autowired
    private B2A3_M2S.mes.service.CalculatorService service;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    MainService mainService;

    @Test
    public void test() {
        System.out.println("계산기 테스트 시작합니다.");
        service.getDeliveryDate(LocalDateTime.now(), null);
    }

    @Test
    public void progressPercentTest() {

        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);
        for(ObtainOrderDto obtainOrderDto : obtainOrderDtoList){
            obtainOrderDto.setOrderUnitNm(B2A3_M2S.mes.service.CodeServiceImpl.getCodeNm("UNIT_TYPE", obtainOrderDto.getOrderUnit()));
            obtainOrderDto.setOrderStateNm(B2A3_M2S.mes.service.CodeServiceImpl.getCodeNm("OBTAIN_STATE" , obtainOrderDto.getOrderState()));
            obtainOrderDto.setProgressPercent( mainService.progressPercent(obtainOrderDto.getOrderDate() , obtainOrderDto.getDueDate()));
            System.out.println(obtainOrderDto.getProgressPercent());
        }


    }

}
