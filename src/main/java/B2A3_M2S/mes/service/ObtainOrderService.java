package B2A3_M2S.mes.service;

import B2A3_M2S.mes.dto.CompanyDto;
import B2A3_M2S.mes.dto.ItemDto;
import B2A3_M2S.mes.dto.ObtainOrderFormDto;
import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.repository.CompanyRepository;
import B2A3_M2S.mes.repository.ItemRepository;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import B2A3_M2S.mes.util.NumPrefix;
import B2A3_M2S.mes.util.NumberingService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import B2A3_M2S.mes.entity.Item;
import B2A3_M2S.mes.entity.ObtainOrder;
import B2A3_M2S.mes.entity.QObtainOrder;
import B2A3_M2S.mes.repository.ObtainOrderRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ObtainOrderService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ObtainOrderRepository obtainOrderRepository;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public ObtainOrderFormDto writeObtainOrder(ObtainOrderFormDto obtainOrderFormDto, String companyNm, String itemNm) {
        obtainOrderFormDto.setCompany(CompanyDto.of(companyRepository.findByCompanyNm(companyNm)));
        obtainOrderFormDto.setItem(ItemDto.of(itemRepository.findByItemNm(itemNm)));

        NumberingService<ObtainOrder> service = new NumberingService<>(entityManager, ObtainOrder.class);
        String ocd = service.getNumbering("orderCd", NumPrefix.OBTAIN_ORDER);
        obtainOrderFormDto.setOrderCd(ocd);
//        double min = 3060 + 6.1 * (double) obtainOrderFormDto.getQty();
//        min = Math.ceil(min);
        LocalDateTime orderDate = LocalDateTime.now();
//
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 0));
        LocalDateTime dueTime = orderDate;
        obtainOrderFormDto.setOrderDate(orderDate);


        if (orderDate.compareTo(dateTime) <= 0) {
            dueTime = dueTime.plusDays(2);
            dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10,0));
        } else {
            dueTime = dueTime.plusDays(3);
            dueTime = LocalDateTime.of(dueTime.toLocalDate(), LocalTime.of(10,0));
        }
        long n = obtainOrderFormDto.getQty();
        dueTime = test((int)n,dueTime);

        obtainOrderFormDto.setOrderState("ORDER01");
        obtainOrderFormDto.setOrderUnit("UNIT01");
        obtainOrderFormDto.setDueDate(dueTime);
        return obtainOrderFormDto;
    }
    @Transactional
    public List<ObtainOrder> searchObtainOrder(String companyCd, String companyNm, LocalDateTime startDateTime, LocalDateTime endDateTime,
                                               String orderState, String itemCd, String itemNm, String orderCd) {
        QObtainOrder qObtainOrder = QObtainOrder.obtainOrder;
        BooleanBuilder builder = new BooleanBuilder();

        if (companyCd != null) {
            builder.and(qObtainOrder.company.companyCd.contains(companyCd));
        }

        if (companyNm != null) {
            builder.and(qObtainOrder.company.companyNm.contains(companyNm));
        }

        if (startDateTime != null && endDateTime != null) {
            builder.and(qObtainOrder.orderDate.between(startDateTime, endDateTime));
        }

        if (!orderState.equals("미선택")) {
            builder.and(qObtainOrder.orderState.eq(orderState));
        }

        if (itemCd != null) {
            builder.and(qObtainOrder.item.itemCd.contains(itemCd));
        }

        if (itemNm != null) {
            builder.and(qObtainOrder.item.itemNm.contains(itemNm));
        }

        if (orderCd != null) {
            builder.and(qObtainOrder.orderCd.contains(orderCd));
        }

        return (List<ObtainOrder>) obtainOrderRepository.findAll(builder);

    }

    @Transactional
    public LocalDateTime test(int n, LocalDateTime time) {

        while (n > 0) {
            if(n >= 800) {
                time = testCalculate(800, time);
            }else{
                time = testCalculate(n, time);
            }
            n -= 800;
        }
        return time;
    }
    @Transactional
    public LocalDateTime testCalculate(int n, LocalDateTime startTime){
        int material;				//흑마늘 필요량
        int water;					//정제수 필요량


        material = 250 * n;		//g
        int kg = material / 10000;	//10kg = 10000g 나누기
        if(material % 10000 != 0) {
            kg += 1;
        }
        kg *= 10;			//(kg) kg만큼 주문

        System.out.println("사용해야하는 흑마늘 : " + kg + "kg");	// 사용해야하는 흑마늘 = (kg)kg
        water = 10200 * kg;		//ml	흑마늘 1kg당 정제수 10200ml 필요
        System.out.println("필요한 정제수  : " + water + "ml");
        // 원자재 부족 시 자동 발주 시스템 만들기 ^^

        //생산 시작 시간
        LocalDateTime dueTime = startTime;
        System.out.println("생산 시작 시간 : \t\t" + dueTime);
        dueTime = dueTime.plusMinutes(50);		// 원료 계량 : 용량 무제한, 시간 고정
        System.out.println("원료 계량 후 시간 : \t\t" + dueTime);
        double min = Math.ceil(20 + (0.06 *kg));//전처리에 걸리는 시간 : 용량 최대 1ton
        //1ton 이상인 경우는 다시생각해야함..
//			System.out.println("전처리 걸리는 시간 : " + min);
        dueTime = dueTime.plusMinutes((int)min);

        System.out.println("전처리 후 시간 : \t\t" + dueTime);

        //흑마늘 추출
        min = Math.ceil(60 + (5.76 * kg)); //추출에 걸리는 시간
//			System.out.println(min);
        dueTime = dueTime.plusMinutes((int)min);	//추출 후 시간
        System.out.println("추출 후 시간 : \t\t" + dueTime);

        dueTime = dueTime.plusMinutes(1440);	//혼합 및 살균 : 최대 2000L, 1440분 고정

        System.out.println("혼합 및 살균 후 시간 : \t" + dueTime);

        min = Math.ceil(20 + (4.12 * kg));
//			System.out.println(min);

        dueTime = dueTime.plusMinutes((int)min);	//충진 후 시간

        System.out.println("충진 후 시간 : \t\t" + dueTime);

        min = Math.ceil(10 + (1.44 * kg));		//검사에 걸리는 시간

        dueTime = dueTime.plusMinutes((int)min);	//검사 후 시간

        System.out.println("검사 후 시간 : \t\t" + dueTime);

        dueTime = dueTime.plusMinutes(1440);	//식힘 후 시간

        System.out.println("식힘 후 시간 : \t\t" + dueTime);

        min = Math.ceil(20 + (1.2 * kg));

        dueTime = dueTime.plusMinutes((int)min);	//포장 후 시간

        System.out.println("포장 후 시간 : \t\t" + dueTime);



        return dueTime;


    }
}
