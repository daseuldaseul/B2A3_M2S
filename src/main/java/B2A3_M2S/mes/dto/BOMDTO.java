package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BOMDTO {
    private Long bomNo;         // BOM 번호
    private Long consumption;   // 소모량
    private String remark;      // 비고
    private char useYn;    // 사용유무

    private Item pItem;  // 제품 코드
    private Item mItem; // 자재 코드
}