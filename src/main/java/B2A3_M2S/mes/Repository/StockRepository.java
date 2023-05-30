package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long>, QuerydslPredicateExecutor<Stock> {

    List<Stock> findByItemOrderByRegDate(Item item);
    List<Stock> findByItem(Item item);

    List<Stock> findByQtyIsNot(Long qty);

    List<Stock> findByRegDate(LocalDate today);

    List<Stock> findByRegDateBetween(LocalDate startDate, LocalDate endDate);

    List<Stock> findByItem_ItemCd(String itemCd);


    @Query("SELECT SUM(s.qty) FROM Stock s WHERE s.item.itemCd = :itemCd")
    Integer  getSumQtyByItemCd(@Param("itemCd") String itemCd);
 }
