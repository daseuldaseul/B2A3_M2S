package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.ProcessStock;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.util.List;

@Data
public class ProcessStockDTO {
    private Long stockNo;       //재고 번호
    private String location;    //위치
    private Long qty;           //수량
    private Long consumption;           //수량
    private String remark;      //비고
    private ItemDto item;
    private LotNoLogDTO lotNoLog;

    public static ModelMapper modelMapper = new ModelMapper();

    public static List<ProcessStockDTO> of(List<ProcessStock> list) {
        return modelMapper.map(list, new TypeToken<List<ProcessStockDTO>>() { }.getType());
    }

    public static ProcessStockDTO of(ProcessStock processStock) {
        return modelMapper.map(processStock, ProcessStockDTO.class);
    }
    public ProcessStock createProcessStock() {
        return modelMapper.map(this, ProcessStock.class);
    }
}
