package B2A3_M2S.mes.util;

import javax.persistence.EntityManager;

public interface NumberingRepository<T> {
    String getNumbering(String varName, NumPrefix prefix);
}

//public interface NumberingRepository<T> {
//    String findByNumbering(String varName, String value);
//}
