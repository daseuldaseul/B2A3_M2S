package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductionRepository extends JpaRepository<Production, String>, QuerydslPredicateExecutor<Production> {

}
