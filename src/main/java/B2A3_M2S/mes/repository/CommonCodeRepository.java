package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.entity.CommonCodePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommonCodeRepository extends JpaRepository<CommonCode, CommonCodePK> {
    /*    @Query("SELECT c FROM CommonCode c WHERE c.codeId.codeGroup = :codeGroup AND c.useYn = :useYn")
    List<CommonCode> getByCodeGroupAndUseYn(@Param("codeGroup") String codeGroup, @Param("useYn") String useYn);*/

    /*    @Query("SELECT c FROM CommonCode c WHERE c.codeId.codeGroup != :codeGroup AND c.useYn = :useYn")
    List<CommonCode> getByCodeGroupNotAndUseYn(@Param("codeGroup") String codeGroup, @Param("useYn") String useYn);*/

    @Query("SELECT c FROM CommonCode c " +
            "WHERE c.codeId.codeGroup = :codeGroup AND ((:useYn != 'Y' AND :useYn != 'N' AND 1 = 1) OR ((:useYn = 'Y' OR :useYn = 'N') AND c.useYn = :useYn)) order by c.codeSort asc")
    List<CommonCode> getByCodeGroupAndUseYn(@Param("codeGroup") String codeGroup, @Param("useYn") String useYn);

    @Query("SELECT c FROM CommonCode c " +
            "WHERE c.codeId.codeGroup != :codeGroup AND ((:useYn != 'Y' AND :useYn != 'N' AND 1 = 1) OR ((:useYn = 'Y' OR :useYn = 'N') AND c.useYn = :useYn)) order by c.codeSort asc")
    List<CommonCode> getByCodeGroupNotAndUseYn(@Param("codeGroup") String codeGroup, @Param("useYn") String useYn);
}
