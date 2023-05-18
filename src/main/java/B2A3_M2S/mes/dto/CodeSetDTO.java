package B2A3_M2S.mes.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CodeSetDTO {
    private Map<String, CommonCodeDTO> codeGroup;
    private Map<String, List<CommonCodeDTO>> code;

    public CodeSetDTO() {
        this.codeGroup = new HashMap<String, CommonCodeDTO>();
        this.code = new HashMap<String, List<CommonCodeDTO>>();
    }
}
