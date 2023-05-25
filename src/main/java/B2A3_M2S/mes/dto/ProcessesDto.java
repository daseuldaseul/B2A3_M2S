package B2A3_M2S.mes.dto;


import B2A3_M2S.mes.entity.Processes;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProcessesDto {

    public static ModelMapper modelMapper = new ModelMapper();

    private String procCd;      //공정 코드
    private String procNm;      //공정 이름
    private Long readyTime;   //준비 시간
    private Long workTime;    //작업 시간
    private Long capacity;   //생산 능력
    private String remark;      //비고
    private Character useYn;    //사용유무
    private String procUnit;        //단위
    private String readyUnit;   //준비시간 단위
    private String procState;       //
    private LocalDate regDate;
    private LocalDate modDate;
    private String seatingCapacity;     // 수용능력
    private Character fixYn;            // 고정여부
    private String workTimeUnit;      // 작업시간단위

    private String procStateNm;
    private String readyUnitNm;   //준비시간 단위
    private String procUnitNm;        //단위
    private String workTimeUnitNm;      // 작업시간단위

    public static List<ProcessesDto> of(List<Processes> processesList) {
        return modelMapper.map(processesList, new TypeToken<List<ProcessesDto>>() {
        }.getType());
    }

    public static ProcessesDto of(Processes processes) {
        return modelMapper.map(processes, ProcessesDto.class);
    }

    // 공정 상태
    private ProcessResultDTO pDto;
    public ProcessResultDTO operation(int inputQty) {
        //  0 : 미사용
        //  1 : 사용중

        //  0 : 실패
        //  1 : 완료
        if(pDto.getState() == 1) {
            pDto.setResult(0);
            return this.pDto;
        }

        this.pDto.setState(1);
        return pDto;
    }
    public void endOperation() {
        pDto.setState(0);
    }



    public ProcessesDto() {
        pDto = new ProcessResultDTO();
    }

}
