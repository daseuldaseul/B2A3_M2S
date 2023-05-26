package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.CompanyDto;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.ItemFormDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ItemRepository itemRepository;



    @GetMapping("/item")
    public String item(Model model){

        List<Item> itemList = itemRepository.findAll();
        List<ItemDto> itemDtoList = ItemDto.of(itemList);
        for(ItemDto items : itemDtoList){
            items.setItemGbNm(CodeServiceImpl.getCodeNm("ITEM_GB", items.getItemGb()));
            items.setItemTypeNm(CodeServiceImpl.getCodeNm("ITEM_TYPE", items.getItemType()));
            items.setItemUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", items.getItemUnit()));
        }
        model.addAttribute("codeList1", CodeServiceImpl.getCodeList("ITEM_GB"));
        model.addAttribute("codeList2", CodeServiceImpl.getCodeList("ITEM_TYPE"));
        model.addAttribute("codeList3", CodeServiceImpl.getCodeList("UNIT_TYPE"));
        model.addAttribute("itemList", itemDtoList);
        return "itemPage";
    }

    @PostMapping("/item")
    public String itemWrite(ItemFormDto itemFormDto, @RequestParam String companyNm, Model model){
        CompanyDto companyDto = CompanyDto.of(companyRepository.findByCompanyNm(companyNm));
        itemFormDto.setCompany(companyDto);
        itemFormDto.setRegDate(LocalDate.now());
        Item item = itemFormDto.createItem();

        itemRepository.save(item);

        return "redirect:/item";
    }


    /**
     * 품목구분/타입/단위 검색 추가해야함.
     * **/
    @GetMapping("/item/search")
    public String itemSearch(@RequestParam String itemCd,
                             @RequestParam String itemNm,
                             @RequestParam String companyCd,
                             @RequestParam String companyNm,
                             @RequestParam String itemGb,
                             @RequestParam String itemType,
                             @RequestParam String itemUnit,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                             Model model) {

        List<Item> itemList = itemService.searchItem(itemCd, itemNm, companyCd, companyNm, itemGb, itemType, itemUnit, startDate, endDate);
        List<ItemDto> itemDtoList = ItemDto.of(itemList);
        for(ItemDto items : itemDtoList){
            items.setItemGbNm(CodeServiceImpl.getCodeNm("ITEM_GB", items.getItemGb()));
            items.setItemTypeNm(CodeServiceImpl.getCodeNm("ITEM_TYPE", items.getItemType()));
            items.setItemUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", items.getItemUnit()));
        }

        model.addAttribute("codeList1", CodeServiceImpl.getCodeList("ITEM_GB"));
        model.addAttribute("codeList2", CodeServiceImpl.getCodeList("ITEM_TYPE"));
        model.addAttribute("itemList", itemDtoList);

        return "itemPage";
    }



}
