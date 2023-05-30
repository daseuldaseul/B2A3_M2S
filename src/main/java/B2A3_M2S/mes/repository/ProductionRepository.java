package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, String>, QuerydslPredicateExecutor<Production> {
    List<Production> findByObtainOrderOrderCd(String orderCd);

    List<Production> findByObtainOrder(ObtainOrder obtainOrder);
    //    List<Production> findByObtainOrder(List<ObtainOrder> obtainOrderList);
    @Query("SELECT MAX(p.endDate) FROM Production p")
    LocalDateTime findByMaxEndDate();

    @Query("SELECT p FROM Production p WHERE  p.startDate <= CURRENT_TIMESTAMP AND" +
            " CURRENT_TIMESTAMP <= p.endDate AND p.status = 'STATUS01'")
    List<Production> findByStartDateAndEndDateAndStatus();
    @Query("SELECT p FROM Production p WHERE p.endDate <= CURRENT_TIMESTAMP AND (p.status = 'STATUS01' or p.status = 'STATUS02')")
    List<Production> findByEndDateAndStatus();
}
