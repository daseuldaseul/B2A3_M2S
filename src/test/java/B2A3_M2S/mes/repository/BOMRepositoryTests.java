package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.service.BOMService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BOMRepositoryTests {

    @Autowired
    BOMRepository bomRepository;

    @Autowired
    BOMService service;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void test(){
        System.out.println(bomRepository.getClass().getName());

    }

    @Test
    public void testInsertDummies(){

//        Item material = new Item();
//        material.setItemCd("벌꿀");
//
//        Item item = new Item();
//        item.setItemCd("흑마늘즙");
//
//        BOM bom = BOM.builder()
//
//                .consumption(5L)
//                .materialCd(material)
//                .productCd(item)
//                    .build();
//
//        itemRepository.save(material);
//        itemRepository.save(item);
//        bomRepository.save(bom);


        List<BOMDTO> allBOMs  = service.selectAllBOM();

        for (BOMDTO bom : allBOMs) {
            System.out.println("BOM ID: " + bom.getBomNo());
            System.out.println("Consumption: " + bom.getConsumption());
            System.out.println("Material: " + bom.getMaterialCd());
            System.out.println("Product: " + bom.getProductCd());
            System.out.println("-------------------------");
        }

    }
}
