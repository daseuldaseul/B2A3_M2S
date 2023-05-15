package B2A3_M2S.mes.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "processes")
public class Processes extends BaseTimeEntity {

    @Id
    private String procCd;      //공정 코드

    private String procNm;      //공정 이름

    private String readyTime;   //준비 시간

    private String workTime;    //작업 시간

    private Integer capacity;   //생산 능력

    private String remark;      //비고

    private Character useYn;    //사용유무



    /**
     외래키 설정해야 함
     **/

    //공통 코드
    private String unit;        //단위
    private String readyUnit;   //준비시간 단위
    private String state;       //공정상태
    //공통 코드

}
