package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long>, QuerydslPredicateExecutor<Stock> {

    List<Stock> findByItem_ItemCdOrderByRegDate(String itemCd);
 }
