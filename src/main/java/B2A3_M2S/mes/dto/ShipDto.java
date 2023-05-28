package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.RoutingItem;
import B2A3_M2S.mes.entity.Ship;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ShipDto {
    public static ModelMapper modelMapper = new ModelMapper();

    private String shipNo;  //출하 번호

    private String lotNo;

    private Long shipQty;   //출하 수량

    private LocalDateTime shipDate; //출하일

    private String remark;      //비고

    private ObtainOrderDto obtainOrder;

    public List<Ship> createShip(List<RoutingItemDTO> ShipDtoList) {
        return modelMapper.map(this, new TypeToken<List<Ship>>() {}.getType());
    }

    public static ShipDto of(Ship ship) {
        return modelMapper.map(ship, ShipDto.class);
    }

    public static List<ShipDto> of(List<Ship> list) {
        return modelMapper.map(list, new TypeToken<List<ShipDto>>() {}.getType());
    }
}
