package B2A3_M2S.mes.service;

import B2A3_M2S.mes.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Autowired
    private ProcessesRepository procRepository;
    @Autowired
    private RoutingRepository routingRepository;
    @Autowired
    private BOMRepository bomRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private EquipRepository equipRepository;
    @Autowired
    private ObtainOrderRepository obtainOrderRepository;

    public static double cabbageYield = 0.8;
    public static double garlicYield = 0.6;

    @Override
    public LocalDateTime getDeliveryDate(String itemCd, Long qty) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return null;
    }
}
