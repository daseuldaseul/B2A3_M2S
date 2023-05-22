package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity(name = "equipment")
@Table(name = "equipment")
@AllArgsConstructor
@NoArgsConstructor
public class Equipment extends BaseTimeEntity{

    @Id
    private String equipCd;     //설비 코드
    private String equipNm;     //설비명
    private Long readyTime;  //준비 시간
    private Long capacity;   //생산 능력
    private String workTime;    //생산 소요시간
    private String remark;      //비고
    private String seatingCapacity;     // 수용능력
    private Character fixYn;            // 고정여부

    //외래키
    @ManyToOne
    @JoinColumn(name = "proc_cd")
    private Processes processes;      //공정 코드

    //공통코드
    private String readyUnit;   //준비시간 단위
    private String capaUnit;    //생산 단위
    private String equipState;       //설비 상태
    private String work_time_unit;      // 작업시간단위

}
