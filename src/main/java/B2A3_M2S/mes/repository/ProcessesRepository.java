package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.QProcesses;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import java.time.LocalDateTime;
import java.util.List;


public interface ProcessesRepository extends JpaRepository<Processes, String>, QuerydslPredicateExecutor<Processes> {
    Processes findByProcCd(String procCd);

    List<Processes> findAll();

}
