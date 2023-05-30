package B2A3_M2S.mes.entity;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoutingItem extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routingSeq;       // 기본키

    @ManyToOne
    @JoinColumn(name = "routing_no")
    private Routing routing;        // 외래키

    @ManyToOne
    @JoinColumn(name = "input_item")
    private Item inputItem;     // 들어가는 친구

    @ManyToOne
    @JoinColumn(name = "output_item")
    private Item outputItem;    // 나오는 친구

    private String remark;      // 비고
    private Character useYn;    // 사용유무

    // 이놈도 추가해야하군나,,,,,,,,
    @ManyToOne
    @JoinColumn(name = "bom_no")
    private BOM bom;
}
