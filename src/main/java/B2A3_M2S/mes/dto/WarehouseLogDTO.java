package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.BaseTimeEntity;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.WarehouseLog;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseLogDTO {
    private String inoutNo;     //입출고 번호
    private String logGb;   //입출고 구분
    private String lotNo;
    private Long qty;   //수량
    private LocalDateTime logDate;     //입출고 일시
    private String remark;  //비고

    private ItemDto item;    //품목 코드

    public static ModelMapper modelMapper = new ModelMapper();


    public static List<WarehouseLogDTO> of(List<WarehouseLog> list) {
        return modelMapper.map(list, new TypeToken<List<WarehouseLogDTO>>() {
        }.getType());
    }

    public static WarehouseLogDTO of(WarehouseLog warehouseLog) {
        return modelMapper.map(warehouseLog, WarehouseLogDTO.class);
    }

    public WarehouseLog createLotNoLog() {
        // EquipDto -> Equipment 변환
        return modelMapper.map(this, WarehouseLog.class);
    }
}
