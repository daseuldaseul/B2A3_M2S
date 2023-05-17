package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.entity.BOM;

import java.util.List;

public interface BOMService {

    List<BOMDTO>  selectAllBOM();
}
