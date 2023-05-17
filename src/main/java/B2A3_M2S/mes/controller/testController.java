package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.service.BOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class testController {

    @Autowired
    BOMService service;


    @GetMapping("/main")
    public String home(){

        return "main";
    }

    @GetMapping("/test")
    public String test(Model model){
        List<BOMDTO> BOM = service.selectAllBOM();
        model.addAttribute( "BOM" ,  BOM) ;

        return "BOM";
    }
}
