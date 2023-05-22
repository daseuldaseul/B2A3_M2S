package B2A3_M2S.mes.service;


import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.entity.BOM;

import B2A3_M2S.mes.entity.QBOM;
import B2A3_M2S.mes.repository.BOMRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BOMServiceImpl implements BOMService {

    @Autowired
    private BOMRepository repository;


/*    public List<BOMDTO> selectAllBOM() {

        ModelMapper modelMapper =new ModelMapper();

        List<BOM> bomList = repository.findAll();
        List<BOMDTO> bomDTOList = new ArrayList<>();

        for (BOM bom : bomList) {
            BOMDTO bomDTO = modelMapper.map(bom, BOMDTO.class);
            bomDTOList.add(bomDTO);
        }

        return bomDTOList;
    }*/

    public List<BOMDTO> selectAllBOM(BOMDTO dto) {

        QBOM qbom = QBOM.bOM;
        BooleanBuilder predicate = new BooleanBuilder();
        ModelMapper modelMapper = new ModelMapper();
        List<BOMDTO> bomDTOList = new ArrayList<>();

        if (dto.getPItem() != null) {
            predicate.and(qbom.pItem.itemNm.contains(dto.getPItem().getItemNm()));
        }
        if (dto.getMItem() != null) {
            predicate.and(qbom.mItem.itemNm.contains(dto.getMItem().getItemNm()));
        }
        if (dto.getRegDate() != null && dto != null) {
            predicate.and(qbom.regDate.between(dto.getRegDate(), dto.getEndDate()));
        } else if (dto.getRegDate() != null) {
            predicate.and(qbom.regDate.after(dto.getRegDate()));
        }
        Iterable<BOM> bomList = repository.findAll(predicate);

        for (BOM bom : bomList) {
            BOMDTO bomDTO = modelMapper.map(bom, BOMDTO.class);
            bomDTOList.add(bomDTO);
        }

        return bomDTOList;


    }


}
