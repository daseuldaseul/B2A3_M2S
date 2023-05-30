package B2A3_M2S.mes.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestTest {

    @Autowired
    ShipService shipService;

    @Test
    public void test(){
        shipService.createShip("SO23053000001");

    }
}
