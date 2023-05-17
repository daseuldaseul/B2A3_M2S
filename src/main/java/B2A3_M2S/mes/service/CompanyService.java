package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.QCompany;
import B2A3_M2S.mes.entity.QProcesses;
import B2A3_M2S.mes.repository.CompanyRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Transactional
    public List<Company> searchCompany(String companyCd, String companyNm, String companyGb, LocalDateTime startDate, LocalDateTime endDate) {
        QCompany qCompany = QCompany.company;
        BooleanBuilder builder = new BooleanBuilder();

        if (companyCd != null) {
            builder.and(qCompany.companyCd.contains(companyCd));
        }

        if (companyNm != null) {
            builder.and(qCompany.companyNm.contains(companyNm));
        }
        if (companyGb != "" && companyGb != null){
            builder.and(qCompany.companyGb.eq(companyGb));
        }
        if (startDate != null && endDate != null) {
            builder.and(qCompany.regdate.between(startDate, endDate));
        }
        return (List<Company>) companyRepository.findAll(builder);


    }

}
