package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.UnitConversion;
import B2A3_M2S.mes.entity.WarehouseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface WarehouseLogRepository extends JpaRepository<WarehouseLog, Long> ,QuerydslPredicateExecutor<WarehouseLog> {

List<WarehouseLog> findAll();

}
