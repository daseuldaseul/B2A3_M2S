package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import lombok.Data;

import javax.persistence.*;

@Data
public class ProcessStockDTO {
    private Long stockNo;   //재고 번호
    private String lotNo;
    private String location;    //위치
    private Long qty;   //수량
    private String remark;  //비고
    private Item item;
}
