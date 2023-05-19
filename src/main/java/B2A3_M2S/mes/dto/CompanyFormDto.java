package B2A3_M2S.mes.dto;

import B2A3_M2S.mes.entity.Company;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CompanyFormDto {

    public static ModelMapper modelMapper = new ModelMapper();

    private String companyCd;   //업체 코드

    private String companyNm;   //업체 이름

    private String phoneNumber;      //연락처

    private String address;     //주소

    private String remark;      //비고

    private String companyGb;   //업체 구분


    public Company createCompany(){
        return modelMapper.map(this, Company.class);
    }
}
