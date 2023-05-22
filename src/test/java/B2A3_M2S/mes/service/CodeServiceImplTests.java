package B2A3_M2S.mes.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeServiceImplTests {
    @Autowired
    private CodeServiceImpl service;
    @Test
    public void initTest() throws Exception {
        System.out.println("몇개? " + CodeServiceImpl.getCodeSetDTO().getCodeGroup().size());
    }

    @Test
    public void clearTest() throws Exception {
        //CommonCodeService.clear();
        System.out.println("몇개? " + CodeServiceImpl.getCodeSetDTO().getCodeGroup().size());
    }

    @Test
    public void getCodeGroupNmTest() {
        System.out.println("그룹명" + CodeServiceImpl.getCodeGroupNm("CUST_TYPE"));
    }

    @Test
    public void getCodeListTest() throws Exception {
        System.out.println("그룹명에 대한 코드" + CodeServiceImpl.getCodeList("CUST_TYPE").size());

    }
    @Test
    public void getCodeNmTest() throws Exception {
        System.out.println(CodeServiceImpl.getCodeNm("CUST_TYPE", "CUST01"));
    }

    @Test
    public void getGroupListTest() {
        System.out.println("123");
    }
}
