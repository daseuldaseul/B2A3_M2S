package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.Routing;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RoutingDto {
    private Long routingNo; //라우팅번호

    private Long pTime;     //생산 시간

    private Long pOrder;  //순서

    private String remark;  //비고

    private Character useYn;    //사용유무

    private Item item;    //품목 코드

    private Processes processes; //공정 코드

    private LocalDate regDate;

    private LocalDate modDate;

    public static ModelMapper modelMapper = new ModelMapper();

    public Routing createRouting() {
        return modelMapper.map(this, Routing.class);
    }
    public List<Routing> createRouting(List<RoutingDto> routingDtoList) {
        return modelMapper.map(this, new TypeToken<List<Routing>>() {}.getType());
    }

    public static RoutingDto of(Routing routing) {
        return modelMapper.map(routing, RoutingDto.class);
    }

    public static List<RoutingDto> of(List<Routing> routingList) {
        return modelMapper.map(routingList, new TypeToken<List<RoutingDto>>() {}.getType());
    }

}
