package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.PurchaseOrder;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EnableScheduling
@Service
public class SchedulerService {
//    @Autowired
//    PurchaseOrderRepository repository;
//
//    @Scheduled(cron = "10 * * * * *")
//    @Transactional
//    public void run() {
//        LocalDateTime date = LocalDateTime.now();
//
//        List<PurchaseOrder> orderList = repository.findByPurchaseState("ORDER01");
//
//        for(PurchaseOrder order : orderList){
//            if(date.isAfter(order.getDueDate())){
//                order.setPurchaseState("ORDER03");
//
//            }
//        }
//
//    }

}

