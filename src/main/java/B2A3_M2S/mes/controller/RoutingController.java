package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.RoutingDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.RoutingRepository;
import B2A3_M2S.mes.entity.Routing;
import B2A3_M2S.mes.service.RoutingService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class RoutingController {

    @Autowired
    RoutingRepository routingRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    RoutingService routingService;

    @GetMapping("/routing")
    public String routing(Model model) {
        List<Item> itemList = itemRepository.findByItemGb("완제품");
        List<ItemDto> itemDtoList = ItemDto.of(itemList);

        model.addAttribute("itemList", itemDtoList);
        return "routingPage";
    }

    @ResponseBody
    @GetMapping("/routing/detail")
    public String routingDetail(@RequestParam String itemNm, Model model) {

        Gson gson = new Gson();
        System.out.println("-------------------------------------------");
        Item item = itemRepository.findByItemNm(itemNm);
        List<Routing> routing = routingRepository.findByItem(item);
        List<RoutingDto> routingDtoList = RoutingDto.of(routing);


        String json = gson.toJson(routingDtoList);
        return json;
    }

    @GetMapping("/routing/search")
    public String routingSearch(@RequestParam String itemCd, @RequestParam String itemNm,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                Model model) {


        List<Item> itemList = routingService.searchItem(itemNm, itemCd, startDate, endDate);
        List<ItemDto> itemDtoList = ItemDto.of(itemList);


        model.addAttribute("itemList", itemDtoList);

        return "routingPage";
    }
}
