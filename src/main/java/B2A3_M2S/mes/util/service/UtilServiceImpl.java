package B2A3_M2S.mes.util.service;

import B2A3_M2S.mes.dto.*;
import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.*;
import B2A3_M2S.mes.service.ShipService;
import B2A3_M2S.mes.service.StockService;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.repository.UtilRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilServiceImpl implements UtilService {
    @Autowired
    LotNoLogRepository lotRepository;
    @Autowired
    ProcessStockRepository procStockRepository;
    @Autowired
    RoutingItemRepository routingItemRepository;
    @Autowired
    BOMRepository bomRepository;
    @Autowired
    StockService service;
    @Autowired
    ProductionRepository productionRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    private ShipService shipService;
    @Autowired
    private StockService stockService;

    /***
     * 각 공정 단계에서 자재 투입시 호출하는 메소드 입니다.
     * @param "List<ProductionDTO> pList" 생산계획 리스트를 전달합니다.
     * @return "List<LotNoLogDTO>" 생성된 LotNoLogDTO를 반환합니다.
     */
    @Override
    public List<LotNoLogDTO> saveInput(List<ProductionDTO> pList) {
        System.out.println("0번0번");
        System.out.println(pList);
        // lot 저장할 리스트
        // 계획수립 -> 생산중 즉, 투입되는 Lot 이력을 저장하는 List 입니다.
        List<LotNoLogDTO> lList = new ArrayList<>();

        // 재공재고 조회, 임마 DB반영
        List<ProcessStockDTO> psList = procStockRepository.findByQtyNot(0L).stream().map(ProcessStockDTO::of).collect(Collectors.toList());
        List<RoutingItemDTO> riList = null;
        System.out.println("1번1번");
        // 생산계획 조회
        for (ProductionDTO pDto : pList) {
            System.out.println("2번2번");
            System.out.println(pDto);

            // 하나의 생산계획 단계에서 투입할 자재를 모아두는 리스트입니다.
            List<ProcessStockDTO> inputItem = new ArrayList<>();

            // 계획의 공정에 대한 라우팅 아이템을 조회합니다.
            riList = routingItemRepository.findByRouting(pDto.getRouting()).stream().map(RoutingItemDTO::of).collect(Collectors.toList());

            // 라우팅 아이템, 즉 소모되는 자재 기준으로 (재공재고가 충분한지 파악하기 위해서)
            for (RoutingItemDTO riDto : riList) {
                System.out.println("3번3번");
                System.out.println("3번3번 riDto: " + riDto);
                // 한 라우팅 아이템의 한 자재를 저장할 리스트 ?
                List<ProcessStockDTO> currentList = new ArrayList<>();

                // 정제수는 제외합니다.
                if (riDto.getInputItem().getItemType().equals("ITEM05"))
                    continue;

                // 총 갯수
                double totalQty = 0;
                // 필요한 갯수
                Double needQty = 0.0;

                // 재공재고 루프를 돈다 (재공재고에 존재하는지 확인)
                for (ProcessStockDTO psDto : psList) {

                    // 재공재고에 해당 재고가 존재한다면 [psDto : 재공재고, riDto : 라우팅 아이템(인풋 아이템, 아웃풋 아이템으로 사용)]
                    // 현재 공정의 라우팅 아이템에 설정된 재공재고를 찾습니다. (투입할 자재)
//                    if (psDto.getItem().getItemCd().equals(riDto.getInputItem().getItemCd()) &&
//                            psDto.getLocation().equals(riDto.getRouting().getProcesses().getProcCd())) {

                    // 재공재고의 제품코드와 인풋아이템 코드가 같으면
                    if (psDto.getItem().getItemCd().equals(riDto.getInputItem().getItemCd())) {
                        // 필요한 자재 저장 (현재 재공재고이 있는애들로만)
                        currentList.add(psDto);
                        System.out.println("똑같은넘 찾음");
                    }
                }

                Long convert = pDto.getPlanQty();
                if (riDto.getInputItem().getItemUnit().equals("UNIT06") && riDto.getOutputItem().getItemUnit().equals("UNIT02")) {
                    convert *= 80;
                } else if ((riDto.getInputItem().getItemUnit().equals("UNIT02") || riDto.getInputItem().getItemUnit().equals("UNIT12")) && riDto.getOutputItem().getItemUnit().equals("UNIT01")) {
                    convert *= 30;
                }


                // bom이 존재할때
                if (riDto.getBom() != null) {
                    // 해당 bom 조회
                    //BOMDTO bDto = BOMDTO.of(bomRepository.findByBomNo(riDto.getBom().getBomNo()));
                    //needQty = (pDto.getPlanQty() / bDto.getStandard()) * bDto.getConsumption();

                    Item ir = riDto.getOutputItem().createItem();
                    ir.setItemCd(ir.getItemCd().split("-")[0]);
                    System.out.println("니뭔데 : " + ir);
                    List<BOMDTO> bDto = BOMDTO.of(bomRepository.findByProductItem(ir));
                    System.out.println("니뭔데2-1 : " + bDto);
                    //bDto = bDto.stream().filter(a -> !a.getMaterialItem().getItemType().equals("ITEM04")).collect(Collectors.toList());

                    // 아래구문 추가하면 추출에서 뻑남, 정제수 계산이 안되서, 근데 빼면 추출은 넘어가나 혼합에 부자재가 계산되어서 투입량 이상해짐
                    //bDto = bDto.stream().filter(a -> a.getMaterialItem().getItemCd().equals(riDto.getInputItem().getItemCd().split("-")[0])).collect(Collectors.toList());


                    // 내가 부자재일 경우엔 나의 기준으로 계산 ㄱ
                    if (riDto.getInputItem().getItemType().equals("ITEM04")) {
                        bDto = bDto.stream().filter(a -> a.getMaterialItem().getItemCd().equals(riDto.getInputItem().getItemCd().split("-")[0])).collect(Collectors.toList());
                    }
                    // 내가 부자재가 아니면 부자재 빼고 계산 ㄱ
                    else {
                        bDto = bDto.stream().filter(a -> !a.getMaterialItem().getItemType().equals("ITEM04")).collect(Collectors.toList());
                    }
                    System.out.println("니뭔데2-2 : " + bDto);


                    needQty = (convert / riDto.getRouting().getYield()) /
                            (
                                    (
                                            bDto.stream().mapToDouble(BOMDTO::getConsumption).sum() /
                                                    bDto.stream().filter(a -> a.getMaterialItem()
                                                                    .getItemCd()
                                                                    .equals(riDto.getInputItem()
                                                                            .getItemCd().split("-")[0]))
                                                            .mapToDouble(BOMDTO::getConsumption).sum()
                                    )
                            );
                    System.out.println("니뭔데3 : " + bDto.stream().mapToDouble(BOMDTO::getConsumption).sum());

                    System.out.println("니뭔데4 : " + bDto.stream().filter(a -> a.getMaterialItem()
                                    .getItemCd()
                                    .equals(riDto.getInputItem()
                                            .getItemCd().split("-")[0]))
                            .mapToDouble(BOMDTO::getConsumption).sum());


                    System.out.println("몇이고 : " + needQty);
                } else { // 존재 안할때
                    needQty = Double.valueOf(convert);
                }
                System.out.println("몇이고2 : " + needQty);

                // 재공재고가 존재한다면
                if (currentList.size() != 0) {
                    // 재공재고들의 갯수를 파악한다.
                    totalQty = currentList.stream().mapToDouble(ProcessStockDTO::getQty).sum();
                } else {
                    totalQty = 0;
                }
                System.out.println("몇이고3 totalQty : " + totalQty);

                // 소모량 보다 적을때
                if (totalQty < needQty) {
                    // 필요 수량만큼 출고 후 재공재고에 저장
                    // 다시 재공재고에 더함
                    service.releaseItem(riDto.getInputItem().createItem(), (long) (needQty - totalQty)).stream().forEach(a -> psList.add(a));
                } else if (totalQty > needQty) {
                    // 필요 수량만큼 출고 후 재공재고에 저장
                }

                // 아까 넣은 친구들과 다 같이 모움 (투입 준비)
                Double needQty_temp = needQty;
                System.out.println("몇이고4 needQty_temp : " + needQty_temp);

                for (ProcessStockDTO psDto : psList) {
                    // 재공재고에 해당 재고가 존재한다면 psDto : 재공재고, riDto : 라우팅 아이템(인풋 아이템, 아웃풋 아이템으로 사용)
//                    if (psDto.getItem().getItemCd().equals(riDto.getInputItem().getItemCd()) &&
//                            psDto.getLocation().equals(riDto.getRouting().getProcesses().getProcCd())) {
                    if (psDto.getItem().getItemCd().equals(riDto.getInputItem().getItemCd())) {
                        // 필요한 자재 저장해놔
                        inputItem.add(psDto);

                        if (psDto.getQty() >= needQty_temp) {
                            psDto.setConsumption(Long.valueOf(String.valueOf((needQty_temp.intValue()))));
                            needQty_temp = 0.0;
                        } else {
                            psDto.setConsumption(psDto.getQty());
                            needQty_temp -= psDto.getQty();
                        }
                    }
                }
            }

            // 이제 투입 준비합니다
            // Lot No 찍읍시다
            System.out.println("4번4번");
            System.out.println("너 얼마있음 " + inputItem);
            for (ProcessStockDTO data : inputItem) {
                if (data.getConsumption() == 0)
                    continue;

                System.out.println("5번5번");
                // 등록할 Lot 이력 생성
                LotNoLogDTO lDto = new LotNoLogDTO();
                lDto.setIItem(data.getItem());
                lDto.setProcesses(pDto.getProcesses());
                lDto.setInputQty(data.getConsumption());
                lDto.setFStockNo(0L);
                lDto.setPLotSeq1(data.getLotNoLog().getLotSeq());
                lDto = LotNoLogDTO.of(lotRepository.save(lDto.createLotNoLog()));
                data.setQty(data.getQty() - data.getConsumption());
                data.setConsumption(0L);
                data.setLocation(pDto.getProcesses().getProcCd());
                //data.setLotNoLog(lDto);
                data = ProcessStockDTO.of(procStockRepository.save(data.createProcessStock()));
                lDto.setFStockNo(data.getStockNo());
                lDto.setProduction(pDto);
                lList.add(lDto);
            }
            System.out.println("6번6번");
            lotRepository.saveAll(lList.stream().map(LotNoLogDTO::createLotNoLog).collect(Collectors.toList()));
        }
        return lList;
    }

    /***
     * 각 공정 단계가 종료되었을 시 호출하는 메소드 입니다.
     * @param "List<ProductionDTO> pList" 생산계획 리스트를 전달합니다.
     * @return "List<LotNoLogDTO>" 생성된 LotNoLogDTO를 반환합니다.
     */
    @Override
    public List<LotNoLogDTO> saveOutput(List<ProductionDTO> pList) {
        System.out.println(pList);
        // lot 저장할 리스트
        // 생산중 -> 생산완료 즉, 투입되는 Lot 이력을 저장하는 List 입니다.
        List<LotNoLogDTO> lList = new ArrayList<>();

        // 재공재고 조회, 임마 DB반영
        List<ProcessStockDTO> psList = procStockRepository.findByQtyNot(0L).stream().map(ProcessStockDTO::of).collect(Collectors.toList());
        List<RoutingItemDTO> riList = null;
        // 생산계획 기준으로 ㄱㄱ
        for (ProductionDTO pDto : pList) {
            System.out.println(pDto);

            // 하나의 생산계획 단계에서 투입할 자재를 모아두는 리스트입니다.
            List<ProcessStockDTO> inputItem = new ArrayList<>();

            // 계획의 공정에 대한 라우팅 아이템을 조회합니다.
            riList = routingItemRepository.findByRouting(pDto.getRouting()).stream().map(RoutingItemDTO::of).collect(Collectors.toList());
            //List<ProcessStockDTO> currentList = new ArrayList<>();

            // 아웃풋 재공재고 생성합니다.
            ProcessStockDTO processStockDto = new ProcessStockDTO();
            processStockDto.setQty(pDto.getPlanQty());
            processStockDto.setItem(riList.get(0).getOutputItem());
            processStockDto.setLocation(pDto.getProcesses().getProcCd());
            processStockDto = ProcessStockDTO.of(procStockRepository.save(processStockDto.createProcessStock()));

            // 라우팅 아이템을 기준으로 정제수를 제외한 LotNo을 조회해서 수정합니다.
            List<LotNoLog> lotNoList = new ArrayList<>();

            String tempLotNo = lotRepository.createLotNo(pDto.getProcesses().getProcCd());
            System.out.println("왜안되노 보자: " + riList);
            for (RoutingItemDTO riDto : riList) {
                // 정제수는 제외합니다.
                if (riDto.getInputItem().getItemType().equals("ITEM05"))
                    continue;

                System.out.println("출력해라: riDto " + riDto);


                // LotNo을 조회해서 (공정, inputItem, Lot Null 여부) 생산품의 정보를 설정해준다.
                List<LotNoLog> tempLotNoList = lotRepository
                        .findByProduction(pDto.createProduction());
//                List<LotNoLog> tempLotNoList = lotRepository
//                        .findByiItemAndProcessesAndLotNoNull(riDto.getInputItem().createItem(),
//                                pDto.getProcesses().createProcesses());
                Long fStockNo = processStockDto.getStockNo();
                ProcessStockDTO finalProcessStockDto = processStockDto;

                tempLotNoList.stream().forEach(a -> {
/*                            if (riDto.getBom() == null) {
                                a.setOutputQty(a.getInputQty());
                            } else {
                                BOM bEntity = null;
                                bEntity = bomRepository.findByBomNo(riDto.getBom().getBomNo());
                                System.out.println("잇제 들어왔ㅅ제");
                                Long std = bEntity.getStandard();
                                Double csp = bEntity.getConsumption();
                                //a.setOutputQty((long) ((a.getInputQty() / std) * csp));
                            }*/
                            a.setFStockNo(fStockNo);
                            a.setOItem(finalProcessStockDto.getItem().createItem());
                            a.setLotNo(tempLotNo);
                            //a.setOutputQty(pDto.getPlanQty());
                            a.setOutputQty(a.getInputQty());
                        }
/*
                tempLotNoList.stream().forEach(a -> {
                            if (riDto.getBom() == null) {
                                a.setOutputQty(a.getInputQty());
                            } else {
                                BOM bEntity = null;
                                bEntity = bomRepository.findByBomNo(riDto.getBom().getBomNo());
                                System.out.println("잇제 들어왔ㅅ제");
                                Long std = bEntity.getStandard();
                                Double csp = bEntity.getConsumption();
                                a.setOutputQty((long) ((a.getInputQty() / std) * csp));
                            }
                            a.setFStockNo(fStockNo);
                            a.setOItem(finalProcessStockDto.getItem().createItem());
                            a.setLotNo(tempLotNo);
                        }
*/
                );
                lotNoList.addAll(tempLotNoList);
                break;
            }
            lotRepository.saveAll(lotNoList);

            List<LotNoLog> tempMergeList = new ArrayList<>();
            tempMergeList.addAll(lotNoList);
            System.out.println("출력해라: " + tempMergeList);
            // 크면 병합 가자
            // 무한루프으
            while (tempMergeList.size() > 1) {
                LotNoLog logEntity = new LotNoLog();
                logEntity.setPLotSeq1(tempMergeList.get(0).getLotSeq());
                logEntity.setPLotSeq2(tempMergeList.get(1).getLotSeq());
                //logEntity.setInputQty(tempMergeList.get(0).getOutputQty() + tempMergeList.get(1).getOutputQty());
                logEntity.setInputQty(tempMergeList.get(0).getInputQty() + tempMergeList.get(1).getInputQty());
                logEntity.setOutputQty(logEntity.getInputQty());
                //logEntity.setOutputQty(pDto.getPlanQty());
                logEntity.setIItem(tempMergeList.get(0).getOItem());
                logEntity.setOItem(logEntity.getIItem());
                logEntity.setLotNo(tempMergeList.get(0).getLotNo());
                logEntity.setFStockNo(tempMergeList.get(0).getFStockNo());
                logEntity.setRemark("MERGE");
                tempMergeList.remove(1);
                tempMergeList.remove(0);
                logEntity = lotRepository.save(logEntity);
                tempMergeList.add(logEntity);
                System.out.println("병합 들어옴");
            }

            tempMergeList.get(0).setOutputQty(pDto.getPlanQty());
            lotRepository.save(tempMergeList.get(0));
            processStockDto.setLotNoLog(LotNoLogDTO.of(tempMergeList.get(0)));
            ProcessStock pEntity = procStockRepository.save(processStockDto.createProcessStock());
            //processStockDto = ProcessStockDTO.of(procStockRepository.save(processStockDto.createProcessStock()));
            System.out.println("너 왜 두번임? " + processStockDto);

            // 여기서 진행
            // 입고 후 출고 해야함
            if (pDto.isLastGb()) {
                stockService.addProducts(pEntity.getItem(), pEntity.getLotNoLog().getLotNo(), pEntity.getQty());
                pEntity.setQty(0L);
                pEntity = procStockRepository.save(pEntity);
                shipService.createShip(pDto.getObtainOrder().getOrderCd());
            }

        }
        return lList;
    }

    @Override
    public LotNoLogDTO saveFinalOutput(LotNoLogDTO log) {
        return null;
    }

    @Override
    public String getLotNo(NumPrefix numbering) {
        if (!numbering.equals(numbering.RECEIVING))
            return null;

        return lotRepository.createLotNo(numbering.getTitle());
    }

    @Override
    public String getLotNo(String pcodCd) {
        return lotRepository.createLotNo(pcodCd);
    }


    // 입고시 사용합니다.
    @Override
    public LotNoLogDTO saveReceiving(WarehouseLogDTO wDto) {
        // 최초 LotNoLog 생성
        LotNoLogDTO lDto = new LotNoLogDTO();
        //lDto.setLotNo(lotRepository.createLotNo(wDto.getLotNo()));
        lDto.setLotNo(wDto.getLotNo());
        lDto.setFStockNo(-2L);
        lDto.setInputQty(wDto.getQty());
        lDto.setOutputQty(wDto.getQty());
        lDto.setIItem(wDto.getItem());
        lDto.setOItem(wDto.getItem());
        lDto.setRemark(wDto.getInoutNo());
        lDto = LotNoLogDTO.of(lotRepository.save(lDto.createLotNoLog()));
        return lDto;
    }
}
