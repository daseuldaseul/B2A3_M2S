package B2A3_M2S.mes.util.service;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.ProductionDTO;
import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.util.enums.NumPrefix;

import java.util.List;

public interface UtilService {
    /*
    lot_seq
    mod_date
    reg_date
    c_lot_seq1
    c_lot_seq2
    f_stock_no
    input_qty
    lot_no
    output_qty
    p_lot_seq1
    p_lot_seq2
    remark
    item_cd
    proc_cd
     */


    // 출고시
    LotNoLogDTO saveInput(WarehouseLogDTO wDto);

    // 각 공정별 Input
    LotNoLogDTO saveInput(List<ProductionDTO> pList);

    // 각 공정별 생산량 Update
    LotNoLogDTO saveOutput(List<ProductionDTO> pList);

    // 포장 완료시 호출
    LotNoLogDTO saveFinalOutput(LotNoLogDTO log);

    // 입고시 Lot 가져옴
    String getLotNo(NumPrefix numbering);

}
