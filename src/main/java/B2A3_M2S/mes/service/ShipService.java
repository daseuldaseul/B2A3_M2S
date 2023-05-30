package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.QShip;
import B2A3_M2S.mes.entity.Ship;
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

    @Transactional
    public List<Ship> searchShip(String shipNo, String companyCd, String companyNm, String obtainOrderCd,
                                    String itemCd, String itemNm,
                                    LocalDate startOrderDate, LocalDate endOrderDate,
                                    LocalDate startShipDate, LocalDate endShipDate,
                                    LocalDate startDueDate, LocalDate endDueDate) {


        LocalDateTime startOrdertDateTime = null;
        LocalDateTime endOrderDateTime = null;
        LocalDateTime startShipDateTime = null;
        LocalDateTime endShipDateTime = null;
        LocalDateTime startDueDateTime = null;
        LocalDateTime endDueDateTime = null;
        if(startOrderDate != null && endOrderDate != null) {
            startOrdertDateTime = LocalDateTime.of(startOrderDate, LocalTime.MIN);
            endOrderDateTime = LocalDateTime.of(endOrderDate, LocalTime.MAX);
        }
        if(startShipDate != null && endShipDate != null) {
            startShipDateTime = LocalDateTime.of(startShipDate, LocalTime.MIN);
            endShipDateTime = LocalDateTime.of(endShipDate, LocalTime.MAX);
        }
        if(startDueDate != null && endDueDate != null) {
            startDueDateTime = LocalDateTime.of(startDueDate, LocalTime.MIN);
            endDueDateTime = LocalDateTime.of(endDueDate, LocalTime.MAX);
        }

        QShip qShip = QShip.ship;
        BooleanBuilder builder = new BooleanBuilder();

        if (shipNo != null){
            builder.and(qShip.shipNo.contains(shipNo));
        }
        if (companyCd != null) {
            builder.and(qShip.obtainOrder.company.companyCd.contains(companyCd));
        }
        if (companyNm != null) {
            builder.and(qShip.obtainOrder.company.companyNm.contains(companyNm));
        }
        if (obtainOrderCd != null){
            builder.and(qShip.obtainOrder.orderCd.contains(obtainOrderCd));
        }
        if (itemCd != null) {
            builder.and(qShip.obtainOrder.item.itemCd.contains(itemCd));
        }
        if (itemNm != null) {
            builder.and(qShip.obtainOrder.item.itemNm.contains(itemNm));
        }
        if (startOrderDate != null && endOrderDate != null) {
            builder.and(qShip.obtainOrder.orderDate.between(startOrdertDateTime, endOrderDateTime));
        }
        if (startShipDate != null && endShipDate != null) {
            builder.and(qShip.shipDate.between(startShipDateTime, endShipDateTime));
        }
        if (startDueDate != null && endDueDate != null) {
            builder.and(qShip.obtainOrder.dueDate.between(startDueDateTime, endDueDateTime));
        }
        return (List<Ship>) shipRepository.findAll(builder);


    }
}
