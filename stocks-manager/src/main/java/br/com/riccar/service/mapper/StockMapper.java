package br.com.riccar.service.mapper;

import br.com.riccar.model.Stock;
import br.com.riccar.service.DTO.StockDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockMapper {

    public StockDTO toDTO(Stock stock) {
        StockDTO stockDTO = new StockDTO();

        stockDTO.setName(stock.getName());
        stockDTO.setPrice(stock.getPrice());
        stockDTO.setDatePurchase(stock.getDatePurchase());
//        stockDTO.setTodayVolum(stock.getNumeroConta());
//        stockDTO.setPreMarket(stock.getDataNascimento());
//        stockDTO.setAfterHours(stock.)

        return stockDTO;
    }

    public Stock toEntity(StockDTO StockDTO) {
        Stock stock = new Stock();

        stock.setName(StockDTO.getName());
        stock.setPrice(StockDTO.getPrice());
        stock.setDatePurchase(StockDTO.getDatePurchase());
     /*   stock.setNumeroConta(StockDTO.getNumeroConta());
        stock.setDataNascimento(StockDTO.getDataNascimento());
        stock.setDataNascimento(StockDTO.getDataNascimento());*/

        return stock;

    }

    public List<StockDTO> toDTOList(List<Stock> stockList) {

        List<StockDTO> stockListDTO = stockList.stream()
                .map(entityHist -> new StockDTO(entityHist.getId(),entityHist.getName(), entityHist.getPrice(), entityHist.getDatePurchase()))
                //        entityHist.getSaldo(), entityHist.getNumeroConta(), entityHist.getDataNascimento()))
                .collect(Collectors.toList());
        return stockListDTO;
    }

    public List<Stock> toEntityList(List<StockDTO> stockDTOList) {

        List<Stock> stockList = new ArrayList<Stock>();

        for (StockDTO dt : stockDTOList) {
            //     for(Cliente entity :clienteList) {
            Stock st = new Stock();
            st.setName(dt.getName());
            st.setPrice(dt.getPrice());
            st.setDatePurchase(dt.getDatePurchase());
//            st.setExclusive(dt.getExclusive());
//            st.setDataNascimento(dt.getDataNascimento());
            stockList.add(st);
        }
        //   }

        return stockList;
    }


}
