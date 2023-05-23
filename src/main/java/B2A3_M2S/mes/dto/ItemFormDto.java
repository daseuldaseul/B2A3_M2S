package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;


@Getter
@Setter
public class ItemFormDto {
    public static ModelMapper modelMapper = new ModelMapper();

    private String itemCd;        //품목 코드

    private String itemNm;      //품목명

    private Long stock;         //적정 재고

    private Long standard;      //규격

    private String remark;      //비고

    private Character useYn;    //사용유무

    private String itemGb;      //품목 구분

    private String itemType;    //품목 타입

    private String itemUnit;       //재고 단위

    private String sUnit;   //규격 단위

    private CompanyDto company;   // 업체코드

    private LocalDate regDate;
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
}
