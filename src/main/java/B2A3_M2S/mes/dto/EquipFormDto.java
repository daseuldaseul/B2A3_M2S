package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Equipment;
import B2A3_M2S.mes.entity.Processes;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;

/** Getter, Setter를 안 넣으면 매핑이 안돼요 **/
@Getter
@Setter
public class EquipFormDto {
    private String equipCd;     //설비 코드

    private String equipNm;     //설비명

    private Long readyTime;  //준비 시간

    private Long capacity;   //생산 능력

    private String workTime;    //생산 소요시간

    private String remark;      //비고


    private Processes processes;      //공정 코드

    private String readyUnit;   //준비시간 단위

    private String capaUnit;    //생산 단위

    private String equipState;       //설비 상태

    private List<EquipFormDto> equipDtos = new ArrayList<>();

    public static ModelMapper modelMapper = new ModelMapper();

    public Equipment createEquipment() {
        // EquipDto -> Equipment 변환
        return modelMapper.map(this, Equipment.class);
    }

    public static EquipFormDto of(Equipment equipment) {
        // Equipment -> EquptDto 변환
        return modelMapper.map(equipment, EquipFormDto.class);
    }

    public static List<EquipFormDto> of(List<Equipment> equipmentList) {
        // EquipmentList -> EquptDtoList 변환
        return modelMapper.map(equipmentList, new TypeToken<List<EquipFormDto>>() {}.getType());
    }
}
