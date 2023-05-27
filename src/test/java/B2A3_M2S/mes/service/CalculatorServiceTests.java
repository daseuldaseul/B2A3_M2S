package B2A3_M2S.mes.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculatorServiceTests {
    @Autowired
    private CalculatorService service;

    @Test
    public void test() {
        System.out.println("asd");
        service.getDeliveryDate("asdasd", 123L);
    }

}
