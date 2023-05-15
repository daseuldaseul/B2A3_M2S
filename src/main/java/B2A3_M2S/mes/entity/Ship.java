package B2A3_M2S.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "ship")
@Table(name = "ship")
public class Ship extends BaseTimeEntity {

    @Id
    private String shipNo;  //출하 번호

    private String lotNo;

    private Long shipQty;   //출하 수량

    private LocalDateTime shipDate; //출하일

    private String remark;      //비고





    //외래키
//    @OneToOne
//    @JoinColumn(name = "order_cd")
//    private ObtainOrder obtainOrder;    //수주코드
}
