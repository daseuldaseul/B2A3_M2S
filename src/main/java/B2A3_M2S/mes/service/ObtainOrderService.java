package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.ObtainOrderFormDto;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ObtainOrderService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    ItemRepository itemRepository;

    public ObtainOrderFormDto writeObtainOrder(ObtainOrderFormDto obtainOrderFormDto, String companyNm, String itemNm){
        obtainOrderFormDto.setCompany(companyRepository.findByCompanyNm(companyNm));
        obtainOrderFormDto.setItem(itemRepository.findByItemNm(itemNm));
        obtainOrderFormDto.setOrderCd("code_" + itemNm + obtainOrderRepository.count());
        double min = 3060 + 6.1 * (double) obtainOrderFormDto.getQty();
        min = Math.ceil(min);
        LocalDateTime orderDate = LocalDateTime.now();

        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12,0));
        LocalDateTime dueTime = orderDate;
        obtainOrderFormDto.setOrderDate(orderDate);

        if(orderDate.compareTo(dateTime) <= 0){
            dueTime.plusDays(2);
        }else{
            dueTime.plusDays(3);
        }

        obtainOrderFormDto.setDueDate(dueTime.plusMinutes((int)min));
        return obtainOrderFormDto;
    }
}
