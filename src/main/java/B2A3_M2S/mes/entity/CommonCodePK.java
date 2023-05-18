package B2A3_M2S.mes.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CommonCodePK implements Serializable {
    private String codeGroup;   //코드 그룹
    private String cd;    //코드
}
