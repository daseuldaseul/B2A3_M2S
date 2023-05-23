package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.UnitConversion;
import B2A3_M2S.mes.repository.UnitConversionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitConversionServiceImpl implements UnitConversionService {
    @Autowired
    private UnitConversionRepository repository;

    public double unitConversion(double data, String fromUnit, String toUnit) {
        UnitConversion conversion = repository.findByFromUnitAndToUnit(fromUnit, toUnit);
        double result = data * conversion.getConversionFactor();
        return result;
    }
}