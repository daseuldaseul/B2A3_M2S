package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ObtainOrderDto;
import B2A3_M2S.mes.dto.PurchaseOrderDto;
import B2A3_M2S.mes.dto.StockDto;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.Production;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.repository.ProductionRepository;
import B2A3_M2S.mes.repository.PurchaseOrderRepository;
import B2A3_M2S.mes.repository.StockRepository;
import B2A3_M2S.mes.service.CodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MainService {

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ProductionRepository productionRepository;

    public List<ObtainOrderDto> getObtainOrderDtoList() {

        List<ObtainOrder> obtainOrderList = obtainOrderRepository.findAll();
        List<ObtainOrderDto> obtainOrderDtoList = ObtainOrderDto.of(obtainOrderList);
        for (ObtainOrderDto obtainOrderDto : obtainOrderDtoList) {
            obtainOrderDto.setOrderUnitNm(CodeServiceImpl.getCodeNm("UNIT_TYPE", obtainOrderDto.getOrderUnit()));
            obtainOrderDto.setOrderStateNm(CodeServiceImpl.getCodeNm("OBTAIN_STATE", obtainOrderDto.getOrderState()));
            obtainOrderDto.setProgressPercent(progressPercent(obtainOrderDto.getOrderDate(), obtainOrderDto.getDueDate()));
        }


        return obtainOrderDtoList;
    }

    public List<PurchaseOrderDto> getPurchaseOrderDtoList() {

        List<PurchaseOrderDto> purchaseOrderList = PurchaseOrderDto.of(purchaseOrderRepository.findAll());

        for (PurchaseOrderDto orders : purchaseOrderList) {
            orders.setPurchaseStateNm(CodeServiceImpl.getCodeNm("PURCHASE_STATE", orders.getPurchaseState()));
            orders.setProgressPercent(progressPercent(orders.getOrderDate(), orders.getDueDate()));


        }

        return purchaseOrderList;

    }
/*
    public Map<String, Integer> getProcessesPercent() {
        Map<String, Integer> processes = new HashMap<>();

        processes.put("계량", 30);
        processes.put("전처리", 30);
        processes.put("추출", 30);
        processes.put("혼합", 33);
        processes.put("충진", 32);
        processes.put("포장", 40);


        return processes;
    }
*/

    public Map<String, Integer> getProcessesPercent() {
        Map<String, Integer> processes = new HashMap<>();
        Integer proc1percent = 0;
        Integer proc2percent = 0;
        Integer proc3percent = 0;
        Integer proc4percent = 0;
        Integer proc6percent = 0;
        Integer proc10percent = 0;
        try {
            Production production_1 = productionRepository.findByProcessesProcCdAndStatus("PROC01", "STATUS02").get(0);
            proc1percent = progressPercent(production_1.getStartDate(), production_1.getEndDate());
        } catch (Exception e) {
        }
        try {
            Production production_1 = productionRepository.findByProcessesProcCdAndStatus("PROC02", "STATUS02").get(0);
            proc2percent = progressPercent(production_1.getStartDate(), production_1.getEndDate());
        } catch (Exception e) {

        }
        try {
            Production production_1 = productionRepository.findByProcessesProcCdAndStatus("PROC03", "STATUS02").get(0);
            proc3percent = progressPercent(production_1.getStartDate(), production_1.getEndDate());
        } catch (Exception e) {

        }
        try {
            Production production_1 = productionRepository.findByProcessesProcCdAndStatus("PROC04", "STATUS02").get(0);
            proc4percent = progressPercent(production_1.getStartDate(), production_1.getEndDate());
        } catch (Exception e) {
        }
        try {
            Production production_1 = productionRepository.findByProcessesProcCdAndStatus("PROC06", "STATUS02").get(0);
            proc6percent = progressPercent(production_1.getStartDate(), production_1.getEndDate());
        } catch (Exception e) {

        }
        try {
            Production production_1 = productionRepository.findByProcessesProcCdAndStatus("PROC10", "STATUS02").get(0);
            proc10percent = progressPercent(production_1.getStartDate(), production_1.getEndDate());
        } catch (Exception e) {

        }

        if(proc10percent > 0) {
            proc1percent = 100;
            proc2percent = 100;
            proc3percent = 100;
            proc4percent = 100;
            proc6percent = 100;
        } else if( proc6percent > 0) {
            proc1percent = 100;
            proc2percent = 100;
            proc3percent = 100;
            proc4percent = 100;
        } else if( proc4percent > 0) {
            proc1percent = 100;
            proc2percent = 100;
            proc3percent = 100;
        } else if(proc3percent > 0) {
            proc1percent = 100;
            proc2percent = 100;
        } else if(proc2percent > 0) {
            proc1percent = 100;
        }


        processes.put("계량", proc1percent);
        processes.put("전처리", proc2percent);
        processes.put("추출", proc3percent);
        processes.put("혼합", proc4percent);
        processes.put("충진", proc6percent);
        processes.put("포장", proc10percent);

        return processes;
    }

    public List<Long> getDailyProduction() {
        List<Long> dailyProduction = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L));
        List<Stock> stockList = stockRepository.findByRegDateBetween(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
        List<StockDto> stockDtoList = StockDto.of(stockList);
        for (StockDto stockDto : stockDtoList) {
            if (stockDto.getItem().getItemCd().equals("P_001")) {
                dailyProduction.set(0, dailyProduction.get(0) + stockDto.getQty());
            } else if (stockDto.getItem().getItemCd().equals("P_002")) {
                dailyProduction.set(1, dailyProduction.get(1) + stockDto.getQty());
            } else if (stockDto.getItem().getItemCd().equals("P_003")) {
                dailyProduction.set(2, dailyProduction.get(2) + stockDto.getQty());
            } else if (stockDto.getItem().getItemCd().equals("P_004")) {
                dailyProduction.set(3, dailyProduction.get(3) + stockDto.getQty());
            }
        }

        return dailyProduction;
    }

    public List<Long> getMonthlyProduction() {
        List<Long> monthlyProduction = new ArrayList<>(Arrays.asList(0L, 0L, 0L, 0L));
        LocalDate currentDate = LocalDate.now();
        LocalDate startOfMonth = currentDate.withDayOfMonth(1); // 이번 달의 시작일
        LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth()); // 이번 달의 종료일

        List<Stock> stockList = stockRepository.findByRegDateBetween(startOfMonth.atStartOfDay(), endOfMonth.atTime(LocalTime.MAX));
        List<StockDto> stockDtoList = StockDto.of(stockList);
        for (StockDto stockDto : stockDtoList) {
            if (stockDto.getItem().getItemCd().equals("P_001")) {
                monthlyProduction.set(0, monthlyProduction.get(0) + stockDto.getQty());
            } else if (stockDto.getItem().getItemCd().equals("P_002")) {
                monthlyProduction.set(1, monthlyProduction.get(1) + stockDto.getQty());
            } else if (stockDto.getItem().getItemCd().equals("P_003")) {
                monthlyProduction.set(2, monthlyProduction.get(2) + stockDto.getQty());
            } else if (stockDto.getItem().getItemCd().equals("P_004")) {
                monthlyProduction.set(3, monthlyProduction.get(3) + stockDto.getQty());
            }
        }
        return monthlyProduction;
    }

    public int progressPercent(LocalDateTime startDate, LocalDateTime dueDate) {
        if (startDate == null || dueDate == null)
            return 0;

        System.out.println(startDate);
        System.out.println(dueDate);
        LocalDateTime currentDate = LocalDateTime.now();  // 현재 날짜 가져오기
        System.out.println("현재: " + currentDate);

        // 시작날짜부터 종료날짜까지의 총 일수 계산
        long totalDays = ChronoUnit.HOURS.between(startDate, dueDate);
        System.out.println("total days" + totalDays);
        // 시작날짜부터 현재 날짜까지의 경과 일수 계산

        long elapsedDays = ChronoUnit.HOURS.between(startDate, currentDate);
        System.out.println("백분율1: " + elapsedDays);

        // 진행된 백분율 계산
        double progressPercent = (double) elapsedDays / totalDays * 100;
        System.out.println("백분율2: " + progressPercent);
        int progressPercentInt = (int) Math.round(progressPercent);
        if (progressPercentInt > 100) {
            progressPercentInt = 100;
        }
        System.out.println("백분율: " + progressPercent);

        return progressPercentInt;
    }
}
