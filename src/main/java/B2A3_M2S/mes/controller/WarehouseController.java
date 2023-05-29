package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.dto.WarehouseLogDTO;
import B2A3_M2S.mes.service.CodeServiceImpl;
import B2A3_M2S.mes.service.WarehouseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/warehouseLog")
public class WarehouseController {

    @Autowired
    WarehouseLogService service;

    @RequestMapping("/list")
    public String getWarehouseLog(Model model){

        List<WarehouseLogDTO> warehouseLogDTOList = service.getWareHouseLog();

        for(WarehouseLogDTO warehouseLog : warehouseLogDTOList){
            warehouseLog.getItem().setItemUnitValue(CodeServiceImpl.getCodeNm("UNIT_TYPE" , warehouseLog.getItem().getItemUnit()));

        }
        model.addAttribute("warehouseLogList",warehouseLogDTOList );

        return "warehouseLog";
    }
}
