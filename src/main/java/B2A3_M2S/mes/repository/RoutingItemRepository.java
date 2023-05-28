package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.entity.RoutingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface RoutingItemRepository extends JpaRepository<RoutingItem, Long> {
    List<RoutingItem> findByRouting(Routing routing);
}
