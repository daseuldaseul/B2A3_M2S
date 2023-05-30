package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.*;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.RoutingItem;
import B2A3_M2S.mes.repository.*;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculatorServiceImpl implements CalculatorService {
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
    @Autowired
    private RoutingItemRepository routingItemRepository;

    @Autowired
    private ProductionRepository productionRepository;

    @PersistenceContext
    private EntityManager entityManager;
    // 나는 시뮬레이터
    @Override
    public LocalDateTime getDeliveryDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 수주가 들어오면 자재량 계산 이후 발주일 뽑고 해당 시간 추가
        LocalDateTime start = LocalDateTime.now();  //임시 시작시간
        // 테스트를 위해 수주 정보 조회
        ObtainOrderDto oDto = obtainOrderRepository.findAll()
                .stream().map(ObtainOrderDto::of).collect(Collectors.toList()).get(1);
        System.out.println("여기야1. : " + oDto);

        // 자재 조회
        List<BOMDTO> bList = bomRepository.findBypItem(oDto.getItem().getItemCd(), oDto.getQty()).stream().map(BOMDTO::of).collect(Collectors.toList());

        // material 목록 추출
        List<ItemDto> materialList = bList.stream().map(a -> {
            a.getMaterialItem().setConsumption(a.getConsumption());
            a.getMaterialItem().setCurrentQty(a.getConsumption());
            a.getMaterialItem();
            return a.getMaterialItem();
        }).collect(Collectors.toList());



        System.out.println("여긴 BOM 출력입니다");
        bList.forEach(System.out::println);

        // 라우팅 정보 조회
        List<RoutingDto> rList = routingRepository.findByItem(oDto.getItem().createItem()).stream().map(RoutingDto::of).collect(Collectors.toList());

        /*
            각 품목별 더하기 고고
            우선 양배추 하나로만 해봅시다
        */
        // 요긴 전체시간
        Long workTime = 0L;

        //요긴 전체 리드타임
        Long leadTime = 0L;

        System.out.println("계산기 시작");
        // 각 공정별로 시간 구함
        for (int i = 0; i < rList.size(); i++) {
            RoutingDto rDto = rList.get(i);
            ProcessesDto pDto = rDto.getProcesses();

            // 공정에 투입되야 하는 자재 정보 조회
            List<RoutingItemDTO> recipeList = routingItemRepository.findByRouting(rList.get(i).createRouting()).stream().map(RoutingItemDTO::of).collect(Collectors.toList());

            // 임시 데이터 저장 변수
            // 공정별 작업시간에 사용
            Long workTime_temp = 0L;
            Long leadTime_temp = 0L;
            Long seatingCapacity = rDto.getCapacity() == null ? pDto.getSeatingCapacity() : rDto.getCapacity();

            System.out.println(pDto.getProcNm() + " 시작");

            // 투입 자재 목록
            List<ItemDto> currentMaterial = new ArrayList<>();

            // 자재 검색 -> 나중에 최적화
            for (int j = 0; j < recipeList.size(); j++) {
                for (int k = 0; k < materialList.size(); k++) {
                    if (materialList.get(k).getItemCd().equals(recipeList.get(j).getInputItem().getItemCd())) {
                        currentMaterial.add(materialList.get(k));
                        break;
                    }
                }
            }

            //recipeList
            // 투입되는 양이 capa보다 큰지 검사 후 작업시간 산출
            // 여기서 한개의 자재가 투입되는 친구가 아닐수도 있기에 조건을 구해봅시다
            // 총 합계
            double total = currentMaterial.stream().filter(a -> !a.getItemType().equals("ITEM04")).mapToDouble(ItemDto::getCurrentQty).sum();
            System.out.println("가라 레시피:" + recipeList);

            //단위변환 몰라서 하드코딩 -> ml -> ea, ea -> box
            for (int j = 0; j < recipeList.size(); j++) {
                if (recipeList.get(j).getInputItem().getItemType().equals("ITEM04"))
                    continue;

                if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT02") && recipeList.get(j).getInputItem().getItemUnit().equals("UNIT06")) {
                    total /= 80;
                    break;
                } else if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT12") && recipeList.get(j).getInputItem().getItemUnit().equals("UNIT06")) {
                    total /= 15;
                    break;
                } else if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT01")) {
                    total /= 30;
                    break;
                } else if (recipeList.get(j).getOutputItem().getItemUnit().equals("UNIT11")) {
                    total /= 25;
                    break;
                }
            }
            
            // 전체 수량 저장하기 위해 사용
            double total_2 = total;
            System.out.println("현재 자재 좀 출력해바:");
            currentMaterial.stream().forEach(System.out::println);

            // 열 갯수만큼 나누기
            total /= pDto.getRowCnt();

            double test = total;


            //작업별로 돌립니다
            // 2열 짜리도 계산 추가로 넣어야 함
            while (total > 0) {
                leadTime_temp += pDto.getReadyTime();
                System.out.println("공정 정보: " + pDto);
                System.out.println("workTime(현재까지 전체시간): " + workTime);
                System.out.println("전체 자재 및 생산계획 수량: " + total);
                // 수용할 수 있는 만큼 계산하기 위해 임시저장 변수 (투입 수량)
                double total_temp = total;

                // 열 갯수만큼 수용능력 증가
                //seatingCapacity *= pDto.getRowCnt();

                // 현재 수량에서 투입수량 빼기
                if (seatingCapacity <= total) {
                    total -= seatingCapacity;
                    total_temp = seatingCapacity;
                } else {
                    total = 0;
                }

                // 작업시간 구하기
                // capa가 없으면 고정적인 시간 소요
                if (pDto.getCapacity() == null) {
                    workTime_temp += pDto.getWorkTime();
                } else if (pDto.getCapacity() != null) { // 공정에 capa가 있기에 시간을 계산 (고정이 아니라는 말)
                    Long capacity_temp = pDto.getCapacity();
                    System.out.println(" 제발르,," + recipeList.get(0));
                    System.out.println("capacity_temp: " + capacity_temp);
                    System.out.println("total_temp(투입 수량): " + total_temp);
                    System.out.println("pDto.getWorkTime(): " + pDto.getWorkTime());

                    // 시간구하기 (고정이 아니므로 열 갯수로 나눔)
                    //workTime_temp += (int) Math.ceil((total_temp / capacity_temp) * pDto.getWorkTime() / pDto.getRowCnt());
                    
                    // 먼저 수량을 나눠서 해봄
                    workTime_temp += (int) Math.ceil((total_temp / capacity_temp) * pDto.getWorkTime());
                }

                System.out.println("workTime_temp: " + workTime_temp);
                System.out.println("여기야 getWorkTime(): " + pDto.getWorkTime());

            } // 공정에 따른 작업시간 계산 끝

            // 친구들 정리_1
            for (int j = currentMaterial.size() - 1; j >= 0; j--) {
                if (currentMaterial.get(j).getItemType().equals("ITEM04")) {
                    materialList.remove(currentMaterial.get(j));
                    currentMaterial.remove(j);
                }
            }

            // 친구들 정리_2
            for (int j = 0; j < currentMaterial.size(); j++) {
                if (j == 0 && !recipeList.get(0).getOutputItem().getItemType().equals("ITEM04")) {
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

            //수정
            for(int j=0; j<pDto.getRowCnt(); j++) {
                ProductionDTO productionDTO = new ProductionDTO();
                NumberingService<Production> service = new NumberingService<>(entityManager, Production.class);
                String pn = service.getNumbering("planNo", NumPrefix.PRODUCTION);
                productionDTO.setPlanNo(pn);
                productionDTO.setPlanQty((long) test);
                productionDTO.setStartDate(start.plusMinutes(workTime + leadTime));
                productionDTO.setProcesses(pDto);
                productionDTO.setObtainOrder(oDto);
                productionDTO.setCompletion(false);
                productionDTO.setEndDate(start.plusMinutes(workTime + leadTime + workTime_temp + leadTime_temp));
                productionRepository.save(productionDTO.createProduction());
            }
            //수정


            workTime += workTime_temp;
            leadTime += leadTime_temp;





        }

        System.out.println("전체 작업시간: " + workTime);
        System.out.println("전체 리드타임: " + leadTime);
        System.out.println("전체 시간: " + workTime + leadTime);
        System.out.println("끝나는 날: " + LocalDateTime.now().plusMinutes(workTime + leadTime).format(formatter));
        System.out.println("계산기 종료");
        return null;
    }
}
