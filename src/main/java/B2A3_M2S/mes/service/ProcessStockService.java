package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.dto.StockDto;

import java.util.ArrayList;
import java.util.List;

public interface ProcessStockService {
    public List<ProcessStockDTO> getProcStockList(String location);
}
