package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.UnitConversion;
import B2A3_M2S.mes.repository.UnitConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface UnitConversionService {


    /**
     *
     * @param "변환할 값"
     * @param "기존 단위"
     * @param "변환할 단위"
     * @return
     */
   double unitConversion(double data , String fromUnit,  String toUnit);
}
