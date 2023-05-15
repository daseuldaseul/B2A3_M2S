package B2A3_M2S.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity(name = "common_code")
@Table(name = "common_code")
public class CommonCode extends BaseTimeEntity{

    @Id
    private String cd;    //코드

    private String codeGroup;   //코드 그룹

    private String displayValue;    //코드명

    private Long codeSort;   //정렬 순서

    private Character useYn;    // 사용유무

    private String remark;  //비고


}
