package B2A3_M2S.mes.dto;


import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderDto {

    public static ModelMapper modelMapper = new ModelMapper();

    private String orderNo;    //발주 번호

    private Long orderQty; //발주 수량

    private LocalDateTime orderDate;   //발주일

    private LocalDateTime dueDate;     //입고예정일

    private Character urgencyYn;    //긴급 요청 여부

    private String remark;

    private CompanyDto company;  //업체 코드

    private ItemDto item;    //자재 기본키
    //공통코드
    private String purchaseState;   //발주 상태

    private String purchaseStateNm; //발주 상태 코드값

    private int progressPercent; //진행률
    public static List<PurchaseOrderDto> of(List<PurchaseOrder> purchaseList) {
        return modelMapper.map(purchaseList, new TypeToken<List<PurchaseOrderDto>>() {
        }.getType());
    }

    public static PurchaseOrderDto of(PurchaseOrder purchaseOrder) {
        return modelMapper.map(purchaseOrder, PurchaseOrderDto.class);
    }

}
