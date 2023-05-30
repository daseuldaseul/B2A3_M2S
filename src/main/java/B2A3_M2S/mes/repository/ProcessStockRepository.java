package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ProcessStock;
import B2A3_M2S.mes.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProcessStockRepository extends JpaRepository<ProcessStock, Long> {

   /* List<Stock> findByItemOrderByRegDate(Item item);
    List<Stock> findByItem(Item item);

    List<Stock> findByQtyIsNot(Long qty);

    List<Stock> findByRegDate(LocalDate today);

    List<Stock> findByRegDateBetween(LocalDate startDate, LocalDate endDate);*/
    List<ProcessStock> findByQtyNot(Long qty);
    List<ProcessStock> findByItemAndQtyNot(Item item, Long qty);

}
