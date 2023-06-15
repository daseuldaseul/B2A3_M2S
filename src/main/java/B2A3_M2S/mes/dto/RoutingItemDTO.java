package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.entity.RoutingItem;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class RoutingItemDTO {
    private Long routingSeq;       // 기본키
    private RoutingDto routing;        // 외래키
    private ItemDto inputItem;     // 들어가는 친구
    private ItemDto outputItem;    // 나오는 친구
    private String remark;      // 비고
    private Character useYn;    // 사용유무

    // 일단 보자,,
    private BOMDTO bom;

    public static ModelMapper modelMapper = new ModelMapper();
    public RoutingItem createRoutingItem() {
        return modelMapper.map(this, RoutingItem.class);
    }
    public List<RoutingItem> createRoutingItem(List<RoutingItemDTO> routingDtoList) {
        return modelMapper.map(this, new TypeToken<List<RoutingItem>>() {}.getType());
    }

    public static RoutingItemDTO of(RoutingItem routingItem) {
        return modelMapper.map(routingItem, RoutingItemDTO.class);
    }

    public static List<RoutingItemDTO> of(List<RoutingItem> list) {
        return modelMapper.map(list, new TypeToken<List<RoutingItemDTO>>() {}.getType());
    }
}
