package B2A3_M2S.mes.dto;


import B2A3_M2S.mes.entity.CommonCodePK;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonCodeDTO {
    private CommonCodePK codeId;
    private String displayValue;    //코드명
    private int codeSort;   //정렬 순서
    private Character useYn;    // 사용유무
    private String remark;  //비고
    private LocalDate regDate, modDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate, modDate, regDateEnd, modDateEnd;

    public String getCodeGroup() {
        return this.codeId.getCodeGroup();
    }

    public String getCd() {
        System.out.println("123: " + codeId);
        return this.codeId != null ? this.codeId.getCd() : "";
    }
}
