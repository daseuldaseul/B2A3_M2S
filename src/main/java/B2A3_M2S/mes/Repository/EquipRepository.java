package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Equipment;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public interface EquipRepository extends JpaRepository<Equipment, String>, QuerydslPredicateExecutor<Equipment> /* JpaSpecificationExecutor<Equipment> */ {
    List<Equipment> findAll();

    // 검색 시 specification 사용할 때 사용
    List<Equipment> findAll(@Nullable Specification<Equipment> equip);

    Equipment findByEquipCd(String equipCd);

    List<Equipment> findByEquipNmContaining(String EquipNm);

    Optional<Equipment> findById(String equipCd);
}
