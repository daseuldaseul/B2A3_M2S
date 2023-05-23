package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.EquipFormDto;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.repository.EquipRepository;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.dto.EquipDto;
import B2A3_M2S.mes.entity.Equipment;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.EquipService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class EquipController {
    @Autowired
    EquipRepository equipRepository;

    @Autowired
    EquipService equipService;

    @Autowired
    ProcessesRepository processesRepository;


    @GetMapping(value = "/equipment")
    public String equipment(Model model) {
        List<Equipment> equipmentList = equipRepository.findAll();
        List<EquipDto> equipDtoList = EquipDto.of(equipmentList);

        for(EquipDto equipDto : equipDtoList){
            equipDto.setReadyUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", equipDto.getReadyUnit()));
            equipDto.setCapaUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", equipDto.getCapaUnit()));
            equipDto.setEquipStateNm(CodeServiceImpl.getCodeNm("EQUIP_STATE", equipDto.getEquipState()));
            equipDto.setWorkTimeUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", equipDto.getWorkTimeUnit()));
        }

        model.addAttribute("codeList1", CodeServiceImpl.getCodeList("EQUIP_STATE"));
        model.addAttribute("codeList2", CodeServiceImpl.getCodeList("UNIT_TYPE"));

        model.addAttribute("equipList", equipDtoList);
        return "equipPage";
    }


    @PostMapping("/equipment")
    public String equipmentRegister(EquipFormDto equipFormDto, String procNm, Model model) {
        EquipFormDto result = equipService.writeEquip(equipFormDto, procNm);
        Equipment equipment = new Equipment();
        equipment = result.createEquipment(); // equipFormDto를 equipment로 변환
        equipRepository.save(equipment);


        return "redirect:/equipment";
    }


    @GetMapping(value = "/equipment/search")
    public String searchKeyword(@RequestParam String equipNm, @RequestParam String equipState, Model model) {
        List<Equipment> equipmentList = equipService.findEquipList(equipNm, equipState);
        List<EquipDto> equipDtoList = EquipDto.of(equipmentList);
        // 조회 페이지의 attributeName과 동일한 값으로 넣어 조회

        for(EquipDto equipDto : equipDtoList){
            equipDto.setReadyUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", equipDto.getReadyUnit()));
            equipDto.setCapaUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", equipDto.getCapaUnit()));
            equipDto.setEquipStateNm(CodeServiceImpl.getCodeNm("EQUIP_STATE", equipDto.getEquipState()));
            equipDto.setWorkTimeUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", equipDto.getWorkTimeUnit()));
        }
        model.addAttribute("equipList", equipDtoList);
        model.addAttribute("codeList1", CodeServiceImpl.getCodeList("EQUIP_STATE"));
        model.addAttribute("codeList2", CodeServiceImpl.getCodeList("UNIT_TYPE"));


        return "equipPage";
    }

    @GetMapping("/equipment/autoComplete")
    @ResponseBody
    public String equipmentAutoComplete(@RequestParam("text") String text) {

        Gson gson = new Gson();
        System.out.println(text);
        System.out.println("-------------------------------------------");
        List<Equipment> equip = equipRepository.findByEquipNmContaining(text);
        System.out.println(equip);

        String json = gson.toJson(equip);
        return json;
    }
}
