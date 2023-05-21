package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.entity.CommonCodePK;
import B2A3_M2S.mes.entity.QCommonCode;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

@SpringBootTest
public class CommonCodeTests {
    @Autowired
    private CommonCodeRepository repository;

    @Test
    public void selectTest() {
        CommonCodePK pk = new CommonCodePK();
        pk.setCd("CUST_TYPE");
        pk.setCodeGroup("0");
        System.out.println(repository.findById(pk));
    }

    @Test
    public void insertDummies() {
        CommonCodePK pk = new CommonCodePK();
        pk.setCodeGroup("0");
        pk.setCd("ITEM_GB");
        CommonCode commonCode = CommonCode.builder()
                .codeId(pk)
                .displayValue("품목구분")
                .codeSort(1)
                .useYn('Y')
                .remark("테스트로 넣음")
                .build();
        repository.save(commonCode);
    }

    @Test
    public void testQuery2() {
        QCommonCode qCommonCode = QCommonCode.commonCode;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exGroup = qCommonCode.codeId.codeGroup.contains(keyword);
        BooleanExpression exCode = qCommonCode.codeId.cd.contains(keyword);
        BooleanExpression exAll = exGroup.or(exCode);
        builder.and(exAll);
        builder.and(qCommonCode.codeId.codeGroup.eq("0"));


    }

/*    @Test
    public void selectListTest() {
        System.out.println( repository.getGroupList(""));
    }*/
}
