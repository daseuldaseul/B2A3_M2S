package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.util.repository.UtilRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface LotNoLogRepository extends JpaRepository<LotNoLog, Long>, UtilRepository, QuerydslPredicateExecutor<LotNoLog> {

    List<LotNoLog> findAll();
}
