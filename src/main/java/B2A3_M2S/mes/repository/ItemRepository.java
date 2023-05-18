package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Equipment;
import B2A3_M2S.mes.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String>, QuerydslPredicateExecutor<Item> {
    Item findByItemNm(String itemNm);

    List<Item> findByItemGb(String itemGb);
}
