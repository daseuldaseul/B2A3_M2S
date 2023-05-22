package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.CommonCodeDTO;
import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.service.CodeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system")
@Log4j2
public class CommonCodeController {
    @Autowired
    CodeService service;

    @GetMapping("/list")
    public void list(Model model, CommonCodeDTO dto) {
        model.addAttribute("group", service.getGroupList(dto));
    }

    @GetMapping("/detail")
    public @ResponseBody ResponseEntity<List<CommonCodeDTO>> detail(String codeGroup, String useYn) throws Exception {
        return ResponseEntity.ok(service.getCodeList(codeGroup, useYn));
    }
}
