package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.repository.ItemRepository;

import B2A3_M2S.mes.repository.RoutingRepository;
import B2A3_M2S.mes.service.RoutingService;
import com.querydsl.core.types.dsl.CaseBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;

@SpringBootTest
public class StickDueTests {

//    @Autowired
//    ItemRepository itemRepository;
//
//    @Autowired
//    RoutingRepository routingRepository;
//
//    @Autowired
//
//
//    @Test
//    public void  dueDayTest() {
//
//        LocalDateTime dueTime = LocalDateTime.now();
//
//
//
//        ItemDto item = ItemDto.of(itemRepository.findByItemNm("석류젤리스틱(BOX)"));
//
//        System.out.println("수주 시각 : " + dueTime);
//        System.out.println("수주 품목 : " + item.getItemNm());
//        System.out.println("수주 수량 : " + 1000 +"box");
//
//
//        if (dueTime.toLocalTime().isBefore(LocalTime.of(15, 0))) {
//            dueTime = dueTime.plusDays(3).withHour(10).withMinute(0).withSecond(0);
//        } else {
//            dueTime = dueTime.plusDays(4).withHour(10).withMinute(0).withSecond(0);
//        }
//
//        System.out.println("발주 입고 시각 : " + dueTime);
//
//
//
//       // System.out.println(routingRepository.findByItem_ItemCd(item.getItemCd()));
//
//
//
//    }



}
