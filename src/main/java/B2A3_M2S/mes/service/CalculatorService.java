package B2A3_M2S.mes.service;

import java.time.LocalDateTime;

public interface CalculatorService {
    LocalDateTime getDeliveryDate(String itemCd, Long qty);
}
