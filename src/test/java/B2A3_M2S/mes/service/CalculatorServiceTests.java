package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.repository.BOMRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import B2A3_M2S.mes.util.enums.TestEnum;
import B2A3_M2S.mes.util.service.UtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class CalculatorServiceTests {
    @Autowired
    private B2A3_M2S.mes.service.CalculatorService service;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    MainService mainService;

    @Autowired
    UtilService utilService;

    @Autowired
    ProductionRepository productionRepository;

    @Autowired
    CalculatorService calculatorService;

    @Autowired
    ItemRepository repository;
    @Autowired
    StockService stockService;

    @Test
    public void test() {
        System.out.println("계산기 테스트 시작합니다.");

        Item item = repository.findByItemNm("흑마늘");
        WarehouseLogDTO wDto;
        wDto = stockService.addMaterials(item , 100000L);
        utilService.saveReceiving(wDto);
        wDto = stockService.addMaterials(item , 100000L);
        utilService.saveReceiving(wDto);
        wDto = stockService.addMaterials(item , 100000L);
        utilService.saveReceiving(wDto);

        item = repository.findByItemNm("파우치");
        wDto = stockService.addMaterials(item , 30000L);
        utilService.saveReceiving(wDto);

        item = repository.findByItemNm("박스");
        wDto = stockService.addMaterials(item , 1000L);
        utilService.saveReceiving(wDto);

        ObtainOrderDto odt = ObtainOrderDto.of(obtainOrderRepository.findByOrderCd("SO23061400001").get(0));
        service.getDeliveryDate(LocalDateTime.now(), odt);
    }

    @Test
    public void test2() {
        calculatorService.schedulerApplication();
    }

    @Test
    public void test3() {
        //utilService.saveInput(productionRepository.findByStartDateAndEndDateAndStatus().stream().map(ProductionDTO::of).collect(Collectors.toList()));
    }

    @Test
    public void progressPercentTest() {
        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);
        for (ObtainOrderDto obtainOrderDto : obtainOrderDtoList) {
            obtainOrderDto.setOrderUnitNm(B2A3_M2S.mes.service.CodeServiceImpl.getCodeNm("UNIT_TYPE", obtainOrderDto.getOrderUnit()));
            obtainOrderDto.setOrderStateNm(B2A3_M2S.mes.service.CodeServiceImpl.getCodeNm("OBTAIN_STATE", obtainOrderDto.getOrderState()));
            obtainOrderDto.setProgressPercent(mainService.progressPercent(obtainOrderDto.getOrderDate(), obtainOrderDto.getDueDate()));
            System.out.println(obtainOrderDto.getProgressPercent());
        }
    }

    @Test
    public void testEnum() {
        System.out.println("--------------------");
        System.out.println("--------------------");
        System.out.println(TestEnum.TEST1);
        System.out.println(TestEnum.TEST2);
        System.out.println(TestEnum.TEST3);
        System.out.println("--------------------");
        System.out.println("--------------------");

        System.out.println(TestEnum.TEST1.getA());
        System.out.println(TestEnum.TEST2.getA());
        System.out.println(TestEnum.TEST3.getA());

        System.out.println("--------------------");
        System.out.println("--------------------");

        TestEnum.TEST1.setA(1);

        System.out.println(TestEnum.TEST1.getA());
        System.out.println(TestEnum.TEST2.getA());
        System.out.println(TestEnum.TEST3.getA());

        System.out.println("--------------------");
        System.out.println("--------------------");
    }
}
