package B2A3_M2S.mes.util.service;

import B2A3_M2S.mes.dto.*;
import B2A3_M2S.mes.entity.ProcessStock;
import B2A3_M2S.mes.entity.RoutingItem;
import B2A3_M2S.mes.repository.BOMRepository;
import B2A3_M2S.mes.repository.LotNoLogRepository;
import B2A3_M2S.mes.repository.ProcessStockRepository;
import B2A3_M2S.mes.repository.RoutingItemRepository;
import B2A3_M2S.mes.service.StockService;
import B2A3_M2S.mes.util.enums.NumPrefix;
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

    /**
     * 출고시 호출하는 메소드 입니다.
     *
     * @param "Enum 코드번호 (출고)"
     * @return 저장된 LotNoLog 객체 반환
     */
    @Override
    public ProcessStockDTO saveInput(WarehouseLogDTO wDto) {
        // 최초 LotNoLog 생성
        LotNoLogDTO lDto = new LotNoLogDTO();
        lDto.setLotNo(wDto.getLotNo());
        lDto.setFStockNo(-1L);
        lDto.setInputQty(wDto.getQty());
        lDto.setOutputQty(wDto.getQty());
        lDto.setIItem(wDto.getItem());
        lDto.setOItem(wDto.getItem());
        lDto.setRemark(wDto.getInoutNo());
        lDto = LotNoLogDTO.of(lotRepository.save(lDto.createLotNoLog()));

        // 재공재고 생성
        ProcessStockDTO pDto = new ProcessStockDTO();
        pDto.setQty(wDto.getQty());
        pDto.setItem(wDto.getItem());
        pDto.setLotNoLog(lDto);
        pDto.setLocation(NumPrefix.RELEASE.getTitle());
        pDto = ProcessStockDTO.of(procStockRepository.save(pDto.createProcessStock()));
        return pDto;
    }

    /***
     * 출고를 제외한 각 공정 단계에서 자재 투입시 호출하는 메소드 입니다.
     * @param "Enum 코드번호 (출고)"
     * @return 저장된 LotNoLog 객체 반환
     */
    @Override
    public LotNoLogDTO saveInput(List<ProductionDTO> pList) {
        System.out.println("들어옴 " + pList);
        StockService service = new StockService();
        // lot 저장할 리스트
        // 계획수립 -> 생산중 즉, 투입되는 Lot 이력을 저장하는 List 입니다.
        List<LotNoLogDTO> lList = new ArrayList<>();

        // 재공재고 조회, 임마 DB반영
        List<ProcessStockDTO> psList = procStockRepository.findByQtyNot(0L).stream().map(ProcessStockDTO::of).collect(Collectors.toList());
        List<RoutingItemDTO> riList = null;

        System.out.println("들어옴1 " + psList);
        System.out.println("들어옴2 " + riList);
        // 생산계획 조회
        for (ProductionDTO pDto : pList) {
            // 하나의 생산계획 단계에서 투입할 자재를 모아두는 리스트입니다.
            List<ProcessStockDTO> inputItem = new ArrayList<>();

            // 해당 제품을 만들기 위해 소모되는 자재량 계산이 필요함
            // 라우팅 아이템을 조회해서 bom_no가 존재하면
            // 해당 bom을 조회해서 소모량을 가지고 온 다음 계산해서 총 소모량 계산

            // 그렇지 않다면 (bom_no)가 null 이라면
            // 해당 자재 100프로가 그대로 넘어가기 때문에 재공재고에서 결과물 수량만큼 감소시킨다 ?

            // 근데 그 전에 재공재고 테이블에 해당 자재가 없으면
            // 출고를 시킨다.

            // 아웃풋 아이템 기준으로 해당 라우팅으로 어떤 아이템을 생산하는지 확인 (라우팅 아이템) 조회
            // 아웃풋 기준으로 어떤 자재가 소모되는지 확인
            riList = routingItemRepository.findByRouting(pDto.getRouting()).stream().map(RoutingItemDTO::of).collect(Collectors.toList());

            // 라우팅 아이템, 즉 소모되는 자재 기준으로 (재공재고가 충분한지 파악하기 위해서)
            for (RoutingItemDTO riDto : riList) {

                // 한 라우팅 아이템의 한 자재를 저장할 리스트 ?
                List<ProcessStockDTO> currentList = new ArrayList<>();

                if (riDto.getInputItem().getItemType().equals("ITEM05"))
                    continue;

                double totalQty = 0;
                Double needQty = 0.0;

                // 재공재고 루프를 돈다 (재공재고에 존재하는지 확인)
                for (ProcessStockDTO psDto : psList) {
                    // 재공재고에 해당 재고가 존재한다면 psDto : 재공재고, riDto : 라우팅 아이템(인풋 아이템, 아웃풋 아이템으로 사용)
                    if (psDto.getItem().getItemCd().equals(riDto.getInputItem().getItemCd()) &&
                            psDto.getLocation().equals(riDto.getRouting().getProcesses().getProcCd())) {

                        // 필요한 자재 저장해놔 (현재 재공재고이 있는애들로만)
                        currentList.add(psDto);
                    }
                }

                // bom이 존재할때
                if (riDto.getBom() != null) {
                    // 해당 bom 조회
                    BOMDTO bDto = BOMDTO.of(bomRepository.findByBomNo(riDto.getBom().getBomNo()));
                    needQty = (pDto.getPlanQty() / bDto.getStandard()) * bDto.getConsumption();

                    // 해당 자재에 대한 소모량 구함
//                    Double dobule_temp = bomRepository.findByProductItemAndMaterialItem(riDto.getOutputItem().getItemCd(), riDto.getInputItem().getItemCd(), pDto.getPlanQty());
//                    needQty = bomRepository.findByProductItemAndMaterialItem(riDto.getOutputItem().getItemCd(), riDto.getInputItem().getItemCd(), pDto.getPlanQty());
//                    needQty = needQty == null ? 0.0 : needQty;
                } else { // 존재 안할때
                    needQty = Double.valueOf(pDto.getPlanQty());
                }

                // 재공재고가 존재한다면
                if (currentList.size() != 0) {
                    // 재공재고들의 갯수를 파악한다.
                    totalQty = currentList.stream().mapToDouble(ProcessStockDTO::getQty).sum();
                }

                if (totalQty < needQty) {
                    // 필요 수량만큼 출고 후 재공재고에 저장
                    System.out.println("필요: totalQty " + totalQty);
                    System.out.println("필요: needQty " + needQty);
                    service.releaseItem(riDto.getInputItem().createItem(), (long) (needQty - totalQty)).stream().forEach(a -> psList.add(a));
                }
                // 소모량 보다 많을때
                else if (totalQty > needQty) {
                    // 필요 수량만큼 출고 후 재공재고에 저장
                    System.out.println("필요: totalQty " + totalQty);
                    System.out.println("필요: needQty " + needQty);
                    service.releaseItem(riDto.getInputItem().createItem(), (long) (needQty - totalQty)).stream().forEach(a -> psList.add(a));
                }


                // 아까넣은 친구들과 다 같이 모움 (투입 준비)
                Double needQty_temp =  needQty;
                for (ProcessStockDTO psDto : psList) {
                    // 재공재고에 해당 재고가 존재한다면 psDto : 재공재고, riDto : 라우팅 아이템(인풋 아이템, 아웃풋 아이템으로 사용)
                    if (psDto.getItem().getItemCd().equals(riDto.getInputItem().getItemCd()) &&
                            psDto.getLocation().equals(riDto.getRouting().getProcesses().getProcCd())) {
                        // 필요한 자재 저장해놔
                        inputItem.add(psDto);

                        if (psDto.getQty() >= needQty_temp) {
                            psDto.setConsumption(Long.valueOf(String.valueOf(needQty_temp)));
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
            for (ProcessStockDTO data : inputItem) {
                // 등록할 Lot 이력 생성
                LotNoLogDTO lDto = new LotNoLogDTO();
                lDto.setIItem(data.getItem());
                lDto.setProcesses(pDto.getProcesses());
                lDto.setInputQty(data.getConsumption());
                lDto.setPLotSeq1(data.getLotNoLog().getPLotSeq1());
                data.setQty(data.getQty() - data.getConsumption());
                data.setConsumption(0L);
                lList.add(lDto);
            }


            lotRepository.saveAll(lList.stream().map(LotNoLogDTO::createLotNoLog).collect(Collectors.toList()));
            procStockRepository.saveAll(psList.stream().map(ProcessStockDTO::createProcessStock).collect(Collectors.toList()));


            System.out.println("최종 재공재고 ");
            psList.stream().forEach(System.out::println);
            System.out.println("최종 lot list: ");
            lList.stream().forEach(System.out::println);

        }
        return null;
    }

    @Override
    public LotNoLogDTO saveOutput(List<ProductionDTO> pList) {
        return null;
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
    public LotNoLogDTO saveReceiving(WarehouseLogDTO wDto) {
        // 최초 LotNoLog 생성
        LotNoLogDTO lDto = new LotNoLogDTO();
        //lDto.setLotNo(lotRepository.createLotNo(wDto.getLotNo()));
        lDto.setLotNo(wDto.getLotNo());
        lDto.setFStockNo(-1L);
        lDto.setInputQty(wDto.getQty());
        lDto.setOutputQty(wDto.getQty());
        lDto.setIItem(wDto.getItem());
        lDto.setOItem(wDto.getItem());
        lDto.setRemark(wDto.getInoutNo());
        lDto = LotNoLogDTO.of(lotRepository.save(lDto.createLotNoLog()));
        return lDto;
    }
}
