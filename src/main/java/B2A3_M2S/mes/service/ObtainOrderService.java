package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.QObtainOrder;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ObtainOrderService {

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Transactional
    public List<ObtainOrder> searchObtainOrder(String companyCd, String companyNm, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                               String orderState, String itemCd, String itemNm, String orderCd) {
        QObtainOrder qObtainOrder = QObtainOrder.obtainOrder;
        BooleanBuilder builder = new BooleanBuilder();

        if(companyCd != null) {
            builder.and(qObtainOrder.company.companyCd.contains(companyCd));
        }

        if(companyNm != null) {
            builder.and(qObtainOrder.company.companyNm.contains(companyNm));
        }

        if(startDateTime != null && endDateTime != null) {
            builder.and(qObtainOrder.orderDate.between(startDateTime, endDateTime));
        }

        if(!orderState.equals("미선택")) {
            builder.and(qObtainOrder.orderState.eq(orderState));
        }

        if(itemCd != null) {
            builder.and(qObtainOrder.item.itemCd.contains(itemCd));
        }

        if(itemNm != null) {
            builder.and(qObtainOrder.item.itemNm.contains(itemNm));
        }

        if(orderCd != null) {
            builder.and(qObtainOrder.orderCd.contains(orderCd));
        }

        return (List<ObtainOrder>) obtainOrderRepository.findAll(builder);

    }
}
