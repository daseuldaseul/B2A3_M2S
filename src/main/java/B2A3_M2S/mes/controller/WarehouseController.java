package B2A3_M2S.mes.controller;

import B2A3_M2S.mes.service.WarehouseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WarehouseController {

    @Autowired
    WarehouseLogService service;

    @RequestMapping("warehouseLog/list")
    public String getWarehouseLog(Model model){

        model.addAttribute("warehouseLogList", service.getWareHouseLog());

        return "warehouseLog";
    }
}
