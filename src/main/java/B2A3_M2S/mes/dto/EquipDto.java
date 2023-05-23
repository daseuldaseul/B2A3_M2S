package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Equipment;
import B2A3_M2S.mes.entity.Processes;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.description.method.MethodDescription;
import org.hibernate.loader.plan.build.internal.spaces.EntityQuerySpaceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

/** Getter, Setter를 안 넣으면 매핑이 안돼요 **/
@Getter
@Setter
public class EquipDto {
    private String equipCd;     //설비 코드
    private String equipNm;     //설비명
    private Long readyTime;  //준비 시간
    private Long capacity;   //생산 능력
    private String workTime;    //생산 소요시간
    private String remark;      //비고
    private ProcessesDto processes;      //공정 코드
    private String readyUnit;   //준비시간 단위
    private String readyUnitNm;   //준비시간 단위
    private String capaUnit;    //생산 단위
    private String capaUnitNm;    //생산 단위
    private String equipState;       //설비 상태
    private String equipStateNm;       //설비 상태
    private String seatingCapacity;     // 수용능력
    private Character fixYn;            // 고정여부
    private String workTimeUnit;      // 작업시간단위
    private String workTimeUnitNm;      // 작업시간단위

    private List<EquipDto> equipDtos = new ArrayList<>();
    public static ModelMapper modelMapper = new ModelMapper();

    public Equipment createEquipment() {
        // EquipDto -> Equipment 변환
        return modelMapper.map(this, Equipment.class);
    }

    public static EquipDto of(Equipment equipment) {
        // Equipment -> EquptDto 변환
        return modelMapper.map(equipment, EquipDto.class);
    }

    public static List<EquipDto> of(List<Equipment> equipmentList) {
        // EquipmentList -> EquptDtoList 변환
        return modelMapper.map(equipmentList, new TypeToken<List<EquipDto>>() {}.getType());
    }
}
