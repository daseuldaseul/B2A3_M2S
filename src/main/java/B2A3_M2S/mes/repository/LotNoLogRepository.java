package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.util.repository.UtilRepository;
import org.apache.tomcat.jni.Proc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LotNoLogRepository extends JpaRepository<LotNoLog, Long>, UtilRepository, QuerydslPredicateExecutor<LotNoLog> {

    List<LotNoLog> findAll();

    @Query("SELECT l FROM LotNoLog l WHERE l.processes.procCd = :procCd AND l.lotNo IS NULL")
    List<LotNoLog> findByProcCdAndLotNoNull(@Param("procCd") String procCd);

    //LotNoLog findByLotSeqAndLotNoNotNull(Long lotSeq);
    LotNoLog findByfStockNoAndLotNoNull(Long fStockNo);
//    List<LotNoLog> findByProcessesAndiItemAndLotNoNull(Processes processe, Item iItem);
    List<LotNoLog> findByiItemAndProcessesAndLotNoNull(Item iItem, Processes processes);

}
