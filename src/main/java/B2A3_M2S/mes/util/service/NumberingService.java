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

/*    public NumberingService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }*/

    @Override
    public String getNumbering(String varName, NumPrefix prefix) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        String numbering = prefix.getTitle()
                + LocalDate.now(ZoneId.of("Asia/Seoul")).format(formatter);

        String className = entityClass.getSimpleName();
        String qurey = "SELECT MAX(m." + varName + ") FROM " + className + " m WHERE m." + varName + " LIKE '%" + numbering + "%'";

        List<String> list = em.createQuery(qurey, String.class).getResultList();

        if (list.size() == 0 || list.get(0) == null) {
            numbering += "00001";
        } else {
            String temp = list.get(0);
            String suffix = temp.substring(temp.lastIndexOf(numbering) + 1, temp.length());
            String tempStr = String.valueOf(Integer.parseInt(suffix) + 1);

            while (true) {
                if (tempStr.length() >= 5)
                    break;
                tempStr = "0" + tempStr;
            }
            numbering += tempStr;
        }
        return numbering;

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
//        String numbering = prefix.getTitle()
//                + "-"
//                + LocalDate.now(ZoneId.of("Asia/Seoul")).format(formatter)
//                + "-";
//
//        String className = entityClass.getSimpleName();
//        String qurey = "SELECT MAX(m." + varName + ") FROM " + className + " m WHERE m." + varName + " LIKE '%" + numbering + "%'";
//
//        List<String> list = em.createQuery(qurey, String.class).getResultList();
//
//        if (list.size() == 0 || list.get(0) == null) {
//            numbering += "00001";
//        } else {
//            String temp = list.get(0);
//            String suffix = temp.substring(temp.lastIndexOf("-") + 1, temp.length());
//            String tempStr = String.valueOf(Integer.parseInt(suffix) + 1);
//
//            while (true) {
//                if (tempStr.length() >= 5)
//                    break;
//                tempStr = "0" + tempStr;
//            }
//            numbering += tempStr;
//        }
//        return numbering;
    }

}


//
//public class NumberingRepositoryImpl<T> implements NumberingRepository<T> {
//    private final EntityManager em;
//    private final Class<T> entityClass;
//
//    public NumberingRepositoryImpl(EntityManager entityManager, Class<T> entityClass) {
//        this.em = entityManager;
//        this.entityClass = entityClass;
//    }
//
//    @Override
//    public String findByNumbering(String varName, String value) {
//        String className = entityClass.getSimpleName();
//        String qurey = "SELECT MAX(m." + varName + ") FROM "
//                + className + " m WHERE m." + varName + " LIKE '%" + value + "%'";
//
//        List<String> list = em.createQuery(
//                         qurey
//                        ,String.class)
//                .getResultList();
//        return list.get(0);
//    }
//}


//    public class NumberingRepositoryImpl<T> implements NumberingRepository<T> {
//    private final EntityManager em;
//    private final Class<T> entityClass;
//
//    public NumberingRepositoryImpl(EntityManager entityManager, Class<T> entityClass) {
//        this.em = entityManager;
//        this.entityClass = entityClass;
//    }
//
//
//    @Override
//    public String findByNumbering(String varName, String value) {
////        System.out.println("굿");
////        System.out.println("굿" + entityClass.getTypeName());
////        System.out.println(getClass().getGenericSuperclass());
////        System.out.println(getClass().getGenericSuperclass().getClass());
////        String className = null;
////        System.out.println("여길봐 " + this.getClass().getGenericInterfaces()[0]);
////        List<T> asd = new ArrayList<>();
////        NumberingRepository<T> test = null;
////        Type[] genericInterfaces = getClass().getGenericInterfaces();
////        System.out.println("type 사이즈 줘" + genericInterfaces.length);
////        boolean b = genericInterfaces[0] instanceof ParameterizedType;
////        System.out.println("type 사이즈 줘" +  b);
////        for (Type genericInterface : genericInterfaces) {
////            if (genericInterface instanceof ParameterizedType) {
////                System.out.println("들어옴1");
////                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
////                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
////                if (actualTypeArguments.length > 0) {
////                    System.out.println("들어옴2");
////                    Type actualType = actualTypeArguments[0];
////                    System.out.println("왜 안되노" + actualType.getClass());
////                    System.out.println("왜 안되노" + actualType.getTypeName());
////                    System.out.println("왜 안되노" + actualType.toString());
////                    System.out.println("왜 안되노" + actualType);
////                    if (actualType instanceof Class) {
////                        System.out.println("들어옴3");
////                        //entityClass = (Class<T>) actualType;
////                        // 이후에 필요한 작업 수행
////                    }
////                }
////            }
////        }
//
//        /*Arrays.stream(genericInterfaces).forEach(a -> System.out.println(a.getClass()));
//        if (genericInterfaces.length > 0 && genericInterfaces[0] instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
//            System.out.println(parameterizedType.getClass());
//            System.out.println(parameterizedType.getActualTypeArguments());
//            System.out.println(parameterizedType.getActualTypeArguments().getClass());
//            System.out.println(parameterizedType.getActualTypeArguments()[0]);
//            System.out.println(parameterizedType.getActualTypeArguments()[0].getTypeName());
//            System.out.println(parameterizedType.getActualTypeArguments()[0].getClass());
//            System.out.println(parameterizedType.getTypeName());
//
//            *//*entityClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];*//*
//            entityClass = (T) parameterizedType.getActualTypeArguments()[0];
//            className = entityClass.getClass().getSimpleName();
//            System.out.println("드디어 되겠지?" + className);
//        }*/
//
//
//        Class cls = entityClass.getClass();
//        String className = entityClass.getSimpleName();
//        System.out.println("뭐냐너" + className);
//        System.out.println("뭐냐너" + entityClass.getTypeName());
//        System.out.println("뭐냐너" + entityClass.getClass());
//        System.out.println("뭐냐너" + entityClass.getSimpleName());
//        System.out.println("뭐냐너" + entityClass.getName());
//        System.out.println("뭐냐너" + entityClass);
//
//        //String qurey = "SELECT MAX(m." + varName + ") FROM " + className + " m WHERE m." + varName + " LIKE :" + varName;
//        String qurey = "SELECT MAX(m." + varName + ") FROM " + className + " m WHERE m." + varName + " LIKE '%" + value +"%'" ;
//        //String qurey = "SELECT m FROM " + className + " m WHERE m." + varName + " LIKE " + "'0%'";
//        System.out.println("왜그래 대체 ㅠㅠ" + qurey);
//        List<String> list = em.createQuery(
//                qurey
//               ,String.class)
////                .setParameter("varName", value + "%")
//                .getResultList();
//
//        System.out.println(list.size());
//        list.stream().forEach(System.out::println);
//        return null;
//    }
//}
