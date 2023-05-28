package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<Production, String> {
}
