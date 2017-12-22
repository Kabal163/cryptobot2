package ru.akuna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public void testPerformance()
    {
        for (int i = 0; i < 99999999; i ++)
        {
            if (i == 99999998)
            {
                log.info("Market: " + getMarketName() + " finished analysis.");
            }
        }
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

    private static final Logger log = LoggerFactory.getLogger(MarketWrapper.class);

}
