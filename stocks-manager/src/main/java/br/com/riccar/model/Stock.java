package br.com.riccar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name= "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "seq_stock")
    @SequenceGenerator(name = "seq_stock", sequenceName = "seq_stock", allocationSize = 1, initialValue = 12 )
    private Integer id;

    @NotNull
    @Column(name= "name")
    private String name;

    @NotNull
    @Column(name= "price")
    private BigDecimal price;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Column(name= "date_purchase")
    private LocalDate datePurchase;

    /*@JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateLastDividends;

    @NotNull
    @Column(name= "today_volum")
    private BigDecimal todayVolum;

    @NotNull
    @Column(name= "pre_market")
    private BigDecimal preMarket;

    @NotNull
    @Column(name= "after_hours")
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
                "name='" + name + '\'' +
                ", price=" + price +
                ", datePurchase=" + datePurchase +
       //         ", dateLastDividends=" + dateLastDividends +
       //         ", todayVolum=" + todayVolum +
                '}';
    }
}
