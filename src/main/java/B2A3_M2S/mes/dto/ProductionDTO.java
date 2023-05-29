package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Production;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductionDTO {
    private String planNo;      //계획 번호
    private Long planQty;       //계획 수량
    private Long prodQty;       //생산 수량
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime startDate;    //생산 시작 일시
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    private LocalDateTime endDate;      //생상 종료 일시



    private String remark;              // 비고
    private ObtainOrderDto obtainOrder;    //수주 코드
    private ProcessesDto processes;     //공정 코드
    private LotNoLogDTO lotNoLog;
    private boolean completion;

    public static ModelMapper modelMapper = new ModelMapper();

    public static List<ProductionDTO> of(List<Production> list) {
        return modelMapper.map(list, new TypeToken<List<ProductionDTO>>() {
        }.getType());
    }

    public static ProductionDTO of(Production production) {
        return modelMapper.map(production, ProductionDTO.class);
    }

    public Production createProduction() {
        return modelMapper.map(this, Production.class);
    }

}
