package B2A3_M2S.mes.service;


import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.repository.BOMRepository;
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

        ModelMapper modelMapper = new ModelMapper();

        List<BOM> bomList = repository.findAll(dto);
        List<BOMDTO> bomDTOList = new ArrayList<>();

        for (BOM bom : bomList) {
            BOMDTO bomDTO = modelMapper.map(bom, BOMDTO.class);
            bomDTOList.add(bomDTO);
        }

        return bomDTOList;
    }

}
