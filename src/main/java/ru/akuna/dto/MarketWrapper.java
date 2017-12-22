package ru.akuna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketWrapper
{
    @JsonProperty("Last")
    private double last;

    @JsonProperty("Bid")
    private double bid;

    @JsonProperty("Ask")
    private double ask;

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
        return "MarketWrapper{" +
                "last=" + last +
                ", bid=" + bid +
                ", ask=" + ask +
                ", marketName='" + marketName + '\'' +
                '}';
    }
}
