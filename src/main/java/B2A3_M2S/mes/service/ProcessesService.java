package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ProcessesDto;
import B2A3_M2S.mes.entity.Processes;
import B2A3_M2S.mes.entity.QProcesses;
import B2A3_M2S.mes.repository.ProcessesRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessesService {

    @Autowired
    ProcessesRepository processesRepository;

    @Transactional
    public List<Processes> searchProcesses(String procNm, String procCd, String procState, LocalDate startDate, LocalDate endDate) {
        QProcesses qProcesses = QProcesses.processes;
        BooleanBuilder builder = new BooleanBuilder();

        if (procNm != null) {
            builder.and(qProcesses.procNm.contains(procNm));
        }

        if (procCd != null) {
            builder.and(qProcesses.procCd.contains(procCd));
        }
        if (!procState.equals("none")){
            builder.and(qProcesses.procState.eq(procState));
        }
        if (startDate != null && endDate != null) {
            builder.and(qProcesses.regDate.between(startDate, endDate));
        }
        return (List<Processes>) processesRepository.findAll(builder);
    }



}
