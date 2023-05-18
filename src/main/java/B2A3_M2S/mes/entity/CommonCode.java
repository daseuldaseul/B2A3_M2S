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
    private String remarkㄱsadfnsjkdafhsjklafhsjkdafhsjkldafhsjkldafhsklda;      // 수정
    /*
    public String getCodeGroup() {
        return this.codeId.getCodeGroup();
    }
*/

    public String getCd() {
        return this.codeId.getCd();
    }
}
