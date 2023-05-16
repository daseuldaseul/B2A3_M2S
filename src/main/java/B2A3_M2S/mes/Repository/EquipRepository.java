package B2A3_M2S.mes.Repository;

import B2A3_M2S.mes.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipRepository extends JpaRepository<Equipment, String> {
    List<Equipment> findAll();

    Equipment findByEquipCd(String equipCd);

    // 어떤 글자를 포함하는 단어 찾기
    List<Equipment> findByEquipNmContaining(String equipNm);

    List<Equipment> findByEquipState(String equipState);
}
