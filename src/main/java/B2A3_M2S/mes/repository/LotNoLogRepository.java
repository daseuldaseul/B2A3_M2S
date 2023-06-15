package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.Production;
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

    LotNoLog findByfStockNoAndLotNoNull(Long fStockNo);
    List<LotNoLog> findByiItemAndProcessesAndLotNoNull(Item iItem, Processes processes);
    List<LotNoLog> findByProduction(Production production);


    @Query (value = "WITH recursive rc_1_1 as( "
            + "SELECT * "
            + "FROM lot_no_log "
            + "WHERE f_stock_no = -1 "
            + "and lot_no = :lotNo "
            + "UNION "
            + "SELECT "
            + "a.* "
            + "FROM lot_no_log a "
            + "INNER JOIN rc_1_1 b "
            + "ON b.lot_seq = a.p_lot_seq1 || b.lot_seq = a.p_lot_seq2 "
            + ") "
            + "select * from rc_1_1 where remark <> 'MERGE' || remark is null", nativeQuery = true)
    List<LotNoLog> findByTracking(@Param("lotNo") String lotNo);

    @Query (value =   "WITH recursive rc_1 as ( "
                    + "SELECT "
                    + " * "
                    + "FROM lot_no_log "
                    + "WHERE lot_seq = (select max(lot_seq) from lot_no_log where lot_no = :lotNo) "
                    + "UNION "
                    + "SELECT "
                    + "a.* "
                    + "FROM lot_no_log a "
                    + "INNER JOIN rc_1 b "
                    + "ON a.lot_seq = b.p_lot_seq1 "
                    + "), "
                    + "rc_2 as( "
                    + "SELECT "
                    + " * "
                    + "FROM rc_1 "
                    + "WHERE p_lot_seq2 != 0 "
                    + "UNION "
                    + "SELECT "
                    + "a.* "
                    + "FROM lot_no_log a "
                    + "            INNER JOIN rc_2 b "
                    + "            ON a.lot_seq = b.p_lot_seq2 || a.lot_seq = b.p_lot_seq1 "
                    + ") "
                    + "select distinct "
                    + "* "
                    + "from ( "
                    + "       select "
                    + "* "
                    + "from rc_1 "
                    + "union "
                    + "select "
                    + "* "
                    + "from rc_2) as a "
                    + "where a.remark != 'MERGE' || a.remark is null || a.lot_seq = (select max(lot_seq) from lot_no_log where lot_no = :lotNo) "
                    + " order by a.lot_seq desc ", nativeQuery = true)
    List<LotNoLog> findByreverseTracking(@Param("lotNo") String lotNo);
    List<LotNoLog> findByoItemNullAndProcesses_procCd(String procCd);
    LotNoLog findByLotSeq(Long lotSeq);
}

