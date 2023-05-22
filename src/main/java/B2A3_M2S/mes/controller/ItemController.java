package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.ItemFormDto;
import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.service.ItemService;
import B2A3_M2S.mes.util.NumPrefix;
import B2A3_M2S.mes.util.NumberingService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        itemService.ItemList(model);

        List<Item> itemList = itemRepository.findAll();
        List<ItemDto> itemDtoList = ItemDto.of(itemList);

        model.addAttribute("itemList", itemDtoList);
        return "itemPage";
    }

    @PostMapping("/item")
    public String itemWrite(ItemFormDto itemFormDto, @RequestParam String companyNm, Model model){

        itemFormDto.setCompany(companyRepository.findByCompanyNm(companyNm));
        System.out.println(companyNm);
        System.out.println(companyRepository.findByCompanyNm(companyNm));
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
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                             Model model) {

        List<Item> itemList = itemService.searchItem(itemCd, itemNm, companyCd, companyNm, startDate, endDate);
        List<ItemDto> itemDtoList = ItemDto.of(itemList);

        model.addAttribute("itemList", itemDtoList);

        return "itemPage";
    }


    @GetMapping("/item/autoComplete")
    @ResponseBody
    public String itemAutoComplete(@RequestParam("text") String text) {
        Gson gson = new Gson();
        System.out.println(text);
        System.out.println("-------------------------------------------");
        List<Item> itemList = itemRepository.findByItemNmContaining(text);

        String json = gson.toJson(itemList);
        return json;
    }

    @GetMapping("/item/autoComplete2")
    @ResponseBody
    public String itemAutoComplete2(@RequestParam("text") String text) {
        Gson gson = new Gson();
        System.out.println(text);
        System.out.println("-------------------------------------------");
        List<Company> companyList = companyRepository.findByCompanyNmContaining(text);

        String json = gson.toJson(companyList);
        return json;
    }
}
