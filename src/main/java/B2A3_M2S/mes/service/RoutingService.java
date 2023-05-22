package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.QItem;
import B2A3_M2S.mes.repository.ItemRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoutingService {

    @Autowired
    ItemRepository itemRepository;

    @Transactional
    public List<Item> searchItem(String itemCd, String itemNm, LocalDate startDate, LocalDate endDate) {


        QItem qItem = QItem.item;
        BooleanBuilder builder = new BooleanBuilder();

        if(itemCd != null) {
            builder.and(qItem.itemCd.contains(itemCd));
        }

        if(itemNm != null) {
            builder.and(qItem.itemNm.contains(itemNm));
        }

        if(startDate != null && endDate != null) {
            builder.and(qItem.regdate.between(startDate, endDate));
        }

        builder.and(qItem.itemCd.contains("P"));

        return (List<Item>) itemRepository.findAll(builder);

    }
}
