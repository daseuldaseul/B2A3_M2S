package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ItemFormDto;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.service.ItemService;
import B2A3_M2S.mes.util.NumPrefix;
import B2A3_M2S.mes.util.NumberingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ItemRepository itemRepository;



    @GetMapping("/item")
    public String ex(Model model){
        itemService.ItemList(model);
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
}
