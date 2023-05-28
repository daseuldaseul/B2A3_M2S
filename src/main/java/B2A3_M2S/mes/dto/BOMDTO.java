package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.LotNoLog;
import jdk.jfr.DataAmount;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BOMDTO {
    public static ModelMapper modelMapper = new ModelMapper();

    private Long bomNo;         // BOM 번호

    private String remark;      // 비고

    private Double consumption;   //소모량

    private Character useYn;    //사용유무

    private ItemDto productItem;     //제품 코드

    private ItemDto materialItem;    //자재 코드

    private LocalDate regDate;
    private LocalDate endDate;
    private LocalDate modDate;

    //추가함
    private int depth;
    private Double needQty;
    private Long standard;      // 기준
    private String productCd;
    private String materialCd;

    public BOMDTO(Long bomNo, String remark, Double consumption, Character useYn, LocalDate regDate, LocalDate modDate, Integer depth, Double needQty, Long standard, String productCd, String materialCd) {
        this.bomNo = bomNo;
        this.remark = remark;
        this.consumption = consumption;
        this.useYn = useYn;
        this.regDate = regDate;
        this.modDate = modDate;
        this.depth = depth;
        this.needQty = needQty;
        this.standard = standard;
        this.productCd = productCd;
        this.materialCd = materialCd;
    }

    public static List<BOMDTO> of(List<BOM> list) {
        return modelMapper.map(list, new TypeToken<List<BOMDTO>>() {
        }.getType());
    }

    public static BOMDTO of(BOM bom) {
        return modelMapper.map(bom, BOMDTO.class);
    }

    public BOM createBOM() {
        // EquipDto -> Equipment 변환
        return modelMapper.map(this, BOM.class);
    }
}