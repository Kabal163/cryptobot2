package ru.akuna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Market
{
    @JsonProperty("Last")
    private double last;

    @JsonProperty("Bid")
    private double bid;

    @JsonProperty("Ask")
    private double ask;

    @JsonProperty("BelowPrice")
    private double belowPrice;

    public double getBelowPrice() {
        return belowPrice;
    }

    public void setBelowPrice(double belowPrice) {
        this.belowPrice = belowPrice;
    }

    public double getAbovePrice() {
        return abovePrice;
    }

    public void setAbovePrice(double abovePrice) {
        this.abovePrice = abovePrice;
    }

    @JsonProperty("AbovePrice")
    private double abovePrice;

    @JsonProperty("MarketName")
    private String marketName;

    public double getLast()
    {
        return last;
    }

    public void setLast(double last)
    {
        this.last = last;
    }

    public double getBid()
    {
        return bid;
    }

    public void setBid(double bid)
    {
        this.bid = bid;
    }

    public double getAsk()
    {
        return ask;
    }

    public void setAsk(double ask)
    {
        this.ask = ask;
    }

    public String getMarketName()
    {
        return marketName;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
    }

    @Override
    public String toString()
    {
        return "BittrexMarket{" +
                "last=" + last +
                ", bid=" + bid +
                ", ask=" + ask +
                ", marketName='" + marketName + '\'' +
                '}';
    }

    private static final Logger log = LoggerFactory.getLogger(Market.class);

}
