package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity(name = "lot_no_log")
@Table(name = "lot_no_log")
@AllArgsConstructor
@NoArgsConstructor
public class LotNoLog extends BaseTimeEntity{

    @Id
    private Long lotSeq;

    //외래키 설정
    private Long pLotSeq1;
    private Long pLotSeq2;


    private Long cLotSeq1;
    private Long cLotSeq2;

    private Long fStockNo;  //최종 재고 번호

    @ManyToOne
    @JoinColumn(name = "item_cd")
    private Item item;    //품목 코드

    private String lotNo;   //

    @ManyToOne
    @JoinColumn(name = "proc_cd")
    private Processes processes; //공정 코드

    //외래키설정

    private Long inputQty;  //투입 수량

    private Long outputQty;  //생산 수량

    private String remark;  //비고


}
