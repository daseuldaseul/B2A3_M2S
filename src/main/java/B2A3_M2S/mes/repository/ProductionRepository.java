package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, String>, QuerydslPredicateExecutor<Production> {
//    List<Production> findByObtainOrder(List<ObtainOrder> obtainOrderList);
}
