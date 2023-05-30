package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.WarehouseLog;
import B2A3_M2S.mes.repository.BOMRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.repository.UtilRepositoryImpl;
import B2A3_M2S.mes.util.service.UtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StockServiceTests {

    @Autowired
    BOMService bomService;

    @Autowired
    StockService service;

    @Autowired
    ItemRepository repository;

    @Autowired
    UtilService utilService;

    @Test
    public void addStockTest() {
        Item item = repository.findByItemNm("흑마늘");
        service.addMaterials(item, 100000L);
        service.addMaterials(item, 100000L);
        service.addMaterials(item, 100000L);
    }

    @Test
    public void addStockTestProduct() {
        Item item = repository.findByItemNm("양배추즙(BOX)");
        String lotNo = "001";
        service.addMaterials(item, 100L);
        service.addMaterials(item, 30L);
    }

    @Test
    public void releaseStockTest() {
        Item item = repository.findByItemNm("양배추");
        service.releaseItem(item, 105L);
    }

    @Test
    public void asdsadada() {
        BOMDTO bomdto = new BOMDTO();

        List<BOMDTO> BOM = bomService.selectAllBOM(bomdto);

        System.out.println(BOM);
    }

    @Test
    public void test2() {
        System.out.println("입고 " +
                "Lot No 입니다" + utilService.getLotNo(NumPrefix.RECEIVING));
        //utilService.saveInput(WarehouseLogDTO.of());
    }
}
