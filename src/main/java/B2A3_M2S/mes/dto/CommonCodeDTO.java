package B2A3_M2S.mes.dto;


import B2A3_M2S.mes.entity.CommonCodePK;
import lombok.*;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonCodeDTO {
    /*    private String cd;    //코드
        private String codeGroup;   //코드 그룹*/
    private CommonCodePK codeId;
    private String displayValue;    //코드명
    private int codeSort;   //정렬 순서
    private Character useYn;    // 사용유무
    private String remark;  //비고
    private LocalDateTime regDate, modDate;
    //private String code;

    public String getCodeGroup() {
        return this.codeId.getCodeGroup();
    }
    public String getCd() {
        return this.codeId.getCd();
    }
}
