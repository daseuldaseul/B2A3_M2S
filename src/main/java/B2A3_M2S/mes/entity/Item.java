package B2A3_M2S.mes.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity(name = "item")
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
public class Item extends BaseTimeEntity{
    @Id
    @Column(name="item_cd")
    private String itemCd;        //품목 코드

    private String itemNm;      //품목명

    private Long stock;         //적정 재고

    private Long standard;      //규격

    private String remark;      //비고

    private Character useYn;    //사용유무

    private Long orderMin;

    private Long orderMax;
    /**
        외래키 설정해야함
     **/
    //공통코드
    private String itemGb;      //품목 구분

    private String itemType;    //품목 타입

    private String itemUnit;       //재고 단위

    private String sUnit;   //규격 단위
    //공통코드

    @ManyToOne
    @JoinColumn(name = "company_cd")
    private Company company;   // 업체코드

}
