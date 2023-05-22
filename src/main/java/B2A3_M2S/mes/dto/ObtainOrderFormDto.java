package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class ObtainOrderFormDto {

    public static ModelMapper modelMapper = new ModelMapper();

    private String orderCd;    //수주코드

    private Long qty;   //수주량

    private LocalDateTime dueDate;  //납기일

    private LocalDateTime orderDate;   //수주일

    private String shipAdr; //배송지

    private String remark;  //비고

    private Character useYn; //사용유무

    private Company company; //업체 코드

    private Item item;    //품목 코드

    private String orderState;   //수주 상태

    private String orderUnit;    //단위
    public ObtainOrder createObtainOrder(){
        return modelMapper.map(this, ObtainOrder.class);
    }
}
