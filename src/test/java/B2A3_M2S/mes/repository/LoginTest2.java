package B2A3_M2S.mes.repository;


import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Routing;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

//@RunWith(SpringRunner.class)
//@DataJpaTest
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

        bomRepository.findBypItem("P_002", 1200L).stream().forEach(System.out::println);
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------");
        bomRepository.findNeedQtyBypItem("P_002", 1200).stream().forEach(System.out::println);
    }


    @Autowired
    private LotNoLogRepository repository;
    @Autowired
    private ProductionRepository productionRepository;
    @Test
    public void logNoTest() {
       /* Item ii = Item.builder()
                .itemCd("P_001")
                .build();
        List<RoutingDto> rList = routingRepository.findByItem(ii).stream().map(RoutingDto::of).collect(Collectors.toList());
        System.out.println("뭐고");
        rList.stream().forEach(a -> System.out.println(repository.createLotNo(a.getProcesses().getProcCd())));*/
        System.out.println("여기:" + productionRepository.findByMaxEndDate());
        System.out.println("여기:");
    }
}
