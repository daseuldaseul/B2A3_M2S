package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, String>, QuerydslPredicateExecutor<PurchaseOrder> {

    List<PurchaseOrder> findAll();

    PurchaseOrder findByOrderNo(String orderNo);
//    PurchaseOrder findByPOrderDate(LocalDateTime time);
}


