package br.com.riccar.controller;

import br.com.riccar.service.DTO.StockDTO;
import br.com.riccar.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StockController {

    @Autowired
    private StockService stockService;


    @ApiResponse(responseCode = "201", description = "201 Created success.")
    @Operation(summary = "Cria um registro de uma ação", description = "Api que Representa um modelo de ações.")
    @ResponseBody
    @PostMapping(value = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDTO> create(@Valid @RequestBody StockDTO stockDTO) {

        return ResponseEntity.ok(this.stockService.createStock(stockDTO));

    }

    @GetMapping(value = "/stocks")
    @Operation(summary ="API que lista todos os clientes.",  description = "Api que Representa um modelo de ações.")
    @ApiResponse(responseCode = "200", description = "OK. Success.")
    public ResponseEntity<List<StockDTO>> findAllStocks() {
        return ResponseEntity.ok((this.stockService.findAllStockes()));
    }

   /* @GetMapping(value = "/stocks/id")
    @Operation(summary ="API que lista uma ação",  description = "Api que Representa um modelo de ações.")
    @ApiResponse(responseCode = "200", description = "OK. Success.")
    public ResponseEntity<StockDTO> findByName(@Valid @RequestParam("name") String name) {
        return ResponseEntity.ok((this.stockService.(name));
    }*/
}
