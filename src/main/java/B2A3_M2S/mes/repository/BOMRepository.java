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
public interface BOMRepository extends JpaRepository<BOM, Long>, QuerydslPredicateExecutor<BOM> {
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


/*
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
            + " FROM rc a"
            + " WHERE a.material_cd = :materialCd", nativeQuery = true)
    List<BOM> findBypItem(@Param("productCode") String productCode, @Param("standard") Long standard,  @Param("materialCd") String materialCd);
*/


    @Query("SELECT (b.consumption * :std) as consumption FROM BOM b WHERE b.productItem.itemCd = :pCode and b.materialItem.itemCd = :mCode")
    Double findByProductItemAndMaterialItem(@Param("pCode")String pCode, @Param("mCode")String mCode, @Param("std")double std);
    @Query(nativeQuery = true)
    List<BOMDTO> findNeedQtyBypItem(@Param("productCode") String productCode, @Param("standard") int standard);

    List<BOM> findByProductItem(Item pitem);

    BOM findByBomNo(Long bonNo);
}
