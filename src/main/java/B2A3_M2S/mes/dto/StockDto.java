package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.entity.Stock;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class StockDto {

    private Long stockNo;   //재고 번호

    private String lotNo;

    private String location;    //위치

    private Long qty;   //수량

    private String remark;  //비고

    private Long planQty;
    //외래키
    private ItemDto item;

    private LocalDate regDate;

    private LocalDate modDate;


    public static ModelMapper modelMapper = new ModelMapper();
    public static StockDto of(Stock stock) {return modelMapper.map(stock, StockDto.class);}

    public static List<StockDto> of(List<Stock> stockList) {
        return modelMapper.map(stockList, new TypeToken<List<StockDto>>() {}.getType());
    }

    // 추가



}
