package B2A3_M2S.mes.repository;

import B2A3_M2S.mes.entity.BOM;
import B2A3_M2S.mes.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Long> {
}
