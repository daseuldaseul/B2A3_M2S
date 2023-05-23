package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Equipment;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.service.CodeServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ItemDto {
    private String itemCd;        //품목 코드

    private String itemNm;      //품목명

    private Long stock;         //적정 재고

    private Long standard;      //규격

    private String remark;      //비고

    private Character useYn;    //사용유무

    private String itemGb;      //품목 구분

    private String itemGbNm;      //품목 구분 코드값 받아올 변수

    private String itemTypeNm;    //품목 타입 코드값 받아올 변수

    private String itemUnitNm;       //재고 단위 코드값 받아올 변수

    private String itemType;    //품목 타입
    private String itemUnit;       //재고 단위
    private String sUnit;   //규격 단위

    private CompanyDto company;   // 업체코드

    private LocalDate regDate;

    private LocalDate modDate;

    
    // 코드값
    private String sUnitValue;
    private String itemGbValue;
    private String itemTypeValue;
    private String itemUnitValue;

    public static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemDto of(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    public static List<ItemDto> of(List<Item> itemList) {
        return modelMapper.map(itemList, new TypeToken<List<ItemDto>>() {
        }.getType());
    }

    public void setCodeValue() {
        if (this.getItemGb() != null)
            setItemGbValue(CodeServiceImpl.getCodeNm("ITEM_GB", this.getItemGb()));
        if (this.getItemType() != null)
            setItemTypeValue(CodeServiceImpl.getCodeNm("ITEM_TYPE", this.getItemType()));
        if (this.getSUnit() != null)
            setSUnitValue(CodeServiceImpl.getCodeNm("UNIT_TYPE", this.getSUnit()));
        if (this.getItemUnit() != null)
            setItemUnitValue(CodeServiceImpl.getCodeNm("UNIT_TYPE", this.getItemUnit()));
    }
}
