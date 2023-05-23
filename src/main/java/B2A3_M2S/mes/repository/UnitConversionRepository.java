package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.UnitConversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitConversionRepository extends JpaRepository<UnitConversion, Long> {

    UnitConversion findByFromUnitAndToUnit(String fromUnit, String toUnit);

}