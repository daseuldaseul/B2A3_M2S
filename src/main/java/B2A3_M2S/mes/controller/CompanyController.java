package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.CommonCodeDTO;
import B2A3_M2S.mes.dto.CompanyDto;
import B2A3_M2S.mes.dto.CompanyFormDto;
import B2A3_M2S.mes.dto.ProcessesFormDto;
import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.CompanyService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class CompanyController {


    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyService companyService;


    @GetMapping("/company")
    public String ex1(Model model) {
//        for (int i = 11; i < 99; i++) {
//            Company company = new Company();
//
//            company.setCompanyCd("A" + i);
//            company.setAddress("창원시 " + i);
//            company.setCompanyGb("발주처");
//            company.setCompanyNm("이름" + i);
//            company.setPhoneNumber("010-4324-12" + i);
//            company.setRemark("비고" + i);
//
//            System.out.println(company.toString());
//            companyRepository.save(company);
//        }

        List<CompanyDto> companyList = CompanyDto.of(companyRepository.findAll());

        for(CompanyDto companys : companyList){
            companys.setCompanyGbNm(CodeServiceImpl.getCodeNm("CUST_TYPE", companys.getCompanyGb()));
        }

        model.addAttribute("companyList", companyList);

        model.addAttribute("codeList", CodeServiceImpl.getCodeList("CUST_TYPE"));
        return "companyPage";
    }


    @GetMapping("/company/search")
    public String search(Model model, @RequestParam String companyCd, @RequestParam String companyNm,
                         @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                         @RequestParam String companyGb){


        List<CompanyDto> companyList = CompanyDto.of(companyService.searchCompany(companyCd, companyNm, companyGb, startDate, endDate));
        for(CompanyDto companys : companyList){
            companys.setCompanyGbNm(CodeServiceImpl.getCodeNm("CUST_TYPE", companys.getCompanyGb()));
        }
        model.addAttribute("companyList", companyList);
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("CUST_TYPE"));
        return "companyPage";
    }
    @GetMapping("/company/detail")
    @ResponseBody
    public String detail(@RequestParam String companyCd, Model model){

        Gson gson = new Gson();

        Company company = companyRepository.findByCompanyCd(companyCd);
        CompanyDto companyDto = CompanyDto.of(company);
        String json = gson.toJson(companyDto);
        System.out.println(json);
        return json;

    }

    @PostMapping("/company")
    public String companyWrite(CompanyFormDto companyFormDto, Model model){
        Company company = new Company();
        company = companyFormDto.createCompany();
        company.setRegDate(LocalDate.now());
        companyRepository.save(company);

       return "redirect:/company";

    }


    @GetMapping("/company/searchWord")
    @ResponseBody
    public String autoComplete(@RequestParam String text){
        Gson gson = new Gson();

        List<Company> companyList = companyRepository.findByCompanyNmContaining(text);
        List<CompanyDto> companyDtoList = CompanyDto.of(companyList);
        String json = gson.toJson(companyDtoList);
        System.out.println(json);
        return json;
    }
}
