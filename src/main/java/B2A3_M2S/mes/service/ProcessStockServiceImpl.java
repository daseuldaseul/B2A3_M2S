package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.dto.StockDto;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.ProcessStock;
import B2A3_M2S.mes.repository.LotNoLogRepository;
import B2A3_M2S.mes.repository.ProcessStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessStockServiceImpl implements ProcessStockService {
    @Autowired
    ProcessStockRepository stockRepository;
    @Autowired
    LotNoLogRepository lotNoLogRepository;

    @Override
    public List<ProcessStockDTO> getProcStockList(String location) {
        List<ProcessStockDTO> procStockList = new ArrayList<>();
        procStockList = ProcessStockDTO.of(stockRepository.findByQtyNotAndLocation(0L, location));
        List<LotNoLogDTO> lotNoLogs = LotNoLogDTO.of(lotNoLogRepository.findByoItemNullAndProcesses_procCd(location));

        for (LotNoLogDTO lDto : lotNoLogs) {
            ProcessStockDTO pDto = new ProcessStockDTO();
            pDto.setItem(lDto.getIItem());
            pDto.setQty(lDto.getInputQty());
            pDto.setLocation("투입");
            procStockList.add(pDto);
            LotNoLog lEntity = lotNoLogRepository.findByLotSeq(lDto.getPLotSeq1());

            if(lEntity != null)
                pDto.setLotNoLog(LotNoLogDTO.of(lEntity));
        }

        return procStockList;
    }
}
