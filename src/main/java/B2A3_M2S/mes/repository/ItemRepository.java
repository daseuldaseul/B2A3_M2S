package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import B2A3_M2S.mes.entity.Equipment;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>, QuerydslPredicateExecutor<Item> {
    Item findByItemNm(String itemNm);

    Item findByItemCd(String itemCd);
    List<Item> findByItemGb(String itemGb);

    List<Item> findAllByItemNmContains(String itemNm);
    
    List<Item> findByItemNmContainingAndItemCdContaining(String itemNm, String itemCd);

    List<Item> findByItemCdContaining(String itemCd);

    List<Item> findAll();

    List<Item> findByItemNmContaining(String itemNm);
}
