package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ObtainOrderDto;

import java.time.LocalDateTime;

public interface CalculatorService {
    LocalDateTime getDeliveryDate(LocalDateTime startTime, ObtainOrderDto oDto);

    void schedulerApplication();
}
