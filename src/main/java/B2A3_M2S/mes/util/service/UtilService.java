package B2A3_M2S.mes.util.service;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.util.enums.NumPrefix;

import java.util.List;

public interface UtilService {

    // 출고시
//    ProcessStockDTO saveInput(WarehouseLogDTO wDto);

    // 각 공정별 Input
    LotNoLogDTO saveInput(List<ProductionDTO> pList);

    // 각 공정별 생산량 Update
    LotNoLogDTO saveOutput(List<ProductionDTO> pList);

    // 포장 완료시 호출
    LotNoLogDTO saveFinalOutput(LotNoLogDTO log);

    // 입고시 Lot 가져옴
    String getLotNo(NumPrefix numbering);

    // 입고시 log 찍음
    LotNoLogDTO saveReceiving(WarehouseLogDTO wDto);

    public String getLotNo(String pcodCd);
}
