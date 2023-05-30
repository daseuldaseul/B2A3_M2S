package B2A3_M2S.mes.entity;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Production extends BaseTimeEntity {

    @Id
    private String planNo;      //계획 번호
    private Long planQty;       //계획 수량
    private Long prodQty;       //생산 수량
    private LocalDateTime startDate;    //생산 시작 일시
    private LocalDateTime endDate;      //생상 종료 일시
    private String remark;              // 비고

    /**
     * 외래키
     **/

    @OneToOne
    @JoinColumn(name = "order_cd")
    private ObtainOrder obtainOrder;    //수주 코드

    @ManyToOne
    @JoinColumn(name = "proc_cd")
    private Processes processes;     //공정 코드

    @OneToOne
    private LotNoLog lotNoLog;
    private boolean completion;

}
