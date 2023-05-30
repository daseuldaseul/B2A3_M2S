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



    @OneToOne // 왜 있지?
    private LotNoLog lotNoLog; // 왜 있지?

    private String status;              // 계획상태

    private boolean completion;
    private boolean firstGb;            // 첫번째 구분
    private boolean lastGb;             // 마지막 구분

    @ManyToOne
    @JoinColumn(name = "item_cd")
    private Item item;                   //제품 코드

    // 이친구도 ,,, 공정코드 뺄까 생각 ㄱㄱ 없으면 쿼리 복잡해지긴함
    @ManyToOne
    @JoinColumn(name = "routing_no")
    private Routing routing;
}
