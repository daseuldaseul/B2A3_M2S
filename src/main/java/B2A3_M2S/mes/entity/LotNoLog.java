package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LotNoLog extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lotSeq;

    //외래키 설정
    private Long pLotSeq1;
    private Long pLotSeq2;

    private Long fStockNo;  //최종 재고 번호

    @ManyToOne
    @JoinColumn(name = "input_item_cd")
    private Item iItem;    //품목 코드
    private String lotNo;   // LotNo

    @ManyToOne
    @JoinColumn(name = "proc_cd")
    private Processes processes; //공정 코드

    //외래키설정
    private Long inputQty;  //투입 수량
    private Long outputQty;  //생산 수량
    private String remark;  //비고


    @ManyToOne
    @JoinColumn(name = "output_item_cd")
    private Item oItem;
}
