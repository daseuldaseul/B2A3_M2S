package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.CommonCodeDTO;
import B2A3_M2S.mes.entity.CommonCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UnitConvertTests {

    @Autowired
    UnitConversionService service;

    @Autowired
    CodeService code;

    @Test
    public void unit(){

//        System.out.println( service.UnitConversion(500 ,  CodeServiceImpl.getCodeNm("UNIT_TYPE" , "UNIT03")
//                , CodeServiceImpl.getCodeNm("UNIT_TYPE" , "UNIT04")  ));

        System.out.println(service.unitConversion(500 , "L", "ml"));


    }
}
