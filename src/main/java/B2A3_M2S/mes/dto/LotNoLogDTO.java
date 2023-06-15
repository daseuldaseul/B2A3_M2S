package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotNoLogDTO extends BaseTimeEntity2 {
    private Long lotSeq;
    private Long pLotSeq1;
    private Long pLotSeq2;
    private Long fStockNo;  //최종 재고 번호
    private ItemDto iItem;    //품목 코드
    private String lotNo;   // LotNo
    private ProcessesDto processes; //공정 코드
    private Long inputQty;  //투입 수량
    private Long outputQty;  //생산 수량
    private Long outputQty2;  //생산 수량(임시저장)
    private String remark;  //비고
    private ItemDto oItem;
    private ProductionDTO production;

    public static ModelMapper modelMapper = new ModelMapper();

    public static List<LotNoLogDTO> of(List<LotNoLog> lotNoLotList) {
        return modelMapper.map(lotNoLotList, new TypeToken<List<LotNoLogDTO>>() {
        }.getType());
    }

    public static LotNoLogDTO of(LotNoLog lotNoLog) {
        return modelMapper.map(lotNoLog, LotNoLogDTO.class);
    }

    public LotNoLog createLotNoLog() {
        // EquipDto -> Equipment 변환
        return modelMapper.map(this, LotNoLog.class);
    }
}
