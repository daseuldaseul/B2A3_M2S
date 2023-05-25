package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockServiceTests {

    @Autowired
    StockService service;

    @Test
    public void addStockTest(){

        Item item = new Item();
        item.setItemCd("P_001");

        service.addProducts(item ,"001" , "입고" , 100L);
    }
}
