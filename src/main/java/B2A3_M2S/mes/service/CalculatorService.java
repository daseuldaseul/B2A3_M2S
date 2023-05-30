package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.ProductionDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface CalculatorService {
    LocalDateTime getDeliveryDate(LocalDateTime startTime, ObtainOrderDto oDto);

    void schedulerApplication();
    LotNoLogDTO saveInput(List<ProductionDTO> pList);
}
