package br.com.riccar.service.impl;

import br.com.riccar.exception.handler.StockAlreadyExistException;
import br.com.riccar.model.Stock;
import br.com.riccar.repository.StockRepository;
import br.com.riccar.service.DTO.StockDTO;
import br.com.riccar.service.StockService;
import br.com.riccar.service.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    public StockServiceImpl(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    private StockRepository stockRepository;

    private StockMapper stockMapper;

    @Override
    public StockDTO createStock(StockDTO stockDTO) {
        Stock stock = stockRepository.findByName(stockDTO.getName());
        if(stock != null) {
            throw new StockAlreadyExistException("A ação " + stock.getName() + " já existe");
        }
        return stockMapper.toDTO(stockRepository.save(this.stockMapper.toEntity(stockDTO)));
    }


    @Override
    public List<StockDTO> findAllStockes() {
        return stockRepository.findAll().stream()
                .map(stockMapper::toDTO)
                .collect(Collectors.toList());
    }
}
