package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.service.NumberingService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@SpringBootTest
public class CommonCodeTests {
    @Autowired
    private B2A3_M2S.mes.repository.CommonCodeRepository repository;
    @Autowired
    private B2A3_M2S.mes.repository.ItemRepository itemRepository;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void selectListTest() {
        System.out.println("여기 테스트 시작함");

        NumberingService<CommonCode> service = new NumberingService<>(entityManager, CommonCode.class);
        System.out.println(service.getNumbering("codeId.cd", NumPrefix.OBTAIN_ORDER));

        //NumberingService<CommonCode> service = new NumberingService<>(CommonCode.class);

        //NumberingRepository<CommonCode> repository = new NumberingRepositoryImpl<>(entityManager ,CommonCode.class);
        //repository.findByNumbering("codeId.codeGroup", "test");
        //repository.findByNumbering("codeId.cd", "0");
        //repository.findByNumbering("test", "test");
    }

    @Autowired
    B2A3_M2S.mes.repository.StockRepository stockRepository;

    @Test
    public void stockTest() {

        ;

        Integer sum = stockRepository.getSumQtyByItemCd(itemRepository.findByItemNm("양배추").getItemCd());
        System.out.println("여기" + sum);
        List<Item> itemList = itemRepository.findAll();
        List<ItemDto> itemDtoList = ItemDto.of(itemList);

        //itemDtoList.get(0).setCurrentQty(stockList);
        //List<Object[]> resultList = stockRepository.getSumQtyByItemCd();

//        for (Object[] result : resultList) {
//            String itemCd = (String) result[0];  // 두 번째 요소: itemCd
//            Long sumQty = (Long) result[1];  // 첫 번째 요소: SUM(qty)
//
//        }
    }
}
