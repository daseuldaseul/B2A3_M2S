package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.PurchaseOrderFormDto;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.PurchaseOrder;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class OrderTest {


//    @Autowired
//    BOMRepository bomRepository;
//
//    @Autowired
//    ItemRepository itemRepository;
//
//    @Autowired
//    PurchaseOrderRepository purchaseOrderRepository;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    @Test
//    public void test() {
//
//
//        List<BOMDTO> list2 = bomRepository.findNeedQtyBypItem("P_003", 5000);
//
//
//        for (BOMDTO list : list2) {
//
//            if (list.getMaterialCd().charAt(0) == 'M' && !list.getMaterialCd().equals("M_006")) {
//
//                int orderMin = (int) (long) ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd())).getOrderMin();
//
//                int orderMax = (int) (long) ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd())).getOrderMax();
//                double needQty = list.getNeedQty();
//                int n = 0;
//                n = (int) needQty / orderMin;
//                if (needQty % orderMin != 0) {
//                    n++;
//                }
//                int orderQty = n * orderMin;
//                System.out.println(orderQty);
//                // 주문해야할 수량
//                while (orderQty > 0) {
//                    if (orderQty > orderMax) {
//                        test2(orderMax, list);
//                        orderQty -= orderMax;
//                    } else {
//                        test2(orderQty, list);
//                        orderQty = 0;
//                    }
//
//
//                }
//            }
//        }
//
//
//    }
//
//    public void test2(int orderQty, BOMDTO list) {
//        NumberingService<PurchaseOrder> service = new NumberingService<>(entityManager, PurchaseOrder.class);
//        String orderNo = service.getNumbering("orderNo", NumPrefix.PURCHASE_ORDER);
//
//
//        PurchaseOrderFormDto purchaseOrderFormDto = new PurchaseOrderFormDto();
//
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime dueTime = now;
//
//
//        if (list.getMaterialCd().equals("M_001") || list.getMaterialCd().equals("M_002")) {
//            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
//            if (now.compareTo(dateTime) <= 0) {
//                dueTime = dueTime.plusDays(2);
//                dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
//
//            } else {
//                dueTime = dueTime.plusDays(3);
//                dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
//            }
//        } else if (list.getMaterialCd().equals("M_003") || list.getMaterialCd().equals("M_004") || list.getMaterialCd().equals("M_005")) {
//            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0));
//            if (now.compareTo(dateTime) <= 0) {
//                dueTime = dueTime.plusDays(3);
//                dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
//
//            } else {
//                dueTime = dueTime.plusDays(4);
//                dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
//            }
//        } else if (list.getMaterialCd().equals("M_007") || list.getMaterialCd().equals("M_008") || list.getMaterialCd().equals("M_009")) {
//            LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0));
//            if (now.compareTo(dateTime) <= 0) {
//                dueTime = dueTime.plusDays(2);
//                dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
//
//            } else {
//                dueTime = dueTime.plusDays(3);
//                dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
//            }
//        }
//
//        DayOfWeek dayOfWeek = dueTime.getDayOfWeek();
//
//        if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.FRIDAY) {
//            purchaseOrderFormDto.setUrgencyYn('N');
//        } else {
//            purchaseOrderFormDto.setUrgencyYn('Y');
//        }
//
//        ItemDto itemDto = ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd()));
//        purchaseOrderFormDto.setPurchaseState("ORDER01");
//        purchaseOrderFormDto.setItem(itemDto);
//        purchaseOrderFormDto.setCompany(itemDto.getCompany());
//        purchaseOrderFormDto.setOrderNo(orderNo);
//        purchaseOrderFormDto.setOrderQty((long) orderQty);
//        purchaseOrderFormDto.setOrderDate(now);
//        purchaseOrderFormDto.setDueDate(dueTime);
//
//        PurchaseOrder purchaseOrder = purchaseOrderFormDto.createPurchaseOrder();
//        purchaseOrderRepository.save(purchaseOrder);
//    }

}
