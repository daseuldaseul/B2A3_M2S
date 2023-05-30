package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.dto.RoutingFormDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.QItem;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.repository.RoutingRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class RoutingService {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProcessesRepository processesRepository;
    @Autowired
    RoutingRepository routingRepository;

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
            builder.and(qItem.regDate.between(startDate, endDate));
        }

        builder.and(qItem.itemCd.contains("P"));

        return (List<Item>) itemRepository.findAll(builder);

    }

    @Transactional
    public void routingWrtie(Map<String, String> requestParams, String itemCd){
        for (long i = 1; i <= 8; i++) {
            String processCd = requestParams.get("processCd" + i);
            if(!processCd.equals("none")) {
                long capacity = Long.parseLong(requestParams.get("capacity" + i));
                double yield = Double.parseDouble(requestParams.get("yield" + i));
                String remark = requestParams.get("remark" + i);

                RoutingFormDto routingFormDto = new RoutingFormDto();

                routingFormDto.setProcesses(ProcessesDto.of(processesRepository.findByProcCd(processCd)));
                routingFormDto.setItem(ItemDto.of(itemRepository.findByItemCd(itemCd)));
                routingFormDto.setPOrder(i);
                routingFormDto.setCapacity(capacity);
                routingFormDto.setYield(yield);
                routingFormDto.setRemark(remark);
                routingFormDto.setUseYn('Y');

                Routing routing = routingFormDto.createRouting();
                routingRepository.save(routing);
            }
        }
    }
}
