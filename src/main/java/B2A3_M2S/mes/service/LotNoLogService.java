package B2A3_M2S.mes.service;

import B2A3_M2S.mes.entity.LotNoLog;
import B2A3_M2S.mes.entity.QLotNoLog;
import B2A3_M2S.mes.repository.LotNoLogRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
            builder.and(qLotNoLog.regDate.between(startDate, endDate));
        }

        return (List<LotNoLog>) lotNoLogRepository.findAll(builder);

    }
}
