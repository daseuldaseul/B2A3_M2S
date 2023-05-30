package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.QObtainOrder;
import B2A3_M2S.mes.entity.QProduction;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductionService {
    @Autowired
    ObtainOrderRepository obtainOrderRepository;


    @Transactional
    public List<ObtainOrder> searchProduction(String orderCd, String itemNm, String itemCd,
                                             LocalDateTime dueStartDateTime, LocalDateTime dueEndDateTime) {
        QObtainOrder qObtainOrder = QObtainOrder.obtainOrder;
        BooleanBuilder builder = new BooleanBuilder();

        if (orderCd != null) {
            builder.and(qObtainOrder.orderCd.contains(orderCd));
        }

        if (itemNm != null) {
            builder.and(qObtainOrder.item.itemNm.contains(itemNm));
        }

        if (itemCd != null) {
            builder.and(qObtainOrder.item.itemCd.contains(itemCd));
        }

        if (dueStartDateTime != null && dueEndDateTime != null) {
            builder.and(qObtainOrder.dueDate.between(dueStartDateTime, dueEndDateTime));
        }


        return (List<ObtainOrder>) obtainOrderRepository.findAll(builder);

    }

}
