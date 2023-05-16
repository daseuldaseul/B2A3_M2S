package B2A3_M2S.mes.service;

import B2A3_M2S.mes.repository.EquipRepository;
import B2A3_M2S.mes.repository.specification.EquipSpecification;
import B2A3_M2S.mes.entity.Equipment;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static B2A3_M2S.mes.entity.QEquipment.equipment;

@Service
public class EquipService {
    @Autowired
    EquipRepository equipRepository;

    /**
      설비명으로 검색
      **/
    // 1. specification을 사용.
    public List<Equipment> searchEquipList(String equipNm, String equipState) {
        List<Equipment> equipmentList = equipRepository.findAll(EquipSpecification.searchEquip(equipNm, equipState));

        return equipmentList;
    }

    // 2. querydsl을 사용
    public List<Equipment> findEquipList(String equipNm, String equipState) {
        BooleanBuilder builder = new BooleanBuilder();

        if(StringUtils.hasText(equipNm)) {
            builder.and(equipment.equipNm.like("%" + equipNm + "%"));
        }
        if(!equipState.equals("미선택")) {
            builder.and(equipment.equipState.eq(equipState));
        }
        
        // BooleanBuilder가 Predicate를 구현함. querydsl 자체애 내장된 findAll(Predicate predicate) 메서드 사용
        return (List<Equipment>) equipRepository.findAll(builder);
    }

}
