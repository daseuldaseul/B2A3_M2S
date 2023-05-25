package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ProductionService {
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

    double cabbage = 2000000;
    double blackGarlic = 600000;
    double cabbageExtract = 0;
    double blackGarlicExtract = 0;
    double cabbageJuice = 0;
    double blackGarlicJuice = 0;
    double cabbageJuiceBox = 0;
    double blackGarlicJuiceBox = 0;
    double rQty = 0;

    public void cabbageCalculator() {
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
        cabbage = cabbageProc1(cabbage);
        cabbage = cabbageProc2(cabbage);
        cabbageExtract = cabbageProc3_1(cabbage);
        cabbageExtract = cabbageProc4_1(cabbageExtract);
        cabbageJuice = cabbageProc5(cabbageExtract);
        cabbageJuice = cabbageProc6(cabbageJuice);
        cabbageJuice = cabbageProc7(cabbageJuice);
        cabbageJuiceBox = cabbageProc8(cabbageJuice);

        System.out.println("총 생산수량(box): " + cabbageJuiceBox);
        System.out.println("미포장 양배추즙 수량: " + rQty);
        System.out.println("총 작업시간: " + workTime + "분");
        System.out.println("총 리드타임: " + leadTime + "분");
        System.out.println("예상납기일 : " + LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes((int)(workTime + leadTime)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
    }
    public double cabbageProc1(double inputQty) {
        System.out.println("계량공정 시작 (투입량): " + inputQty);

        leadTime += 20;
        workTime += 30;

        double outputQty = inputQty;
        System.out.println("계량공정 종료 (생산수량): " + outputQty);
        System.out.println("계량공정 종료 (작업시간): " + workTime);
        System.out.println("계량공정 종료 (준비시간): " + leadTime);

        return outputQty;
    }

    public double cabbageProc2(double inputQty) {
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
                int num = (int) Math.ceil(temp / 50000); // 50kg(50000g)/3min
                workTime += (num * 3);
                outputQty += temp;
                temp = 0;
            }
        } while (temp > 0); // 조건식

        System.out.println("전처리 종료 (생산수량): " + outputQty);
        System.out.println("전처리 종료 (작업시간): " + workTime);
        return inputQty;
    }

    public double cabbageProc3_1(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        double waterQty = inputQty;

        do {
            leadTime += 60;

            if(temp >= 1000000) {
                System.out.println("추출공정_1 시작 (투입량): 1000000, 정제수: 1000000" );
                workTime += 2880; // 48h
                outputQty += (2000000 * 0.8); // 1t + 1t = 2t * 80%
                temp -= 1000000;
                waterQty -= 1000000;

                // 2열 가동
                if(temp >= 1000000) {
                    outputQty += cabbageProc3_2(1000000) ;
                    temp -= 1000000;
                    waterQty -= 1000000;
                } else {
                    outputQty += cabbageProc3_2(temp);
                    temp = 0;
                }
            }
            else { // 1열만 사용
                System.out.println("추출공정_1 시작 (투입량): " + temp + ", 정제수: " + waterQty);
                int num = (int) Math.ceil(temp / 25000); // 25L / 36min
                workTime += (num * 36);
                outputQty += ((temp + waterQty) * 0.8);
                temp = 0;
                waterQty = 0;
            }
            System.out.println("추출공정_1 종료 (생산수량): " + outputQty);
            System.out.println("추출공정 (남은수량): " + temp);
            System.out.println("추출공정 (작업시간): " + workTime);
            System.out.println("추출공정 (준비시간): " + leadTime);
        } while (temp > 0); // 조건식

        return outputQty;
    }
    public double cabbageProc3_2(double inputQty) {
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

                int num = (int) Math.ceil(temp / 25000); // 25L /36min
                outputQty += ((temp + waterQty) * 0.8);
                temp = 0;
                waterQty = 0;
            }
            System.out.println("추출공정 종료_2 (생산수량): " + outputQty);
            System.out.println("추출공정 종료_2 (작업시간): " + workTime);
            System.out.println("추출공정 종료_2 (준비시간): " + leadTime);
        } while (temp > 0); // 조건식


        return outputQty;
    }

    public double cabbageProc4_1(double inputQty) {
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
                    outputQty +=  cabbageProc4_2(2000000);
                    temp -= 2000000;
                } else {
                    outputQty += cabbageProc4_2(temp);
                    temp = 0;
                }
            } else {
                System.out.println("가열공정_1 시작 (투입량): " + temp);
                outputQty += temp;
                temp = 0;
            }
            System.out.println("가열공정_1 종료 (생산수량): " + outputQty);
            System.out.println("가열공정_1 종료 (작업시간): " + workTime);
            System.out.println("가열공정_1 종료 (준비시간): " + leadTime);
            System.out.println("가열공정 (남은수량): " + temp);
        } while (temp > 0); // 조건식


        return outputQty;
    }
    public double cabbageProc4_2(double inputQty) {
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
            System.out.println("가열공정_2 종료 (작업시간): " + workTime);
            System.out.println("가열공정_2 종료 (준비시간): " + leadTime);
        } while (temp > 0); // 조건식


        return outputQty;
    }

    public double cabbageProc5(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        System.out.println("충진공정 시작 (투입량): " + inputQty);

        leadTime += 20;
        outputQty += Math.floor(temp / 80); // 양배추즙 1포 = 80ml
        num = (int) Math.ceil(outputQty / 175); // 175ea / 6min
        workTime += (num * 6);

        System.out.println("충진공정 종료 (생산수량): " + outputQty); // 이거 왜 inputQty지
        System.out.println("충진공정 종료 (작업시간): " + workTime); // 이거 왜 inputQty지
        System.out.println("충진공정 종료 (준비시간): " + leadTime);
        return outputQty;
    }

    public double cabbageProc6(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        System.out.println("검사공정 시작 (투입량): " + inputQty);
        leadTime += 10;

        num = (int) Math.ceil(temp / 250); // 250ea/3min
        workTime += (num * 3);
        outputQty = temp;

        System.out.println("검사공정 종료 (생산수량): " + outputQty);
        System.out.println("검사공정 종료 (작업시간): " + workTime);
        System.out.println("검사공정 종료 (준비시간): " + leadTime);
        return outputQty;
    }

    public double cabbageProc7(double inputQty) {
        System.out.println("식힘공정 시작 (투입량): " + inputQty);
        leadTime += 0;
        workTime += 1440;
        System.out.println("식힘공정 종료 (생산수량): " + inputQty);
        System.out.println("식힘공정 종료 (작업시간): " + workTime);
        return inputQty;
    }

    public double cabbageProc8(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        leadTime += 20;
        System.out.println("포장공정 시작 (투입량): " + inputQty);

        num = (int)(temp / 30);        //10 => 300 고침
                                        //10 3분당 10박스라서
        rQty = (temp % 30);             //남은 양배추즙 팩
        workTime += Math.ceil(num * 0.3);
        outputQty += (int)(temp / 30);  //공정 후 나온 박스 수

        System.out.println("포장공정 종료 (생산수량): " + outputQty);
        System.out.println("포장공정 종료 (작업시간): " + workTime);
        System.out.println("포장공정 종료 (준비시간): " + leadTime);

        return outputQty;
    }





    public void blackGarlicCalculator() {
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
        blackGarlic = garlicProc1(blackGarlic);
        blackGarlic = garlicProc2(blackGarlic);
        blackGarlicExtract = garlicProc3_1(blackGarlic);
        blackGarlicExtract = garlicProc4_1(blackGarlicExtract);
        blackGarlicJuice = garlicProc5(blackGarlicExtract);
        blackGarlicJuice = garlicProc6(blackGarlicJuice);
        blackGarlicJuice = garlicProc7(blackGarlicJuice);
        blackGarlicJuiceBox = garlicProc8(blackGarlicJuice);

        System.out.println("총 생산수량(box): " + blackGarlicJuiceBox);
        System.out.println("미포장 양배추즙 수량: " + rQty);
        System.out.println("총 작업시간: " + workTime + "분");
        System.out.println("총 리드타임: " + leadTime + "분");
        System.out.println("예상납기일 : " + LocalDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes((int)(workTime + leadTime)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")));
    }
    public double garlicProc1(double inputQty) {
        System.out.println("계량공정 시작 (투입량): " + inputQty);
        //흑마늘 200000g (200kg) 넣는다고 가정
        leadTime += 20;
        workTime += 30;

        double outputQty = inputQty;
        System.out.println("계량공정 종료 (생산수량): " + outputQty);
        System.out.println("계량공정 종료 (작업시간): " + workTime);
        System.out.println("계량공정 종료 (준비시간): " + leadTime);

        return outputQty;
    }

    public double garlicProc2(double inputQty) {
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
                int num = (int) Math.ceil(temp / 50000); // 50kg(50000g)/3min
                workTime += (num * 3);
                outputQty += temp;
                temp = 0;
            }
        } while (temp > 0); // 조건식

        System.out.println("전처리 종료 (생산수량): " + outputQty);
        System.out.println("전처리 종료 (작업시간): " + workTime);
        return inputQty;
    }

    public double garlicProc3_1(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        double waterQty = inputQty * 3; // 물은 흑마늘의 3배

        do {
            leadTime += 60;

            if(temp >= 200000) {
                System.out.println("추출공정_1 시작 (투입량): 200,000g(200kg), 정제수: 600,000ml" );
                workTime += 1152; // 200kg에 대한 workTime
                outputQty += (800000 * 0.6);
                temp -= 200000;
                waterQty -= 600000;

                // 2열 가동
                if(temp >= 200000) {
                    outputQty += garlicProc3_2(200000) ;
                    temp -= 200000;
                    waterQty -= 600000;
                } else {
                    outputQty += garlicProc3_2(temp);
                    temp = 0;
                }
            }
            else { // 1열만 사용
                System.out.println("추출공정_1 시작 (투입량): " + temp + ", 정제수: " + waterQty);
                int num = (int) Math.ceil(temp / 25000); // 25L / 36min
                workTime += (num * 36);
                outputQty += ((temp + waterQty) * 0.6);
                temp = 0;
                waterQty = 0;
            }
            System.out.println("추출공정_1 종료 (생산수량): " + outputQty);
            System.out.println("추출공정 (남은수량): " + temp);
            System.out.println("추출공정 (작업시간): " + workTime);
            System.out.println("추출공정 (준비시간): " + leadTime);
        } while (temp > 0); // 조건식

        return outputQty;
    }
    public double garlicProc3_2(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        double waterQty = inputQty * 3;

        do {
            if(temp >= 200000) {
                System.out.println("추출공정_2 시작 (투입량): 200000, 정제수: 600000" );

                outputQty += (800000 * 0.6);
                temp -= 200000;
                waterQty -= 600000;
            } else {
                System.out.println("추출공정_2 시작 (투입량): " + temp + ", 정제수: " + waterQty);

                int num = (int) Math.ceil(temp / 25000); // 25L /36min
                outputQty += ((temp + waterQty) * 0.6);
                temp = 0;
                waterQty = 0;
            }
            System.out.println("추출공정 종료_2 (생산수량): " + outputQty);
            System.out.println("추출공정 종료_2 (작업시간): " + workTime);
            System.out.println("추출공정 종료_2 (준비시간): " + leadTime);
        } while (temp > 0); // 조건식


        return outputQty;
    }

    public double garlicProc4_1(double inputQty) {
        double waterQty = inputQty * 3;
        double temp = inputQty + waterQty; // 추출액 + 정제수 (480,000 + 1,440,000)
//        double temp = inputQty;
        double outputQty = 0;

        do {
            workTime += 1440;

            if(temp >= 1920000) {
                System.out.println("가열공정_1 시작 (투입량): " + 1920000);
                leadTime += 20;
                temp -= 1920000;
                outputQty += 1920000;
                System.out.println("temp1 :::::: "+temp);
                if(temp >= 1920000) {
                    System.out.println("2호출 큼 들어옴");
                    outputQty += garlicProc4_2(1920000);
                    temp -= 1920000;
                    System.out.println("temp2 :::::: "+temp);
                } else {
                    System.out.println("2호출 ㅈㄱ작ㅇㅁ옴");
                    outputQty += garlicProc4_2(temp);
                    temp = 0;
                }
            } else {
                System.out.println("가열공정_1 시작 (투입량): " + temp);
                outputQty += temp;
                temp = 0;
            }
            System.out.println("가열공정_1 종료 (생산수량): " + outputQty);
            System.out.println("가열공정_1 종료 (작업시간): " + workTime);
            System.out.println("가열공정_1 종료 (준비시간): " + leadTime);
            System.out.println("가열공정 (남은수량): " + temp);
        } while (temp > 0); // 조건식


        return outputQty;
    }
    public double garlicProc4_2(double inputQty) {
//        double temp = inputQty;
        //double waterQty = inputQty * 3;
        double temp = inputQty; // + waterQty; // 추출액 + 정제수 (480,000 + 1,440,000)
        double outputQty = 0;

        do {
            if(temp >= 1920000) {
                System.out.println("가열공정_2 시작 (투입량): " + 1920000);
                temp -= 1920000;
                outputQty += 1920000;
            } else {
                System.out.println("가열공정_2 시작 (투입량): " + temp);
                outputQty += temp;
                temp = 0;
            }
            System.out.println("가열공정_2 종료 (생산수량): " + outputQty);
            System.out.println("가열공정_2 종료 (작업시간): " + workTime);
            System.out.println("가열공정_2 종료 (준비시간): " + leadTime);
        } while (temp > 0); // 조건식


        return outputQty;
    }

    public double garlicProc5(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        System.out.println("충진공정 시작 (투입량): " + inputQty);

        leadTime += 20;
        outputQty += Math.floor(temp / 80); // 흑마늘즙 1포 = 80ml 3포 = 240ml 뭐로 해야되지?!
        num = (int) Math.ceil(outputQty / 175); // 175ea / 6min
        workTime += (num * 6);

        System.out.println("충진공정 종료 (생산수량): " + outputQty);
        System.out.println("충진공정 종료 (작업시간): " + workTime);
        System.out.println("충진공정 종료 (준비시간): " + leadTime);
        return outputQty;
    }

    public double garlicProc6(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        System.out.println("검사공정 시작 (투입량): " + inputQty);
        leadTime += 10;

        num = (int) Math.ceil(temp / 250); // 250ea/3min
        workTime += (num * 3);
        outputQty = temp;

        System.out.println("검사공정 종료 (생산수량): " + outputQty);
        System.out.println("검사공정 종료 (작업시간): " + workTime);
        System.out.println("검사공정 종료 (준비시간): " + leadTime);
        return outputQty;
    }

    public double garlicProc7(double inputQty) {
        System.out.println("식힘공정 시작 (투입량): " + inputQty);
        leadTime += 0;
        workTime += 1440;
        System.out.println("식힘공정 종료 (생산수량): " + inputQty);
        System.out.println("식힘공정 종료 (작업시간): " + workTime);
        return inputQty;
    }

    public double garlicProc8(double inputQty) {
        double temp = inputQty;
        double outputQty = 0;
        int num = 0;

        leadTime += 20;
        System.out.println("포장공정 시작 (투입량): " + inputQty);

        num = (int)(temp / 300);        //10 => 300 고침
        //10 3분당 10박스라서
        rQty = (temp % 30);             //남은 양배추즙 팩
        workTime += (num * 3);
        outputQty += (int)(temp / 30);  //공정 후 나온 박스 수

        System.out.println("포장공정 종료 (생산수량): " + outputQty);
        System.out.println("포장공정 종료 (작업시간): " + workTime);
        System.out.println("포장공정 종료 (준비시간): " + leadTime);

        return outputQty;
    }
}
