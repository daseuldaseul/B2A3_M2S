package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BOMDTO {
    private Long bomNo;         // BOM 번호
    private String remark;      // 비고


    private Double consumption;   //소모량



    private Character useYn;    //사용유무



    private Item pItem;     //제품 코드


    private Item mItem;    //자재 코드

    private LocalDate regDate;
    private LocalDate endDate;

    private LocalDate modDate;
}