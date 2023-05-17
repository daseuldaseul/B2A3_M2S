package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.service.CompanyService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        List<Company> companyList = companyRepository.findAll();

        model.addAttribute("companyList", companyList);

        return "companyPage";
    }


    @GetMapping("/company/search")
    public String search(Model model, @RequestParam String companyCd, @RequestParam String companyNm,
                         @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                         @RequestParam String companyGb){

        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null){
            startDateTime =  LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime =  LocalDateTime.of(endDate, LocalTime.MAX);
        }

        List<Company> companyList = companyService.searchCompany(companyCd, companyNm, companyGb, startDateTime, endDateTime);
        model.addAttribute("companyList", companyList);
        return "companyPage";
    }
    @GetMapping("/company/detail")
    @ResponseBody
    public String detail(@RequestParam String companyCd, Model model){

        Gson gson = new Gson();

        Company company = companyRepository.findByCompanyCd(companyCd);
        String json = gson.toJson(company);
        System.out.println(json);
        return json;

    }
}
