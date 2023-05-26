package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.CompanyDto;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.dto.ProcessesFormDto;
import B2A3_M2S.mes.entity.Company;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ProcessesService;
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
import java.util.List;

@Controller
public class ProcessesController {


    @Autowired
    ProcessesRepository processesRepository;

    @Autowired
    ProcessesService processesService;

    @GetMapping("/process")
    public String view(Model model){


        List<ProcessesDto> processList =  ProcessesDto.of(processesRepository.findAll());
        for(ProcessesDto process : processList){
            process.setProcStateNm(CodeServiceImpl.getCodeNm("PROCESS_STATE", process.getProcState()));
            process.setProcUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getProcUnit()));
            process.setReadyUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getReadyUnit()));
            process.setWorkTimeUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getWorkTimeUnit()));
        }

        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PROCESS_STATE"));
        model.addAttribute("processList", processList);
        return "processPage";
    }

    @GetMapping("/process/search")
    public String search(Model model, @RequestParam String procNm, @RequestParam String procCd,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, String procState){





        List<ProcessesDto> processList =  ProcessesDto.of(processesService.searchProcesses(procNm, procCd, procState, startDate, endDate));
        for(ProcessesDto process : processList){
            process.setProcStateNm(CodeServiceImpl.getCodeNm("PROCESS_STATE", process.getProcState()));
            process.setProcUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getProcUnit()));
            process.setReadyUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getReadyUnit()));
            process.setWorkTimeUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getWorkTimeUnit()));
        }
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PROCESS_STATE"));
        model.addAttribute("processList", processList);

        return "processPage";
    }

    @PostMapping("/process")
    public String processWrite(ProcessesFormDto processesFormDto, Model model){
        Processes processes = new Processes();
        processes = processesFormDto.createProcesses();

        processesRepository.save(processes);

        return "redirect:/process";
    }

}
