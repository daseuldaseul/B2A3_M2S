package B2A3_M2S.mes.util.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Repository
@Transactional
public class UtilRepositoryImpl implements UtilRepository {
    @Autowired
    EntityManager entityManager;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

    @Override
    public String createLotNo(String procCd) {
        String prefix = procCd
                + "-"
                + LocalDate.now(ZoneId.of("Asia/Seoul")).format(formatter)
                + "-";
        System.out.println("line 26: " + prefix);
        String result = entityManager.createQuery("SELECT MAX(c.lotNo) FROM LotNoLog c WHERE c.lotNo LIKE :prefix", String.class)
                .setParameter("prefix", prefix + "%")
                .getSingleResult();

        System.out.println("result: " + result);

        if (result == null || result.isEmpty()) {
            result = prefix + "00001";
        } else {
            String temp = result;
            String suffix = temp.substring(temp.lastIndexOf("-") + 1, temp.length());
            suffix = String.valueOf(Integer.parseInt(suffix) + 1);
            while (true) {
                if (suffix.length() >= 5)
                    break;
                suffix = "0" + suffix;
            }
            result = prefix + suffix;
        }
        return result;
    }
}
