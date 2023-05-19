package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.CompanyDto;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.dto.ProcessesFormDto;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

//        for(int i=1; i<99; i++){
//            Processes processes = new Processes();
//            processes.setProcCd("code" + i);
//            processes.setProcNm("공정" + i);
//            processes.setReadyTime(10L + i);
//            processes.setWorkTime(100L + i);
//            processes.setCapacity(50L+ i);
//            processes.setProcState("finish");
//            processes.setRegdate(LocalDateTime.now());
//            processes.setModdate(LocalDateTime.now());
//            processesRepository.save(processes);
//
//        }
        List<ProcessesDto> processList =  ProcessesDto.of(processesRepository.findAll());
        for(ProcessesDto process : processList){
            process.setProcStateNm(CodeServiceImpl.getCodeNm("PROCESS_STATE", process.getProcState()));
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
        List<ProcessesDto> processList =  ProcessesDto.of(processesRepository.findAll());
        for(ProcessesDto process : processList){
            process.setProcStateNm(CodeServiceImpl.getCodeNm("PROCESS_STATE", process.getProcState()));
        }
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PROCESS_STATE"));
        model.addAttribute("processList", processList);
        return "processPage";
    }

}
