package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class StockServiceTests {

    @Autowired
    StockService service;

    @Autowired
    ItemRepository repository;

    @Test
    public void addStockTest(){
        Item item = repository.findByItemNm("양배추");

        service.addMaterials(item , 100L);
        service.addMaterials(item , 30L);
    }

    @Test
    public void addStockTestProduct(){
        Item item = repository.findByItemNm("양배추즙(BOX)");
        String lotNo = "001";
        service.addMaterials(item , 100L);
        service.addMaterials(item , 30L);
    }

    @Test
    public void releaseStockTest(){
        Item item =repository.findByItemNm("양배추");

       service.releaseItem(item, 105L);


    }
}
