package B2A3_M2S.mes.util.service;

import B2A3_M2S.mes.util.enums.NumPrefix;
import B2A3_M2S.mes.util.repository.NumberingRepository;
import lombok.Setter;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
public class NumberingService<T> implements NumberingRepository<T> {
    private EntityManager em;
    private Class<T> entityClass;

    public NumberingService(EntityManager entityManager, Class<T> entityClass) {
        this.em = entityManager;
        this.entityClass = entityClass;
    }

    @Override
    public String getNumbering(String varName, NumPrefix prefix) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String numbering = prefix.getTitle()
                + LocalDate.now(ZoneId.of("Asia/Seoul")).format(formatter);
        System.out.println("여기 numbering: " + numbering);
        String className = entityClass.getSimpleName();
        String qurey = "SELECT MAX(m." + varName + ") FROM " + className + " m WHERE m." + varName + " LIKE '%" + numbering + "%'";

        List<String> list = em.createQuery(qurey, String.class).getResultList();
        System.out.println("list 사이즈" + list.size());
        if (list.size() == 0 || list.get(0) == null) {
            numbering += "00001";
        } else {
            String temp = list.get(0);
            String suffix = temp.split(numbering)[1];
            String tempStr = String.valueOf(Integer.parseInt(suffix) + 1);
            while (true) {
                if (tempStr.length() >= 5)
                    break;
                tempStr = "0" + tempStr;
            }
            numbering += tempStr;
        }
        return numbering;
    }
}
