package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.entity.*;
import B2A3_M2S.mes.repository.ItemRepository;
import com.querydsl.core.BooleanBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;


    public List<ItemDto> getItemList(String text) {

        List<ItemDto> itemDtoList = ItemDto.of(repository.findAllByItemNmContains(text));

        return itemDtoList;
    }

    @Transactional
    public List<ItemDto> ItemList(Model model){
        List<Item> itemList = repository.findAll();
        List<ItemDto> itemDtoList = ItemDto.of(itemList);
        model.addAttribute("codeList1", CodeServiceImpl.getCodeList("ITEM_GB"));
        System.out.println(CodeServiceImpl.getCodeList("ITEM_GB") + "=============");
        model.addAttribute("codeList2", CodeServiceImpl.getCodeList("ITEM_TYPE"));
        model.addAttribute("codeList3", CodeServiceImpl.getCodeList("UNIT_TYPE"));
        model.addAttribute("itemList", itemDtoList);
        return itemDtoList;
    }

    @Transactional
    public List<Item> searchItem(String itemCd, String itemNm, String companyCd, String companyNm,
                                 String itemGb, String itemType, String itemUnit, LocalDate startDate, LocalDate endDate) {
        QItem qItem = QItem.item;
        BooleanBuilder builder = new BooleanBuilder();

        if (itemCd != null) {
            builder.and(qItem.itemCd.contains(itemCd));
        }

        if (itemNm != null) {
            builder.and(qItem.itemNm.contains(itemNm));
        }

        if (!itemGb.equals("미선택")) {
            builder.and(qItem.itemGb.eq(itemGb));
        }

        if (!itemType.equals("미선택")) {
            builder.and(qItem.itemType.eq(itemType));
        }

        if (!itemUnit.equals("미선택")) {
            builder.and(qItem.itemUnit.eq(itemUnit));
        }

        if (companyCd != null) {
            builder.and(qItem.company.companyCd.contains(companyCd));
        }

        if (companyNm != null) {
            builder.and(qItem.company.companyNm.contains(companyNm));
        }

        if (startDate != null && endDate != null) {
            builder.and(qItem.regDate.between(startDate, endDate));
        }

        return (List<Item>) repository.findAll(builder);

    }





}
