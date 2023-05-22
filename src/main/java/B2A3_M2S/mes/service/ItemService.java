package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;


    public List<ItemDto> getItemList(String text) {

        ModelMapper modelMapper = new ModelMapper();
        List<ItemDto> itemDtoList = new ArrayList<>();

        List<Item> itemList = repository.findAllByItemNmContains(text);

        for (Item item : itemList) {
            ItemDto itemDto = modelMapper.map(item, ItemDto.class);
            itemDtoList.add(itemDto);
        }
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


}
