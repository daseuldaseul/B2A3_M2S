package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Processes;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CompanyDto {

    public static ModelMapper modelMapper = new ModelMapper();

    private String companyCd;   //업체 코드

    private String companyNm;   //업체 이름

    private String phoneNumber;      //연락처

    private String address;     //주소

    private String remark;      //비고

    private String companyGb;   //업체 구분
    private String companyGbNm;

    private LocalDate regdate;



    public static List<CompanyDto> of(List<Company> companyList){
        return modelMapper.map(companyList, new TypeToken<List<CompanyDto>>(){}.getType());
    }

    public static CompanyDto of(Company company){
        return modelMapper.map(company, CompanyDto.class);
    }

}
