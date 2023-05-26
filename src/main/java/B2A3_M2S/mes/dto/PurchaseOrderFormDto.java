package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOrderFormDto {

    public static ModelMapper modelMapper = new ModelMapper();

    private String orderNo;    //발주 번호

    private Long orderQty; //발주 수량

    private LocalDateTime orderDate;   //발주일

    private LocalDateTime dueDate;     //입고예정일

    private Character urgencyYn;    //긴급 요청 여부

    private String remark;

    private CompanyDto company;  //업체 코드

    private ItemDto item;    //자재 기본키

    private String purchaseState;   //발주 상태

    public PurchaseOrder createPurchaseOrder() {
        return modelMapper.map(this, PurchaseOrder.class);
    }
}
