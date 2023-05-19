package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
