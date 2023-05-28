package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity(name = "routing")
@Table(name = "routing")
@AllArgsConstructor
@NoArgsConstructor
public class Routing extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routingNo; //라우팅번호

    private Long capacity;     // 총 수용량

    private Long pOrder;  //순서

    private String remark;  //비고

    private Character useYn;    //사용유무


    /**외래키 설정**/

    @ManyToOne
    @JoinColumn(name = "item_cd")
    private Item item;    //품목 코드

    @ManyToOne
    @JoinColumn(name = "proc_cd")
    private Processes processes; //공정 코드

    // 내가 추가함
    private Double yield;       // 수율
}
