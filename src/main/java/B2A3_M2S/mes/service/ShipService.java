package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ShipRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ShipService {


    @Autowired
    ShipRepository shipRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Transactional
    public List<ObtainOrder> searchShip(String companyCd, String companyNm, String obtainOrderCd,
                                        String itemCd, String itemNm,
                                        LocalDate startOrderDate, LocalDate endOrderDate,
                                        LocalDate startDueDate, LocalDate endDueDate) {


        LocalDateTime startOrdertDateTime = null;
        LocalDateTime endOrderDateTime = null;
        LocalDateTime startDueDateTime = null;
        LocalDateTime endDueDateTime = null;

        if(startOrderDate != null && endOrderDate != null) {
            startOrdertDateTime = LocalDateTime.of(startOrderDate, LocalTime.MIN);
            endOrderDateTime = LocalDateTime.of(endOrderDate, LocalTime.MAX);
        }

        if(startDueDate != null && endDueDate != null) {
            startDueDateTime = LocalDateTime.of(startDueDate, LocalTime.MIN);
            endDueDateTime = LocalDateTime.of(endDueDate, LocalTime.MAX);
        }

        QObtainOrder qObtainOrder = QObtainOrder.obtainOrder;
        BooleanBuilder builder = new BooleanBuilder();

        if (companyCd != null) {
            builder.and(qObtainOrder.company.companyCd.contains(companyCd));
        }
        if (companyNm != null) {
            builder.and(qObtainOrder.company.companyNm.contains(companyNm));
        }
        if (obtainOrderCd != null){
            builder.and(qObtainOrder.orderCd.contains(obtainOrderCd));
        }
        if (itemCd != null) {
            builder.and(qObtainOrder.item.itemCd.contains(itemCd));
        }
        if (itemNm != null) {
            builder.and(qObtainOrder.item.itemNm.contains(itemNm));
        }
        if (startOrderDate != null && endOrderDate != null) {
            builder.and(qObtainOrder.orderDate.between(startOrdertDateTime, endOrderDateTime));
        }

        if (startDueDate != null && endDueDate != null) {
            builder.and(qObtainOrder.dueDate.between(startDueDateTime, endDueDateTime));
        }
        return (List<ObtainOrder>) obtainOrderRepository.findAll(builder);


    }
}
