package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.*;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.repository.BOMRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import B2A3_M2S.mes.repository.RoutingItemRepository;
import B2A3_M2S.mes.repository.RoutingRepository;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import B2A3_M2S.mes.util.service.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
@Log4j2
public class CalculatorServiceImpl implements CalculatorService {
    @Autowired
    private RoutingRepository routingRepository;
    @Autowired
    private BOMRepository bomRepository;
    @Autowired
    private RoutingItemRepository routingItemRepository;
    @Autowired
    private ProductionRepository productionRepository;
    @Autowired
    private UtilService utilService;
    @Autowired
    private ShipService shipService;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public LocalDateTime getDeliveryDate(LocalDateTime startTime, ObtainOrderDto oDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(oDto.getOrderCd());
        // startTime = 자재 중 가장 늦은 입고 시간
        // 수주가 들어오면 자재량 계산 이후 발주일 뽑고 해당 시간 추가
        //LocalDateTime start = LocalDateTime.now();  //임시 시작시간

        LocalDateTime start = productionRepository.findByMaxEndDate();
        start = start == null ? LocalDateTime.now() : start;

        if (start.compareTo(startTime) <= 0) {
            //지금이 dateTime보다 빠르다
            start = startTime;
        }

        // 자재 조회
        List<BOMDTO> bList = bomRepository.findBypItem(oDto.getItem().getItemCd(), oDto.getQty()).stream().map(BOMDTO::of).collect(Collectors.toList());

        System.out.println("bom왜이럼: " + bList);

        // material 목록 추출
        // BOM 기준으로 필요한 자재
        List<ItemDto> materialList = bList.stream()
                .map(a -> {
                    a.getMaterialItem().setConsumption(a.getConsumption());
                    a.getMaterialItem().setCurrentQty(a.getConsumption());
                    a.getMaterialItem().setDepth(a.getDepth());
                    return a.getMaterialItem();
                }).collect(Collectors.toList());

        // 라우팅 정보 조회
        // 수주제품에 대한 라우팅 리스트
        List<RoutingDto> rList = routingRepository.findByItem(oDto.getItem().createItem()).stream().map(RoutingDto::of).collect(Collectors.toList());
        System.out.println("라우팅 없나" + rList);
        // 요긴 전체시간
        Long workTime = 0L;

        //요긴 전체 리드타임
        Long leadTime = 0L;

        Integer depth = bList.stream().mapToInt(BOMDTO::getDepth).max().orElseThrow();
        // 각 공정별로 시간 구함
        for (int i = 0; i < rList.size(); i++) {
            // 라우팅을 가져옴
            RoutingDto rDto = rList.get(i);

            // 라우팅에 대한 공정을 가져옴
            ProcessesDto pDto = rDto.getProcesses();

            List<ItemDto> materialList_Temp = new ArrayList<>();
            materialList_Temp.addAll(materialList);

            // 공정에 투입되야 하는 자재 정보 조회
            // 라우팅 아이템을 조회해서 해당 라우팅에 대한 상세 제품을 가져옴 (투입품)
            System.out.println("검사 안나오냐 " + rDto);

            List<RoutingItemDTO> recipeList = routingItemRepository
                    .findByRouting(rDto
                            .createRouting())
                    .stream()
                    .map(RoutingItemDTO::of)
                    .collect(Collectors.toList());


            // 임시 데이터 저장 변수
            // 공정별 작업시간에 사용
            Long workTime_temp = 0L;
            Long leadTime_temp = 0L;

            // 수용능력 계산
            Long seatingCapacity = rDto.getCapacity() == null ? pDto.getSeatingCapacity() : rDto.getCapacity();

            // 레시피 출력
            // 공정 시작 알림
            System.out.println(pDto.getProcNm() + " 시작");

            // 해당 라우팅에서 투입 자재 목록 저장
            List<ItemDto> currentMaterial = new ArrayList<>();

            // 자재 검색 -> 나중에 최적화
            // 투입 자재 목록 관리
            for (int j = 0; j < recipeList.size(); j++) {
                for (int k = materialList_Temp.size() - 1; k >= 0; k--) {

                    if (materialList_Temp.get(k).getItemCd().split("-")[0].equals(recipeList.get(j).getInputItem().getItemCd().split("-")[0])) {
                        if (!materialList_Temp.get(k).getItemType().equals("ITEM05")) {
                            currentMaterial.add(materialList_Temp.get(k));
                            materialList_Temp.remove(k);
                        } else if (depth == materialList_Temp.get(k).getDepth()) {
                            currentMaterial.add(materialList_Temp.get(k));
                            materialList_Temp.remove(k);
                        }
                        break;

                    }
                }
            }

            System.out.println("레시피 뭐야 " + recipeList);
            System.out.println("currentMaterial 뭐야 " + currentMaterial);
            System.out.println("materialList_Temp 뭐야 " + materialList_Temp);

            //recipeList
            // 부자재는 제외하고 총 투입량 (원자재들의 총 투입량)
            // 투입량을 계산해서 사용 (열갯수)
            double total = currentMaterial.stream()
                    .filter(a -> !a.getItemType().equals("ITEM04"))
                    .mapToDouble(ItemDto::getCurrentQty).sum();

            // 총 투입량을 저장하기 위해 사용
            // 총 투입량의 값
            double total_2 = total;

            // 열 갯수만큼 나누기
            total /= pDto.getRowCnt();
            System.out.println("total 몇이야: " + total);
            //작업별로 돌립니다
            while (total > 0) {
                //준비시간 더함
                leadTime_temp += pDto.getReadyTime();
                System.out.println("공정 정보: " + pDto);
                System.out.println("workTime(현재까지 전체시간): " + workTime);
                System.out.println("전체 자재 투입수량: " + total_2);
                System.out.println("자재 정보: " + currentMaterial);

                // 수용할 수 있는 만큼 계산하기 위해 임시저장 변수 (투입 수량)
                // 한번에 투입할 수량을 계산하기 위한 변수
                double total_temp = total;

                // 현재 수량에서 투입수량 빼기
                if (seatingCapacity <= total) {
                    total -= seatingCapacity;
                    total_temp = seatingCapacity;
                } else {
                    total = 0;
                }

                // 생산되는 양을 저장해두는 변수
                double total_temp_2 = total_temp * rDto.getYield();

                // 계획 짤떄 넣는 시간
                Long workTime_plan = workTime_temp;
                Long leadTime_plan = leadTime_temp;

                //단위변환 몰라서 하드코딩 -> ml -> ea, ea -> box
                // 투입 기준은 g, ml, capa 기준은 생산품, 생산품기준으로 변환
                for (int j = 0; j < recipeList.size(); j++) {
                    if (recipeList.get(j).getInputItem().getItemType().equals("ITEM04"))
                        continue;
                    if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT02") && recipeList.get(j).getInputItem().getItemUnit().equals("UNIT06")) {
                        total_temp_2 /= 80;
                        break;
                    } else if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT12") && recipeList.get(j).getInputItem().getItemUnit().equals("UNIT06")) {
                        total_temp_2 /= 15;
                        break;
                    } else if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT01")) {
                        total_temp_2 /= 30;
                        break;
                    } else if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT11")) {
                        total_temp_2 /= 25;
                        break;
                    }
                }

                // 작업시간 구하기
                // capa가 없으면 고정적인 시간 소요
                if (pDto.getCapacity() == null) {
                    workTime_temp += pDto.getWorkTime();
                } else if (pDto.getCapacity() != null) { // 공정에 capa가 있기에 시간을 계산 (고정이 아니라는 말)
                    Long capacity_temp = pDto.getCapacity();
                    System.out.println("capacity_temp: " + capacity_temp);
                    System.out.println("total_temp(투입 수량): " + total_temp);
                    System.out.println("생산수량: " + total_temp_2);
                    System.out.println("pDto.getWorkTime(): " + pDto.getWorkTime());

                    // 먼저 수량을 나눠서 해봄
                    workTime_temp += (int) Math.ceil((total_temp_2 / capacity_temp) * pDto.getWorkTime());
                }

                // 생산계획 insert 부분
                //수정 // 다시 수정
                for (int j = 0; j < pDto.getRowCnt(); j++) {
                    ProductionDTO productionDTO = new ProductionDTO();
                    NumberingService<Production> service = new NumberingService<>(entityManager, Production.class);
                    String pn = service.getNumbering("planNo", NumPrefix.PRODUCTION);
                    productionDTO.setPlanNo(pn);
                    productionDTO.setPlanQty((long) total_temp_2);
                    productionDTO.setStartDate(start.plusMinutes(workTime + leadTime + workTime_plan + leadTime_plan));
                    productionDTO.setProcesses(pDto);
                    productionDTO.setObtainOrder(oDto);
                    productionDTO.setCompletion(false);
                    productionDTO.setEndDate(start.plusMinutes(workTime + leadTime + workTime_temp + leadTime_temp));
                    productionDTO.setStatus("STATUS01");
                    productionDTO.setFirstGb(false);
                    productionDTO.setLastGb(false);
                    productionDTO.setItem(recipeList.get(0).getOutputItem());
                    productionDTO.setRouting(rList.get(i).createRouting());
                    if (i == 0 && total_2 == (total + total_temp))
                        productionDTO.setFirstGb(true);
                    else if ((rList.size() - 1) == i && total <= 0)
                        productionDTO.setLastGb(true);

                    productionRepository.save(productionDTO.createProduction());
                }
            } // 공정에 따른 작업시간 계산 끝

            // 친구들 정리_1
            for (int j = currentMaterial.size() - 1; j >= 0; j--) {
                if (currentMaterial.get(j).getItemType().equals("ITEM04") || currentMaterial.get(j).getItemType().equals("ITEM05")) {
                    materialList.remove(currentMaterial.get(j));
                    currentMaterial.remove(j);
                }
            }
            currentMaterial.stream().forEach(System.out::println);

            if (recipeList.get(0).getBom() != null) {
                depth--;
            }

            // 친구들 정리_2
            for (int j = 0; j < currentMaterial.size(); j++) {
                if (j == 0 && !recipeList.get(0).getOutputItem().getItemType().equals("ITEM04")) {
                    if (recipeList.get(0).getOutputItem().getItemUnit().equals("UNIT02") && recipeList.get(j).getInputItem().getItemUnit().equals("UNIT06")) {
                        total_2 /= 80;
                        break;
                    } else if (recipeList.get(0).getOutputItem().getItemUnit().equals("UNIT12") && recipeList.get(j).getInputItem().getItemUnit().equals("UNIT06")) {
                        total_2 /= 15;
                        break;
                    } else if (recipeList.get(0).getOutputItem().getItemUnit().equals("UNIT01")) {
                        total_2 /= 30;
                        break;
                    } else if (recipeList.get(0).getOutputItem().getItemUnit().equals("UNIT11")) {
                        total_2 /= 25;
                        break;
                    }

                    if (recipeList.get(0).getBom() != null) {
                        currentMaterial.get(j).setDepth(currentMaterial.get(j).getDepth() - 1);
                    }
                    currentMaterial.get(j).setConsumption(total_2 * rDto.getYield());
                    currentMaterial.get(j).setCurrentQty(total_2 * rDto.getYield());
                    currentMaterial.get(j).setItemCd(recipeList.get(0).getOutputItem().getItemCd());
                    currentMaterial.get(j).setItemNm(recipeList.get(0).getOutputItem().getItemNm());
                    currentMaterial.get(j).setItemGb(recipeList.get(0).getOutputItem().getItemGb());
                    currentMaterial.get(j).setItemType(recipeList.get(0).getOutputItem().getItemType());
                } else {
                    materialList.remove(currentMaterial.get(j));
                }
            }

            System.out.println(pDto.getProcNm() + " 공정 작업시간: " + workTime_temp);
            System.out.println(pDto.getProcNm() + " 공정 리드타임: " + leadTime_temp);
            System.out.println(pDto.getProcNm() + " 종료");

            workTime += workTime_temp;
            leadTime += leadTime_temp;
        }

        System.out.println("전체 작업시간: " + workTime);
        System.out.println("전체 리드타임: " + leadTime);
        System.out.println("전체 시간: " + (workTime + leadTime));
        System.out.println("끝나는 날: " + LocalDateTime.now().plusMinutes(workTime + leadTime).format(formatter));
        System.out.println("계산기 종료");
        return start.plusMinutes(workTime + leadTime);
    }

    @Scheduled(fixedDelay = 30000)
    @Transactional
    @Override
    public void schedulerApplication() {
        System.out.println("스케쥴러 실행 중");
        List<ProductionDTO> list = new ArrayList<>();

        list = productionRepository.findByEndDateAndStatus()
                .stream().map(ProductionDTO::of)
                .collect(Collectors.toList());

        // 생산중 -> 생산완료 변경
        if (list.size() > 0) {
            list.stream().forEach(a -> a.setStatus("STATUS03"));
            utilService.saveOutput(list);
            // 우선 생산완료로 변경
            list = ProductionDTO.of(productionRepository.saveAll(list.stream().map(ProductionDTO::createProduction).collect(Collectors.toList())));
        }

        list = productionRepository.findByStartDateAndEndDateAndStatus().stream().map(ProductionDTO::of).collect(Collectors.toList());

        // 계획수립 -> 생산중 변경
        if (list.size() > 0) {
            list.stream().forEach(a -> a.setStatus("STATUS02"));
            list = ProductionDTO.of(productionRepository.saveAll(list.stream()
                    .map(ProductionDTO::createProduction)
                    .collect(Collectors.toList())));
            utilService.saveInput(list);
        }
    }
}
