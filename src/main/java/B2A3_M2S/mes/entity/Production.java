package B2A3_M2S.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "production")
@Table(name = "production")
public class Production extends BaseTimeEntity{

    @Id
    private String planNo;      //계획 번호

    private Long planQty;       //생산 수량

    private LocalDateTime startDate;    //생산 시작 일시

    private LocalDateTime endDate;      //생상 종료 일시

    private String remark;


    /**외래키**/

//    @OneToOne
//    @JoinColumn(name = "order_cd")
//    private ObtainOrder obtainOrder;    //수주 코드
//
//    @ManyToOne
//    @JoinColumn(name = "proc_cd")
//    private Processes processes;     //공정 코드

}
