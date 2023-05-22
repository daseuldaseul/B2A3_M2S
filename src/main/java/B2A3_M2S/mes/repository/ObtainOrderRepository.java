package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.ObtainOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObtainOrderRepository extends JpaRepository<ObtainOrder, String> {

    List<ObtainOrder> findAll();

}
