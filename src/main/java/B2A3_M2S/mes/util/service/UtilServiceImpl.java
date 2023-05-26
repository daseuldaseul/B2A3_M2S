package B2A3_M2S.mes.util.service;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.WarehouseLog;
import B2A3_M2S.mes.repository.LotNoLogRepository;
import B2A3_M2S.mes.repository.ProcessStockRepository;
import B2A3_M2S.mes.util.enums.NumPrefix;
import org.springframework.beans.factory.annotation.Autowired;

public class UtilServiceImpl implements UtilService {
    @Autowired
    LotNoLogRepository lotRepository;
    ProcessStockRepository procRepository;

    /**
     *
     * 출고시 호출하는 메소드 입니다.
     * @param "Enum 코드번호 (출고)"
     * @return 저장된 LotNoLog 객체 반환
     *
     */
    @Override
    public LotNoLogDTO saveInput(WarehouseLogDTO wDto) {
        // 최초 LotNoLog 생성
        LotNoLogDTO lDto = new LotNoLogDTO();
        lDto.setLotNo(lotRepository.createLotNo(wDto.getLotNo()));
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
        pDto = ProcessStockDTO.of(procRepository.save(pDto.createProcessStock()));
        return lDto;
    }

    /***
     * 출고를 제외한 각 공정 단계에서 자재 투입시 호출하는 메소드 입니다.
     * @param "Enum 코드번호 (출고)"
     * @return 저장된 LotNoLog 객체 반환
     */

    @Override
    public LotNoLogDTO saveInput(LotNoLogDTO lDto) {
        return null;
    }

    @Override
    public LotNoLogDTO saveOutput(LotNoLogDTO log) {
        log.setLotNo(log.getProcesses().getProcCd());
        return LotNoLogDTO.of(lotRepository.save(log.createLotNoLog()));
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
}
