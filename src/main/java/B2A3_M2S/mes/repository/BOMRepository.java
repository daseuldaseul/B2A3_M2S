package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.dto.BOMDTO;
import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.CommonCode;
import B2A3_M2S.mes.entity.Item;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BOMRepository extends JpaRepository<BOM, Long> , QuerydslPredicateExecutor<BOM> {
/*    @Query("WITH recursive rc AS ("
            + "SELECT b, 1 as depth "
            + "FROM BOM b "
            + "WHERE b.pItem.itemCd = :item.itemCd "
            + "UNION ALL "
            + "SELECT b, r.depth + 1 as depth "
            + "FROM BOM b "
            + "INNER JOIN b.pItem.itemCd pItem "
            + "INNER JOIN rc r ON b.mItem.itemCd = r.b.pItem.itemCd) "
            + "SELECT r.bomNo, r.consumption, r.remark, r.useYn, r.depth, i.itemNm "
            + "FROM rc r "
            + "LEFT JOIN r.mItem.itemCd i")
    List<Object[]> findBypItem(Item item);*/


/*    @Query(value = "WITH recursive rc as (" +
            "SELECT " +
            "* " +
            ",1 as depth " +
            "FROM bom " +
            "WHERE product_cd = :itemCd " +
            "UNION " +
            "SELECT " +
            "b.* " +
            ",r.depth + 1 as depth " +
            "FROM bom b " +
            "INNER JOIN rc r " +
            "ON b.product_cd = r.material_cd " +
            ") " +
            "SELECT " +
            "a.* " +
            ",i.item_nm " +
            "FROM rc a " +
            "LEFT JOIN item i " +
            "ON a.material_cd = i.item_cd;",
            nativeQuery = true)
    List<BOM> findBypItem(@Param("itemCd") String itemCd);*/



    /*@Query(value = "WITH recursive rc as(" +
            " SELECT" +
            " std.bom_no " +
            ",std.product_cd " +
            ",std.material_cd " +
            ",1 as depth " +
            ",100 as standard " +
            ",(std.consumption * 100 / std.standard) as consumption " +
            ",std.remark" +
            ",std.use_yn" +
            ",std.reg_date" +
            ",std.mod_date" +
            "FROM bom std " +
            "WHERE std.product_cd = 'P_001' " +
            "UNION " +
            "SELECT " +
            " b.bom_no " +
            ",b.product_cd " +
            ",b.material_cd " +
            ",r.depth + 1 as depth " +
            ",r.consumption as standard " +
            ",(r.consumption / b.standard * b.consumption) as consumption " +
            ",b.remark " +
            ",b.use_yn " +
            ",b.reg_date " +
            ",b.mod_date " +
            "FROM bom b " +
            "INNER JOIN rc r " +
            "ON b.product_cd = r.material_cd " +
            ") " +
            "select " +
            "* " +
            "from rc;",
            nativeQuery = true)
    List<BOM> findBypItem();*/
    //List<BOM> findBypItem(@Param("itemCd") String itemCd);




    @Query(value = "WITH recursive rc as("
            + " SELECT std.bom_no, std.product_cd, std.material_cd, 1 as depth, :standard as standard,"
            + " (std.consumption * :standard / std.standard) as consumption, std.remark, std.use_yn,"
            + " std.reg_date, std.mod_date"
            + " FROM bom std"
            + " WHERE std.product_cd = :productCode"
            + " UNION"
            + " SELECT b.bom_no, b.product_cd, b.material_cd, r.depth + 1 as depth, r.consumption as standard,"
            + " (r.consumption / b.standard * b.consumption) as consumption, b.remark, b.use_yn,"
            + " b.reg_date, b.mod_date"
            + " FROM bom b"
            + " INNER JOIN rc r"
            + " ON b.product_cd = r.material_cd"
            + " )"
            + " SELECT a.*"
            + " FROM rc a", nativeQuery = true)
        List<BOM> findBypItem(@Param("productCode") String productCode, @Param("standard") Long standard);

/*    @Query(value = "WITH recursive rc as("
            + " SELECT std.bom_no, std.product_cd, std.material_cd, 1 as depth, :standard as standard,"
            + " (std.consumption * :standard / std.standard) as consumption, std.remark, std.use_yn,"
            + " std.reg_date, std.mod_date"
            + " FROM bom std"
            + " WHERE std.product_cd = :productCode"
            + " UNION"
            + " SELECT b.bom_no, b.product_cd, b.material_cd, r.depth + 1 as depth, r.consumption as standard,"
            + " (r.consumption / b.standard * b.consumption) as consumption, b.remark, b.use_yn,"
            + " b.reg_date, b.mod_date"
            + " FROM bom b"
            + " INNER JOIN rc r"
            + " ON b.product_cd = r.material_cd"
            + " )"
            + " SELECT a.bom_no, a.product_cd, a.material_cd, a.depth, a.standard, a.consumption,"
            + " IFNULL((SELECT (a.consumption - sum(ifnull(qty, 0))) FROM stock  WHERE a.material_cd = item_cd GROUP BY item_cd), a.consumption) AS needQty, a.reg_date, a.mod_date,"
            + " a.remark, a.use_yn"
            + " FROM rc a", nativeQuery = true)
    List<BOMDTO> findNeedQtyBypItem(@Param("productCode") String productCode, @Param("standard") int standard);*/
    @Query(nativeQuery = true)
    List<BOMDTO> findNeedQtyBypItem(@Param("productCode") String productCode, @Param("standard") int standard);

    List<BOM> findByProductItem(Item pitem);
}
