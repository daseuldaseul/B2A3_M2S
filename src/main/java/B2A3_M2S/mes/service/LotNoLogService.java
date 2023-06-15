package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.QLotNoLog;
import B2A3_M2S.mes.repository.LotNoLogRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class LotNoLogService {

    @Autowired
    LotNoLogRepository lotNoLogRepository;


    @Transactional
    public List<LotNoLog> searchLotNoLog(String lotNo, String itemCd, String itemNm, LocalDate startDate, LocalDate endDate) {
        QLotNoLog qLotNoLog = QLotNoLog.lotNoLog;
        BooleanBuilder builder = new BooleanBuilder();

        if (lotNo != null) {
            builder.and(qLotNoLog.lotNo.contains(lotNo));
        }
        if (itemCd != null) {
            builder.and(qLotNoLog.iItem.itemCd.contains(itemCd));
        }
        if (itemNm != null) {
            builder.and(qLotNoLog.iItem.itemNm.contains(itemNm));
        }

        if (startDate != null && endDate != null) {
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(LocalTime.MAX);
            builder.and(qLotNoLog.regDate.between(start, end));
        }

        return (List<LotNoLog>) lotNoLogRepository.findAll(builder);

    }

    @Transactional
    public List<LotNoLog> searchLotNoLogReverse(String lotNo, String itemCd, String itemNm,
//                                                String state,
                                                LocalDate startDate, LocalDate endDate) {
        QLotNoLog qLotNoLog = QLotNoLog.lotNoLog;
        BooleanBuilder builder = new BooleanBuilder();

        if (lotNo != null) {
            builder.and(qLotNoLog.lotNo.contains(lotNo));
        }
        if (itemCd != null) {
            builder.and(qLotNoLog.oItem.itemCd.contains(itemCd));
        }
        if (itemNm != null) {
            builder.and(qLotNoLog.oItem.itemNm.contains(itemNm));
        }
//        state 추가되면
//        if (!state.equals("미선택")) {
//            builder.and(qLotNoLog.iItem.itemNm.eq(state));
//        }
        if (startDate != null && endDate != null) {
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(LocalTime.MAX);
            builder.and(qLotNoLog.regDate.between(start, end));
        }

        return (List<LotNoLog>) lotNoLogRepository.findAll(builder);

    }
}
