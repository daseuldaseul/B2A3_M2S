package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Routing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface RoutingRepository extends JpaRepository<Routing, Long>, QuerydslPredicateExecutor<Routing> {
    List<Routing> findAll();

    // 품목번호를 넣으면 품목에 대한 routing 순서가 보이는
    Routing findByRoutingNo(Long RoutingNo);

    List<Routing> findByItem(Item item);

}
