package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ObtainOrderDto {
    private String orderCd;    //수주코드

    private Long qty;   //수주량

    private LocalDateTime dueDate;  //납기일

    private LocalDateTime orderDate;   //수주일

    private String shipAdr; //배송지

    private String remark;  //비고

    private Character useYn; //사용유무


    /**
     * 외래키
     **/

    private Company company; //업체 코드

    private Item item;    //품목 코드

    //공통 코드
    private String orderState;   //수주 상태

    private String orderStateNm;   //수주 상태 코드

    private String orderUnit;    //단위

    private String orderUnitNm;    //단위 코드
    //공통 코드

    private LocalDate regDate;

    private LocalDate modDate;

    public static ModelMapper modelMapper = new ModelMapper();

    public ObtainOrder createObtainOrder() {
        return modelMapper.map(this, ObtainOrder.class);
    }

    public static ObtainOrderDto of(ObtainOrder obtainOrder) {
        return modelMapper.map(obtainOrder, ObtainOrderDto.class);
    }

    public static List<ObtainOrderDto> of(List<ObtainOrder> obtainOrderList) {
        return modelMapper.map(obtainOrderList, new TypeToken<List<ObtainOrderDto>>() {
        }.getType());
    }
}
