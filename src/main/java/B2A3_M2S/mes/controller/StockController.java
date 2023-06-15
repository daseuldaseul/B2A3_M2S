package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.CommonCodeDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.StockDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ItemService;
import B2A3_M2S.mes.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/stock")
public class StockController {
    @Autowired
    StockService service;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;
    @RequestMapping("/list")
    public String stockList(Model model, @RequestParam(required = false) String itemNm, @RequestParam(required = false) String itemGb) {
        List<ItemDto> itemList = service.searchStockList(itemNm, itemGb);
        for(ItemDto items : itemList){
            items.setItemGbNm(CodeServiceImpl.getCodeNm("ITEM_GB", items.getItemGb()));
            items.setItemTypeNm(CodeServiceImpl.getCodeNm("ITEM_TYPE", items.getItemType()));
            items.setItemUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", items.getItemUnit()));
        }
        model.addAttribute("codeList1", CodeServiceImpl.getCodeList("ITEM_GB"));
        model.addAttribute("codeList2", CodeServiceImpl.getCodeList("ITEM_TYPE"));
        model.addAttribute("codeList3", CodeServiceImpl.getCodeList("UNIT_TYPE"));
        model.addAttribute("itemList", itemList);
        CodeServiceImpl.getCodeList("ITEM_TYPE").forEach(System.out::println);

        return "stock";
    }
    @GetMapping("/detail")
    public @ResponseBody ResponseEntity<List<StockDto>> detail(String itemCd) throws Exception {
        return ResponseEntity.ok(service.getStockList(itemCd));
    }
}
