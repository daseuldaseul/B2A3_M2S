package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.PurchaseOrderRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Transactional
    public List<PurchaseOrder> searchPurchaseOrder(String companyCd, String companyNm, String purchaseState,
                                                   String itemCd, String itemNm, String orderNo,
                                                   LocalDateTime startDate, LocalDateTime endDate) {
        QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;
        BooleanBuilder builder = new BooleanBuilder();
        if (companyCd != null) {
            builder.and(qPurchaseOrder.company.companyCd.contains(companyCd));
        }
        if (companyNm != null) {
            builder.and(qPurchaseOrder.company.companyNm.contains(companyNm));
        }
        if (itemCd != null) {
            builder.and(qPurchaseOrder.item.itemCd.contains(itemCd));
        }
        if (itemNm != null) {
            builder.and(qPurchaseOrder.item.itemNm.contains(itemNm));
        }
        if (orderNo != null) {
            builder.and(qPurchaseOrder.orderNo.contains(orderNo));
        }
        if (!purchaseState.equals("none")){
            builder.and(qPurchaseOrder.purchaseState.eq(purchaseState));
        }
        if (startDate != null && endDate != null) {
            builder.and(qPurchaseOrder.orderDate.between(startDate, endDate));
        }
        return (List<PurchaseOrder>) purchaseOrderRepository.findAll(builder);
    }

}
