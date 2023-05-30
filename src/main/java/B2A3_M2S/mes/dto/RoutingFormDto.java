package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.Routing;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RoutingFormDto {
    private Long routingNo; //라우팅번호

    private Long capacity;     // 총 수용량

    private Long pOrder;  //순서

    private String remark;  //비고

    private Character useYn;    //사용유무

    private ItemDto item;    //품목 코드

    private ProcessesDto processes; //공정 코드

    private LocalDate regDate;

    private LocalDate modDate;

    private Double yield;

    public static ModelMapper modelMapper = new ModelMapper();

    public Routing createRouting() {
        return modelMapper.map(this, Routing.class);
    }

    public static RoutingFormDto of(Routing routing) {
        return modelMapper.map(routing, RoutingFormDto.class);
    }

    public static List<RoutingFormDto> of(List<Routing> routingList) {
        return modelMapper.map(routingList, new TypeToken<List<RoutingFormDto>>() {}.getType());
    }

}
