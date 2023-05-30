package B2A3_M2S.mes.controller;


import B2A3_M2S.mes.dto.*;
import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.EquipRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.service.ItemService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AutoCompleteController {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    ProcessesRepository processesRepository;

    @Autowired
    EquipRepository equipRepository;

    // 회사
    @GetMapping("/autoComplete")
    public String autoComplete(@RequestParam("text") String text) {
        Gson gson = new Gson();
        List<Company> companys = companyRepository.findByCompanyNmContaining(text);
        List<CompanyDto> companyList = CompanyDto.of(companys);
        String json = gson.toJson(companyList);
        return json;
    }

    // 품목(전체용)
    @GetMapping("/autoComplete2")
    public String autoComplete2(String text){
        Gson gson = new Gson();
        String itemList =  gson.toJson(itemService.getItemList(text));

        return itemList;
    }

    // 품목(완제품용)
    @GetMapping("/autoComplete3")
    public String autoComplete3(@RequestParam("text") String text) {

        Gson gson = new Gson();
        List<Item> items = itemRepository.findByItemNmContainingAndItemCdContaining(text, "P");
        List<ItemDto> itemList = ItemDto.of(items);

        String json = gson.toJson(itemList);
        return json;
    }

    // 공정

    @GetMapping("/autoComplete4")
    public String autoComplete4(@RequestParam String text){
        Gson gson = new Gson();

        List<Processes> processList = processesRepository.findByProcNmContaining(text);
        List<ProcessesDto> processesDtoList = ProcessesDto.of(processList);
        String json = gson.toJson(processesDtoList);
        return json;
    }

    // 설비
    @GetMapping("/autoComplete5")
    public String autoComplete5(@RequestParam("text") String text) {

        Gson gson = new Gson();
        List<Equipment> equip = equipRepository.findByEquipNmContaining(text);
        List<EquipDto> equipList = EquipDto.of(equip);
        String json = gson.toJson(equipList);

        return json;
    }

    //품목 - 자재용
    @GetMapping("/autoComplete6")
    public String autoComplete6(@RequestParam("text") String text) {

        Gson gson = new Gson();
        List<Item> item = itemRepository.findByItemNmContainingAndItemCdContaining(text, "M_");
        List<Item> items = new ArrayList<>();
        System.out.println(item.toString());
        for(Item list :item){
            if(list.getItemCd().length() == 5){
                items.add(list);
            }
        }

        List<ItemDto> itemList = ItemDto.of(items);

        String json = gson.toJson(itemList);
        return json;
    }


    //수주업체만
    @GetMapping("/autoComplete7")
    public String autoComplete7(@RequestParam("text") String text) {

        Gson gson = new Gson();
        List<Company> companys = companyRepository.findByCompanyNmContainingAndCompanyCdContaining(text, "OC");
        List<CompanyDto> companyList = CompanyDto.of(companys);

        String json = gson.toJson(companyList);
        return json;
    }

    // 발주업체만
    @GetMapping("/autoComplete8")
    public String autoComplete8(@RequestParam("text") String text) {

        Gson gson = new Gson();
        List<Company> companys = companyRepository.findByCompanyNmContainingAndCompanyCdContaining(text, "PC");
        List<CompanyDto> companyList = CompanyDto.of(companys);

        String json = gson.toJson(companyList);
        return json;
    }


}
