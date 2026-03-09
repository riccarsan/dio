package br.com.riccar.service.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Schema(description = "Representação de uma de um ativo de ação da B3 - Brasil, Bolsa, Balcão")
public class StockDTO {

    public StockDTO(Integer id, String name, BigDecimal price, LocalDate datePurchase) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.datePurchase = datePurchase;
    }

    public StockDTO(){

    }

    @JsonIgnore
    private Integer id;

    @NotBlank(message = "Campo obrigatório não informado")
    private String name;

    @NotBlank(message = "Campo obrigatório não informado")
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate datePurchase;

    /*@JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateLastDividends;

    private BigDecimal todayVolum;

    private BigDecimal preMarket;

    private BigDecimal afterHours;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(LocalDate datePurchase) {
        this.datePurchase = datePurchase;
    }

//    public BigDecimal getPreMarket() {
//        return preMarket;
//    }
//
//    public void setPreMarket(BigDecimal preMarket) {
//        this.preMarket = preMarket;
//    }
//
//    public BigDecimal getAfterHours() {
//        return afterHours;
//    }
//
//    public void setAfterHours(BigDecimal afterHours) {
//        this.afterHours = afterHours;
//    }
//

//
//    public LocalDate getDateLastDividends() {
//        return dateLastDividends;
//    }
//
//    public void setDateLastDividends(LocalDate dateLastDividends) {
//        this.dateLastDividends = dateLastDividends;
//    }
//
//    public BigDecimal getTodayVolum() {
//        return todayVolum;
//    }
//
//    public void setTodayVolum(BigDecimal todayVolum) {
//        this.todayVolum = todayVolum;
//    }

    @Override
    public String toString() {
        return "StockDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", datePurchase=" + datePurchase +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDTO stockDTO = (StockDTO) o;
        return Objects.equals(id, stockDTO.id) && Objects.equals(name, stockDTO.name) && Objects.equals(price, stockDTO.price) && Objects.equals(datePurchase, stockDTO.datePurchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, datePurchase);
    }
}
