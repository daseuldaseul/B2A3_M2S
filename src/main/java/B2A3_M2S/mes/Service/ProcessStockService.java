package B2A3_M2S.mes.Service;


import B2A3_M2S.mes.dto.StockDto;
import B2A3_M2S.mes.entity.Stock;
import B2A3_M2S.mes.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessStockService {

    @Autowired
    StockRepository stockRepository;

    public List<StockDto> getStockList(){
        List<Stock> stockList = stockRepository.findAll();


        return null;
    }


}
