package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bom")
@Table(name = "bom")
public class BOM extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bomNo;         //BOM 번호

    private Double consumption;   //소모량

    private String remark;      //비고

    private Character useYn;    //사용유무


    /**외래키 설정**/

    @ManyToOne
    @JoinColumn(name = "product_cd", referencedColumnName = "item_cd")
    private Item pItem;     //제품 코드

    @ManyToOne
    @JoinColumn(name = "material_cd", referencedColumnName = "item_cd")
    private Item mItem;    //자재 코드


}
