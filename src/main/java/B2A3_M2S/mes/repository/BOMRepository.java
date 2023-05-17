package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.BOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BOMRepository extends JpaRepository<BOM, Long> {

    List<BOM> findAll();
}
