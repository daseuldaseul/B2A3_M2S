package B2A3_M2S.mes.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonCode extends BaseTimeEntity {
    @EmbeddedId
    private CommonCodePK codeId;
    private String displayValue;    //코드명
    private int codeSort;   //정렬 순서
    private Character useYn;    // 사용유무
<<<<<<< HEAD
    private String testestestestestremark;  //비고
=======
>>>>>>> aef55ec85cf1655bc9e852f9d7ea47a1f794b72b

    /*
    public String getCodeGroup() {
        return this.codeId.getCodeGroup();
    }
*/

    public String getCd() {
        return this.codeId.getCd();
    }
}
