package B2A3_M2S.mes.entity;

import B2A3_M2S.mes.dto.BOMDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@SqlResultSetMapping(
        name = "BOMDTOMapping",
        classes = @ConstructorResult(
                targetClass = BOMDTO.class,
                columns = {
                        @ColumnResult(name = "bom_no", type = Long.class),
                        @ColumnResult(name = "remark", type = String.class),
                        @ColumnResult(name = "consumption", type = Double.class),
                        @ColumnResult(name = "use_yn", type = Character.class),
                        @ColumnResult(name = "reg_date", type = LocalDate.class),
                        @ColumnResult(name = "mod_date", type = LocalDate.class),
                        @ColumnResult(name = "depth", type = Integer.class),
                        @ColumnResult(name = "need_qty", type = Double.class),
                        @ColumnResult(name = "standard", type = Long.class),
                        @ColumnResult(name = "product_cd", type = String.class),
                        @ColumnResult(name = "material_cd", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "BOM.finNeedQtyBypItem",
        query = "WITH recursive rc as ("
                + " SELECT std.bom_no, std.product_cd, std.material_cd, 1 as depth, :standard as standard,"
                + " (std.consumption * :standard / std.standard) as consumption, std.remark, std.use_yn,"
                + " std.reg_date, std.mod_date"
                + " FROM bom std"
                + " WHERE std.product_cd = :productCode"
                + " UNION"
                + " SELECT b.bom_no, b.product_cd, b.material_cd, r.depth + 1 as depth, r.consumption as standard,"
                + " (r.consumption / b.standard * b.consumption) as consumption, b.remark, b.use_yn,"
                + " b.reg_date, b.mod_date"
                + " FROM bom b"
                + " INNER JOIN rc r"
                + " ON b.product_cd = r.material_cd"
                + " )"
                + " SELECT a.bom_no, a.product_cd, a.material_cd, a.depth, a.standard, a.consumption,"
                + " IFNULL((SELECT (a.consumption - (sum(ifnull(qty, 0)) - sum(ifnull(plan_qty, 0)))) FROM stock  WHERE a.material_cd = item_cd GROUP BY item_cd), a.consumption) AS need_qty, a.reg_date, a.mod_date,"
                + " a.remark, a.use_yn"
                + " FROM rc a",
        resultSetMapping = "BOMDTOMapping"
)
public class BOM extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bomNo;         //BOM 번호

    private Double consumption;   //소모량

    private String remark;      //비고

    private Character useYn;    //사용유무

    private Long standard;      // 기준
    /**외래키 설정**/

    @ManyToOne
    @JoinColumn(name = "product_cd", referencedColumnName = "item_cd")
    private Item pItem;     //제품 코드

    @ManyToOne
    @JoinColumn(name = "material_cd", referencedColumnName = "item_cd")
    private Item mItem;    //자재 코드


}
