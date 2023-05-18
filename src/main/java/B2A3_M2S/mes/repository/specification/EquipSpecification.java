package B2A3_M2S.mes.repository.specification;


import B2A3_M2S.mes.entity.Equipment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EquipSpecification {
    public static Specification<Equipment> searchEquip(String equipNm, String equipState) {
        return new Specification<Equipment>() {
            @Override
            public Predicate toPredicate(Root<Equipment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();

                if(StringUtils.hasText(equipNm)) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("equipNm"), "%" + equipNm + "%"));
                }
                if(!equipState.equals("미선택")) {
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("equipState"), equipState));
                }

                return predicate;
            }
        };
    }
}
