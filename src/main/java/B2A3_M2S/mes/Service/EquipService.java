package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.EquipFormDto;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.entity.QEquipment;
import B2A3_M2S.mes.repository.EquipRepository;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.repository.specification.EquipSpecification;
import B2A3_M2S.mes.entity.Equipment;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class EquipService {
    @Autowired
    EquipRepository equipRepository;

    @Autowired
    ProcessesRepository processesRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

        QEquipment qEquipment = QEquipment.equipment;

        if(StringUtils.hasText(equipNm)) {
            builder.and(qEquipment.equipNm.like("%" + equipNm + "%"));
        }
        if(!equipState.equals("미선택")) {
            builder.and(qEquipment.equipState.eq(equipState));
        }
        
        // BooleanBuilder가 Predicate를 구현함. querydsl 자체애 내장된 findAll(Predicate predicate) 메서드 사용
        return (List<Equipment>) equipRepository.findAll(builder);
    }

    public EquipFormDto writeEquip(EquipFormDto equipFormDto, String procNm) {
        equipFormDto.setProcesses(ProcessesDto.of(processesRepository.findByProcNm(procNm)));
        equipFormDto.setCapaUnit("UNIT01");

        return equipFormDto;
    }
}
