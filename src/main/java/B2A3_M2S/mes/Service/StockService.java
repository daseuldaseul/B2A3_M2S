package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.entity.WarehouseLog;
import B2A3_M2S.mes.repository.StockRepository;
import B2A3_M2S.mes.repository.WarehouseLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {


    @Autowired
    WarehouseLogRepository warehouseLogRepository;

    @Autowired
    StockRepository stockRepository;

/**
 * 자재를 재고 테이블에 저장하고 입출고 테이블에 기록.
 *
 * @param item  아이템 엔티티
 * @param logGb 로그 구분
 * @param qty 수량
 *
 * */
    public void addMaterials(Item item, String logGb, Long qty ){
        String lotNo = "로트로트";

        Stock stock = Stock.builder()
                //.lotNo(lotNo)
                .item(item)
                .qty(qty)
                .build();

        WarehouseLog warehouseLog = WarehouseLog.builder()
                .item(item)
                //.lotNo(lotNo)
                .logGb("입고")
                .qty(qty)
                .build();

        stockRepository.save(stock);

        warehouseLogRepository.save(warehouseLog);
    }
    public void addProducts(Item item ,String lotNo  ,String logGb, Long qty ){

        Stock stock = Stock.builder()
                .lotNo(lotNo)
                .item(item)
                .qty(qty)
                .build();

        WarehouseLog warehouseLog = WarehouseLog.builder()
                .item(item)
                .lotNo(lotNo)
                .logGb(logGb)
                .qty(qty)
                .build();

        stockRepository.save(stock);

        warehouseLogRepository.save(warehouseLog);

    }
    public List<Stock> releaseItem(Item item,Long qty ){

         List<Stock> stockList = stockRepository.findByItem_ItemCdOrderByRegDate(item.getItemCd());

         List<Stock> releaseList = new ArrayList<>();

         for(Stock stock : stockList){

             if(stock.getQty() <= qty){
                 qty -= stock.getQty();
                 releaseList.add(stock);

                 WarehouseLog warehouseLog = WarehouseLog.builder()
                         .item(item)
                         .lotNo(stock.getLotNo())
                         .logGb("출고")
                         .qty(stock.getQty())
                         .build();


                 warehouseLogRepository.save(warehouseLog);

                 stock.setQty(0L);
                 stockRepository.save(stock);

                 if(qty == 0) break;
                 //i 번쨰 stock qty 를 0 으로
                 //입출고 테이블에 출고 기록
                 //출고 리스트에 추가

             }else {
                 Long stockQty = stock.getQty() - qty;

                 WarehouseLog warehouseLog = WarehouseLog.builder()
                         .item(item)
                         .lotNo(stock.getLotNo())
                         .logGb("출고")
                         .qty(qty)
                         .build();


                 warehouseLogRepository.save(warehouseLog);


                 stock.setQty(stockQty);





                 releaseList.add(stock);


                 stockRepository.save(stock);

                 break;
             }

         }

         return  releaseList;
    }

}
