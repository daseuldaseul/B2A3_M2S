package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.LotNoLogDTO;
import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.repository.LotNoLogRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.LotNoLogService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LotNoLogController {
    @Autowired
    LotNoLogRepository lotNoLogRepository;
    @Autowired
    LotNoLogService lotNoLogService;

    @GetMapping("/lotNoLog")
    public String lotNoLog(Model model){
        List<LotNoLogDTO> lotNoLogList = LotNoLogDTO.of(lotNoLogRepository.findAll());
        //일단 전체 lotNo List 다 불러옴

        model.addAttribute("lotNoLogList", lotNoLogList);
        return "lotNoLogPage";
    }

    @ResponseBody
    @GetMapping("/lotNoLog/detail")
    public String lotNoLogDetail(@RequestParam String lotNo){
        Gson gson = new Gson();
        List<LotNoLogDTO> lotNoLogs = LotNoLogDTO.of(lotNoLogRepository.findAll());
        //lotNo으로 찾아오게 수정해야함 일단 전체 list 가져왔음

        String json = gson.toJson(lotNoLogs);
        return json;
    }

    @GetMapping("lotNoLog/search")
    public String searchLot(@RequestParam(required = false) String lotNo,
                            @RequestParam(required = false) String itemCd,
                            @RequestParam(required = false) String itemNm,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            Model model){

        List<LotNoLogDTO> lotNoLogList = LotNoLogDTO.of(lotNoLogService.searchLotNoLog(lotNo, itemCd, itemNm, startDate, endDate));

        model.addAttribute("lotNoLogList", lotNoLogList);
        return "lotNoLogPage";
    }

    @GetMapping("/lotNoLogReverse")
    public String lotNoLogReverse(Model model){
        List<LotNoLogDTO> lotNoLogList = LotNoLogDTO.of(lotNoLogRepository.findAll());
        //일단 전체 lotNo List 다 불러옴

        model.addAttribute("lotNoLogList", lotNoLogList);
//        model.addAttribute("codeList", CodeServiceImpl.getCodeList(""));
        //공통코드 안보임
        return "lotNoLogReversePage";
    }

    @ResponseBody
    @GetMapping("/lotNoLogReverse/detail")
    public String lotNoLogReverseDetail(@RequestParam String lotNo){
        Gson gson = new Gson();
        List<LotNoLogDTO> lotNoLogs = LotNoLogDTO.of(lotNoLogRepository.findAll());
        //lotNo으로 찾아오게 수정해야함 일단 전체 list 가져왔음

        String json = gson.toJson(lotNoLogs);
        return json;
    }

    @GetMapping("lotNoLogReverse/search")
    public String searchLotReverse(@RequestParam(required = false) String lotNo,
                            @RequestParam(required = false) String itemCd,
                            @RequestParam(required = false) String itemNm,
//                            @RequestParam(required = false) String state,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            Model model){

        List<LotNoLogDTO> lotNoLogList = LotNoLogDTO.of(lotNoLogService.searchLotNoLogReverse(lotNo, itemCd, itemNm, startDate, endDate));

        model.addAttribute("lotNoLogList", lotNoLogList);
        return "lotNoLogReversePage";
    }

}
