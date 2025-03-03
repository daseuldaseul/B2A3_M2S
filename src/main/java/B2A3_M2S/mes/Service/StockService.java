package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.entity.WarehouseLog;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.StockRepository;
import B2A3_M2S.mes.repository.WarehouseLogRepository;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import B2A3_M2S.mes.util.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    @Autowired
    WarehouseLogRepository warehouseLogRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UtilService utilService;
    @PersistenceContext
    EntityManager entityManager;

    public List<Stock> getStockList() {

        List<Stock> stockList = new ArrayList<>();

        stockList = stockRepository.findByQtyIsNot(0L);


        return stockList;

    }







/**
 * 자재를 재고 테이블에 저장하고 입출고 테이블에 기록.
 *
 * @param item  아이템 엔티티
 * @param qty 수량
 *
 * */
    public WarehouseLogDTO addMaterials(Item item, Long qty ){
        NumberingService<WarehouseLog> service = new NumberingService<>(entityManager, WarehouseLog.class);
        String ocd = service.getNumbering("inoutNo", NumPrefix.RECEIVING);

        String lotNo = utilService.getLotNo(NumPrefix.RECEIVING);
        Stock stock = Stock.builder()
                .lotNo(lotNo)
                .item(item)
                .qty(qty)
                .build();

        WarehouseLog warehouseLog = WarehouseLog.builder()
                .inoutNo(ocd)//채번으로 변경 예정
                .item(item)
                .lotNo(lotNo)
                .logGb("RECEIVING")
                .qty(qty)
                .build();

        stockRepository.save(stock);
        warehouseLogRepository.save(warehouseLog);

        return WarehouseLogDTO.of(warehouseLog);
    }

    public WarehouseLogDTO addProducts(Item item ,String lotNo, Long qty ){
        NumberingService<WarehouseLog> service = new NumberingService<>(entityManager, WarehouseLog.class);
        String ocd = service.getNumbering("inoutNo", NumPrefix.RECEIVING);

        Stock stock = Stock.builder()
                .lotNo(lotNo)
                .item(item)
                .qty(qty)
                .build();

        WarehouseLog warehouseLog = WarehouseLog.builder()
                .inoutNo(ocd)
                .item(item)
                .lotNo(lotNo)
                .logGb("RECEIVING")
                .qty(qty)
                .build();

        stockRepository.save(stock);
        warehouseLogRepository.save(warehouseLog);
        return WarehouseLogDTO.of(warehouseLog);

    }
    /**
     * 공정 및 출하 진행 시 itemEntity 와 qty을 입력 받아
     * 재고 테이블에서 qty만큼 뺀 후
     * 입출고 테이블에 출고 기록.
     *
     *
     * @param item  아이템 엔티티
     * @param qty 수량
     *
     * */
    public List<ProcessStockDTO> releaseItem(Item item, Long qty){
        System.out.println("아이템: " + item);
        List<Stock> stockList = stockRepository.findByItem(item);
        List<ProcessStockDTO> pDtoList = new ArrayList<>();

         for(Stock stock : stockList){
             if(stock.getQty() <= qty){
                 NumberingService<WarehouseLog> service = new NumberingService<>(entityManager, WarehouseLog.class);
                 String ocd = service.getNumbering("inoutNo", NumPrefix.RELEASE);

                 qty -= stock.getQty();
                 System.out.println(stock.getQty());
                 System.out.println(qty);

                 WarehouseLog warehouseLog = WarehouseLog.builder()
                         .inoutNo(ocd)
                         .item(item)
                         .lotNo(stock.getLotNo())
                         .logGb("RELEASE")
                         .qty(stock.getQty())
                         .build();

                 WarehouseLogDTO warehouseLogDTO = WarehouseLogDTO.of(warehouseLog);
                 warehouseLogRepository.save(warehouseLog);
                 pDtoList.add(utilService.saveInput(warehouseLogDTO));
                 stock.setQty(0L);
                 stockRepository.save(stock);

                 if(qty == 0) break;

             }else {
                 NumberingService<WarehouseLog> service = new NumberingService<>(entityManager, WarehouseLog.class);
                 String ocd = service.getNumbering("inoutNo", NumPrefix.RELEASE);
                 Long stockQty = stock.getQty() - qty;

                 WarehouseLog warehouseLog = WarehouseLog.builder()
                         .inoutNo(ocd)
                         .item(item)
                         .lotNo(stock.getLotNo())
                         .logGb("RELEASE")
                         .qty(qty)
                         .build();


                 WarehouseLogDTO warehouseLogDTO = WarehouseLogDTO.of(warehouseLog);
                 warehouseLogRepository.save(warehouseLog);
                 pDtoList.add(utilService.saveInput(warehouseLogDTO));
                 stock.setQty(stockQty);
                 stockRepository.save(stock);

                 break;
             }

         }

         return pDtoList;
    }

}
