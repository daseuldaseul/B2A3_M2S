package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.PurchaseOrderFormDto;
import B2A3_M2S.mes.entity.PurchaseOrder;
import B2A3_M2S.mes.entity.QPurchaseOrder;
import B2A3_M2S.mes.repository.BOMRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.PurchaseOrderRepository;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Component
@EnableScheduling
public class PurchaseOrderService {

    @Autowired
    BOMRepository bomRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @PersistenceContext
    private EntityManager entityManager;



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

    @Transactional
    public List<PurchaseOrder> searchPurchaseIn(String companyCd, String companyNm,
                                                   String itemCd, String itemNm, String orderNo,
                                                   LocalDateTime startDate, LocalDateTime endDate) {
        QPurchaseOrder qPurchaseOrder = QPurchaseOrder.purchaseOrder;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(qPurchaseOrder.purchaseState.eq("ORDER03"));
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
        if (startDate != null && endDate != null) {
            builder.and(qPurchaseOrder.dueDate.between(startDate, endDate));
        }
        return (List<PurchaseOrder>) purchaseOrderRepository.findAll(builder);
    }


    public int needQty(String itemNm, int qty) {
        String itemCd = itemRepository.findByItemNm(itemNm).getItemCd();
        List<BOMDTO> list2 = bomRepository.findNeedQtyBypItem(itemCd, qty);

        for(BOMDTO list : list2) {

            if (list.getMaterialCd().equals("M_001") || list.getMaterialCd().equals("M_002") ) {

                int orderMin = (int) (long) ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd())).getOrderMin();

                int orderMax = (int) (long) ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd())).getOrderMax();
                double needQty = list.getNeedQty();
                int n = 0;
                n = (int) needQty / orderMin;
                if (needQty % orderMin != 0) {
                    n++;
                }
                int orderQty = n * orderMin;
                System.out.println("=======================");
                System.out.println(orderQty);

                return orderQty;
            }
        } return 0;
    }

   @Transactional
   public void purchaseOrderCreate(String itemNm, int qty){
       String itemCd = itemRepository.findByItemNm(itemNm).getItemCd();
       List<BOMDTO> list2 = bomRepository.findNeedQtyBypItem(itemCd, qty);


       for(BOMDTO list : list2){

           if(list.getMaterialCd().charAt(0) == 'M' && !list.getMaterialCd().equals("M_006")) {

               int orderMin = (int)(long)ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd())).getOrderMin();

               int orderMax = (int)(long)ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd())).getOrderMax();
               double needQty = list.getNeedQty();
               int n = 0;
               n = (int)needQty / orderMin;
               if(needQty % orderMin != 0){
                   n++;
               }
               int orderQty = n * orderMin;
               System.out.println(orderQty);
               // 주문해야할 수량
               while (orderQty > 0) {
                   if(orderQty > orderMax){
                       test2(orderMax, list);
                       orderQty -= orderMax;


                   }else{
                       test2(orderQty, list);
                       orderQty = 0;
                   }


               }


           }

       }


   }

    @Transactional
    public void test2(int orderQty, BOMDTO list){
        NumberingService<PurchaseOrder> service = new NumberingService<>(entityManager, PurchaseOrder.class);
        String orderNo = service.getNumbering("orderNo", NumPrefix.PURCHASE_ORDER);



        ItemDto itemDto = ItemDto.of(itemRepository.findByItemCd(list.getMaterialCd()));
        PurchaseOrderFormDto purchaseOrderFormDto = new PurchaseOrderFormDto();

        LocalDateTime now = LocalDateTime.now();
        int time = itemDto.getCompany().getOrderTime();
        int day = itemDto.getCompany().getOrderDay();
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(time, 0));

        LocalDateTime dueTime = now;
        if (now.compareTo(dateTime) <= 0) {
            dueTime = dueTime.plusDays(day);
            dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));

        } else {
            dueTime = dueTime.plusDays(day + 1);
            dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10, 0));
        }


        DayOfWeek dayOfWeek = dueTime.getDayOfWeek();

        if(dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.FRIDAY){
            purchaseOrderFormDto.setUrgencyYn('N');
        }else{
            purchaseOrderFormDto.setUrgencyYn('Y');
        }


        purchaseOrderFormDto.setPurchaseState("ORDER01");
        purchaseOrderFormDto.setItem(itemDto);
        purchaseOrderFormDto.setCompany(itemDto.getCompany());
        purchaseOrderFormDto.setOrderNo(orderNo);
        purchaseOrderFormDto.setOrderQty((long) orderQty);
        purchaseOrderFormDto.setOrderDate(now);
        purchaseOrderFormDto.setDueDate(dueTime);

        PurchaseOrder purchaseOrder = purchaseOrderFormDto.createPurchaseOrder();
        purchaseOrderRepository.save(purchaseOrder);

    }

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void updateState(){

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findByPurchaseState("ORDER01");
        LocalDateTime now = LocalDateTime.now();
        for(PurchaseOrder list : purchaseOrderList){
            LocalDateTime dueTime = list.getDueDate();
            if (now.compareTo(dueTime) > 0) {
                list.setPurchaseState("ORDER03");
                purchaseOrderRepository.save(list);
            }
        }

    }
}
