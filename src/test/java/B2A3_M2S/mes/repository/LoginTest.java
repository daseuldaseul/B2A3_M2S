package B2A3_M2S.mes.repository;


import B2A3_M2S.mes.dto.CodeSetDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.service.CodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class LoginTest {
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

    double cabbage = 1000000;
    double cabbageExtract = 0;
    double cabbageJuice = 0;
    double cabbageJuiceBox = 0;
    double rQty = 0;

    @Test
    public void test() {
        //계량 -> 세척 -> 추출-> 가열 -> 충진 -> 검사 ->식힘 -> 포장

        /*/ 실제 돌릴때 로직
        // 양배추즙
        Item item = new Item();
        item.setItemCd("P_001");

        // 수주 불러옴
        ObtainOrderDto oDto = ObtainOrderDto.of(obtainOrderRepository.findByOrderCd("SO052300001").get(0));

        // Routing 조회
        rList = routingRepository.findByItem(oDto.getItem()).stream().map(RoutingDto::of).collect(Collectors.toList());
        rList.stream().forEach(System.out::println);
        
        // bom 조회
        bList = bomRepository.findBypItem(item.getItemCd(), 1000);
        bList.stream().forEach(System.out::println);



          //  BOM에서 나온 소모량을 기준으로 재고 조회 후 자동 발주, D-Day 재료에 따라 ++
          //  이후
          //   생산계획 수립 및 생산
         */


        // 생산 수량

        // 소요시간
        cabbage = proc1(cabbage);
        cabbage = proc2(cabbage);
        cabbageExtract = proc3_1(cabbage);
        cabbageExtract = proc4_1(cabbageExtract);
        cabbageJuice = proc5(cabbageExtract);
        cabbageJuice = proc6(cabbageJuice);
        cabbageJuice = proc7(cabbageJuice);
        cabbageJuiceBox = proc8(cabbageJuice);

        System.out.println("총 생산수량(box): " + cabbageJuiceBox);
        System.out.println("미포장 양배추즙 수량: " + rQty);
        System.out.println("총 작업시간: " + workTime + "분");
        System.out.println("총 리드타임: " + leadTime + "분");
        System.out.println("예상납기일 : " + LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes((int)(workTime + leadTime)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
    }
    public double proc1(double inputQty) {
        System.out.println("계량공정 시작 (투입량): " + inputQty);

        leadTime += 20;
        workTime += 30;

        double outputQty = inputQty;
        System.out.println("계량공정 종료 (생산수량): " + outputQty);

        return outputQty;
    }

    public double proc2(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        System.out.println("전처리 시작 (투입량): " + inputQty);

        do {
            leadTime += 20;

            if(temp >= 1000000) {
                workTime += 60;
                temp -= 1000000;
                outputQty += 1000000;
            } else {
                int num = (int) Math.ceil(temp / 50000);
                workTime += (num * 3);
                outputQty += temp;
                temp = 0;
            }
        } while (temp > 0); // 조건식
        System.out.println("전처리 종료 (생산수량): " + outputQty);
        return inputQty;
    }

    public double proc3_1(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        double waterQty = inputQty;

        do {
            leadTime += 60;

            if(temp >= 1000000) {
                System.out.println("추출공정_1 시작 (투입량): 1000000, 정제수: 1000000" );
                workTime += 2880;
                outputQty += (2000000 * 0.8);
                temp -= 1000000;
                waterQty -= 1000000;

                if(temp >= 1000000) {
                    outputQty += proc3_2(1000000) ;
                    temp -= 1000000;
                    waterQty -= 1000000;
                } else {
                    outputQty += proc3_2(temp);
                    temp = 0;
                }
            } else {
                System.out.println("추출공정_1 시작 (투입량): " + temp + ", 정제수: " + waterQty);
                int num = (int) Math.ceil(temp / 25000);
                workTime += (num * 36);
                outputQty += ((temp + waterQty) * 0.8);
                temp = 0;
                waterQty = 0;
            }
            System.out.println("추출공정_1 종료 (생산수량): " + outputQty);
            System.out.println("추출공정 (남은수량): " + temp);
        } while (temp > 0); // 조건식


        return outputQty;
    }
    public double proc3_2(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        double waterQty = inputQty;

        do {
            if(temp >= 1000000) {
                System.out.println("추출공정_2 시작 (투입량): 1000000, 정제수: 1000000" );

                outputQty += (2000000 * 0.8);
                temp -= 1000000;
                waterQty -= 1000000;
            } else {
                System.out.println("추출공정_2 시작 (투입량): " + temp + ", 정제수: " + waterQty);

                int num = (int) Math.ceil(temp / 25000);
                outputQty += ((temp + waterQty) * 0.8);
                temp = 0;
                waterQty = 0;
            }
            System.out.println("추출공정 종료_2 (생산수량): " + outputQty);
        } while (temp > 0); // 조건식


        return outputQty;
    }

    public double proc4_1(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;

        do {
            workTime += 1440;

            if(temp >= 2000000) {
                System.out.println("가열공정_1 시작 (투입량): " + 2000000);
                leadTime += 20;
                temp -= 2000000;
                outputQty += 2000000;

                if(temp >= 2000000) {
                    outputQty += proc4_2(2000000);
                    temp -= 2000000;
                } else {
                    outputQty += proc4_2(temp);
                    temp = 0;
                }
            } else {
                System.out.println("가열공정_1 시작 (투입량): " + temp);
                outputQty += temp;
                temp = 0;
            }
            System.out.println("가열공정_1 종료 (생산수량): " + outputQty);
            System.out.println("가열공정 (남은수량): " + temp);
        } while (temp > 0); // 조건식
        return outputQty;
    }
    public double proc4_2(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;

        do {
            if(temp >= 2000000) {
                System.out.println("가열공정_2 시작 (투입량): " + 2000000);
                temp -= 2000000;
                outputQty += 2000000;
            } else {
                System.out.println("가열공정_2 시작 (투입량): " + temp);
                outputQty += temp;
                temp = 0;
            }
            System.out.println("가열공정_2 종료 (생산수량): " + outputQty);
        } while (temp > 0); // 조건식


        return outputQty;
    }

    public double proc5(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        System.out.println("충진공정 시작 (투입량): " + inputQty);

        leadTime += 20;
        outputQty += Math.floor(temp / 80);
        num = (int) Math.ceil(outputQty / 175);
        workTime += (num * 6);

        System.out.println("충진공정 종료 (생산수량): " + inputQty);
        return outputQty;
    }

    public double proc6(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        System.out.println("검사공정 시작 (투입량): " + inputQty);
        leadTime += 10;

        num = (int) Math.ceil(temp / 250);
        workTime += (num * 3);
        outputQty = temp;

        System.out.println("검사공정 종료 (생산수량): " + inputQty);
        return outputQty;
    }

    public double proc7(double inputQty) {
        System.out.println("식힘공정 시작 (투입량): " + inputQty);
        leadTime += 0;
        workTime += 1440;
        System.out.println("식힘공정 종료 (생산수량): " + inputQty);
        return inputQty;
    }

    public double proc8(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        leadTime += 20;
        System.out.println("포장공정 시작 (투입량): " + inputQty);

        num = (int)(temp / 30);
        rQty = (temp % 30);
        workTime += Math.ceil((num * 0.3));
        outputQty += (int)(temp / 30);

        System.out.println("포장공정 종료 (생산수량): " + outputQty);
        return outputQty;
    }
}
