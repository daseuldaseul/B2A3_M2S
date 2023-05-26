package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {

    @Id
    private String orderNo;    //발주 번호

    private Long orderQty; //발주 수량

    private LocalDateTime orderDate;   //발주일

    private LocalDateTime dueDate;     //입고예정일

    private Character urgencyYn;    //긴급 요청 여부

    private String remark;

    //외래키

    @ManyToOne
    @JoinColumn(name = "company_cd")
    private Company company;  //업체 코드

    @ManyToOne
    @JoinColumn(name = "material_cd")
    private Item item;    //자재 기본키

    //공통코드
    private String purchaseState;   //발주 상태

}
