package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Processes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ObtainOrderRepository extends JpaRepository<ObtainOrder, String>, QuerydslPredicateExecutor<ObtainOrder> {
    List<ObtainOrder> findAll();

    List<ObtainOrder> findByOrderCd(String orderCd);





}
