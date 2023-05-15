package B2A3_M2S.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "warehouse_log")
@Table(name = "warehouse_log")
public class WarehouseLog extends BaseTimeEntity{

    @Id
    private String inoutNo;     //입출고 번호

    private String logGb;   //입출고 구분

    private String lotNo;

    private Long qty;   //수량

    private LocalDateTime date;     //입출고 일시

    private String remark;  //비고



    //외래키
//
//    @ManyToOne
//    @JoinColumn(name = "item_cd")
//    private Item item;    //품목 코드
}
