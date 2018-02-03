package ru.akuna.entities;


import ru.akuna.entities.listeners.BittrexMarketListener;

import javax.persistence.*;

@EntityListeners(BittrexMarketListener.class)
@Table(name="markets")
@Entity
public class BittrexMarket
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String marketName;

    private double abovePrice;

    private double belowPrice;

    public String getAbovePriceString()
    {
        return String.valueOf(abovePrice);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public double getAbovePrice() {
        return abovePrice;
    }

    public void setAbovePrice(double abovePrice) {
        this.abovePrice = abovePrice;
    }

    public double getBelowPrice() {
        return belowPrice;
    }

    public void setBelowPrice(double belowPrice) {
        this.belowPrice = belowPrice;
    }
}
