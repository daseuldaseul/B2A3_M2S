package B2A3_M2S.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    private String pOrderNo;    //발주 번호

    private Long pOrderQty; //발주 수량

    private LocalDateTime pOrderDate;   //발주일

    private Character urgencyYn;    //긴급 요청 여부

    private String remark;

    //외래키

    @ManyToOne
    @JoinColumn(name = "company_cd")
    private Company companyCd;  //업체 코드

    @ManyToOne
    @JoinColumn(name = "item_cd")
    private Item materialCd;    //자재 기본키

    //공통코드
    private String state;   //발주 상태

}
