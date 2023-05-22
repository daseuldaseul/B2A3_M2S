package B2A3_M2S.mes.repository;


import B2A3_M2S.mes.dto.ProcessesDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LoginTest {

    List<ProcessesDto> pList;

    @Test
    public void test() {
        //계량 -> 세척 -> 추출-> 가열 -> 충진 -> 검사 ->식힘 -> 포장
        pList = new ArrayList<>();
    }
}
