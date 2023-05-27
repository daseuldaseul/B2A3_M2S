package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.repository.BOMRepository;
import B2A3_M2S.mes.service.BOMService;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.ItemService;
import com.google.gson.Gson;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLOutput;
import java.util.List;

@Controller
@RequestMapping("/bom")
public class BOMController {

    @Autowired
    BOMService service;

    @Autowired
    ItemService itemService;
    private Object predicate;




//    @GetMapping("/test")
//    public String test(Model model){
//        List<BOMDTO> BOM = service.selectAllBOM();
//        model.addAttribute( "BOM" ,  BOM) ;
//        System.out.println(BOM);
//
//        return "BOM";
//    }

    @GetMapping("/list")
    /*public String test1(Model model
                    , @RequestParam("product")String product
                    , @RequestParam("material")String material
                    , @RequestParam("startregDate")String startregDate
                    , @RequestParam("endregDate")String endregDate){
        List<BOMDTO> BOM = service.selectAllBOM(product, material , endregDate , startregDate);*/
    public String test1(Model model, BOMDTO dto){
        List<BOMDTO> BOM = service.selectAllBOM(dto);

        for(BOMDTO bDto : BOM) {
            bDto.getMaterialItem().setItemUnitValue(CodeServiceImpl.getCodeNm("UNIT_TYPE" , bDto.getMaterialItem().getItemUnit()));
            bDto.getProductItem().setItemUnitValue(CodeServiceImpl.getCodeNm("UNIT_TYPE" , bDto.getProductItem().getItemUnit()));
        }

        model.addAttribute( "BOM" ,  BOM) ;
//        service.findNeedQtyBypItem("P_002", 1200);
//        service.findNeedQtyBypItem("P_002", 1200);




        return "BOM";
    }


}
