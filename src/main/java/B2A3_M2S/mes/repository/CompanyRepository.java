package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Processes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, String>, QuerydslPredicateExecutor<Company> {


    List<Company> findAll();

    Company findByCompanyCd(String code);

    Company findByCompanyNm(String name);

    List<Company> findByCompanyNmContaining(String name);
}
