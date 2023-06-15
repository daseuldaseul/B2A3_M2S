package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.ProcessStockDTO;
import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.dto.ProcessesFormDto;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.repository.ProcessesRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ProcessStockService;
import B2A3_M2S.mes.service.ProcessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("processStock")
public class ProcessStockController {
    @Autowired
    ProcessesService processesService;
    @Autowired
    ProcessStockService processStockService;

    @GetMapping("/list")
    public String view(Model model, String procNm, String procCd) {
        List<ProcessesDto> processList = processesService.getProcessList(procNm, procCd);
        for (ProcessesDto process : processList) {
            process.setProcStateNm(CodeServiceImpl.getCodeNm("PROCESS_STATE", process.getProcState()));
            process.setProcUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getProcUnit()));
            process.setReadyUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getReadyUnit()));
            process.setWorkTimeUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getWorkTimeUnit()));
        }

        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PROCESS_STATE"));
        model.addAttribute("processList", processList);
        return "processStock";
    }

    @GetMapping("/detail")
    public @ResponseBody ResponseEntity<List<ProcessStockDTO>> detail(String location) throws Exception {
        return ResponseEntity.ok(processStockService.getProcStockList(location));
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam String procNm, @RequestParam String procCd,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate, String procState) {

        List<ProcessesDto> processList = ProcessesDto.of(processesService.searchProcesses(procNm, procCd, procState, startDate, endDate));

        for (ProcessesDto process : processList) {
            process.setProcStateNm(CodeServiceImpl.getCodeNm("PROCESS_STATE", process.getProcState()));
            process.setProcUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getProcUnit()));
            process.setReadyUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getReadyUnit()));
            process.setWorkTimeUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", process.getWorkTimeUnit()));
        }
        model.addAttribute("codeList", CodeServiceImpl.getCodeList("PROCESS_STATE"));
        model.addAttribute("processList", processList);

        return "processStock";
    }

    @PostMapping("/process")
    public String processWrite(ProcessesFormDto processesFormDto, Model model) {
        processesService.saveProc(processesFormDto);
        return "redirect:/processStock";
    }
}
