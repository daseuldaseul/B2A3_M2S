package B2A3_M2S.mes.repository;


import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.BOM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
public class LoginTest2 {
    @Autowired
    private ProcessesRepository procRepository;
    @Autowired
    private RoutingRepository routingRepository;
    @Autowired
    private BOMRepository bomRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private EquipRepository equipRepository;
    @Autowired
    private ObtainOrderRepository obtainOrderRepository;

    List<RoutingDto> rList;
    List<BOM> bList;

    double workTime = 0;
    double leadTime = 0;

    double cabbage = 1863217897;
    double cabbageExtract = 0;
    double cabbageJuice = 0;
    double cabbageJuiceBox = 0;
    double rQty = 0;

    @Test
    public void test() {
        //계량 -> 세척 -> 추출-> 가열 -> 충진 -> 검사 ->식힘 -> 포장



        bomRepository.findBypItem("P_002", 1200).stream().forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        bomRepository.finNeedQtyBypItem("P_002", 1200).stream().forEach(System.out::println);


    }
}
