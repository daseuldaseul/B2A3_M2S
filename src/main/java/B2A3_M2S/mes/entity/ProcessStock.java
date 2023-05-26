package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStock extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockNo;       //재고 번호
    private String location;    //위치
    private Long qty;           //수량
    private String remark;      //비고

    //외래키
    @ManyToOne
    @JoinColumn(name = "itemCd")
    private Item item;

    @OneToOne
    @JoinColumn(name = "lotSeq")
    private LotNoLog lotNoLog;
}
