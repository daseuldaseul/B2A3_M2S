package B2A3_M2S.mes.Service;

import B2A3_M2S.mes.Repository.EquipRepository;
import B2A3_M2S.mes.entity.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EquipService {
    @Autowired
    EquipRepository equipRepository;

    /**
      설비명으로 검색
      **/
    @Transactional
    public List<Equipment> searchEquipNm(@RequestParam String equipNm, Model model) {
        List<Equipment> equipmentList = equipRepository.findByEquipNmContaining(equipNm);

        model.addAttribute("equipmentList", equipmentList);
        return equipmentList;
    }

    @Transactional
    public List<Equipment> searchEquipState(@RequestParam String equipState, Model model) {
        List<Equipment> equipmentList = equipRepository.findByEquipState(equipState);

        model.addAttribute("equipmentList", equipmentList);

        return equipmentList;
    }
}
