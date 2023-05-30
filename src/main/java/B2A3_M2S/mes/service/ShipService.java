package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.ShipDto;
import B2A3_M2S.mes.dto.ShipFormDto;
import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ShipRepository;
import B2A3_M2S.mes.repository.StockRepository;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    @Autowired
    StockRepository stockRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void createShip(String orderCd){
        ObtainOrder obtainOrder = obtainOrderRepository.findSingleByOrderCd(orderCd);
        ObtainOrderDto obtainOrderDto = ObtainOrderDto.of(obtainOrder);
        List<Stock> stockList = stockRepository.findByItemAndQtyNotOrderByLotNoAsc(obtainOrder.getItem(), 0L);
        long qty = obtainOrderDto.getQty();
        for(Stock stock : stockList){
            ShipFormDto shipFormDto = new ShipFormDto();
            NumberingService<Ship> service = new NumberingService<>(entityManager, Ship.class);
            String sn = service.getNumbering("shipNo", NumPrefix.SHIP);
            shipFormDto.setShipNo(sn);
            shipFormDto.setObtainOrder(obtainOrderDto);
            shipFormDto.setShipDate(obtainOrderDto.getDueDate());
            shipFormDto.setLotNo(stock.getLotNo());
            if(stock.getQty() <= qty){

                shipFormDto.setShipQty(stock.getQty());
                qty -= stock.getQty();
                stock.setQty(0L);
                stockRepository.save(stock);
            }else{
                shipFormDto.setShipQty(qty);
                stock.setQty(stock.getQty() - qty);
                qty = 0;
                stockRepository.save(stock);
            }
            shipRepository.save(shipFormDto.createShip());
            if(qty == 0){
                break;
            }
        }
        obtainOrderDto.setOrderState("ORDER03");
        obtainOrderRepository.save(obtainOrderDto.createObtainOrder());


    }

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
