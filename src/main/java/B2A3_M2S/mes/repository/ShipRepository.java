package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ShipRepository extends JpaRepository<Ship, String>, QuerydslPredicateExecutor<Ship> {

    List<Ship> findAll();

    List<Ship> findByObtainOrder(ObtainOrder obtainOrder);
}
