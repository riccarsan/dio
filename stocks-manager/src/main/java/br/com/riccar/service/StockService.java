package br.com.riccar.service;

import br.com.riccar.service.DTO.StockDTO;

import java.util.List;

public interface StockService {
    StockDTO createStock(StockDTO StockDTO);

    List<StockDTO> findAllStockes();
}
