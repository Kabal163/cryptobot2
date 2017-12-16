package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Market
{
    @JsonProperty("MarketCurrency")
    private String marketCurrency;

    @JsonProperty("BaseCurrency")
    private String BaseCurrency;

    @JsonProperty("MarketCurrencyLong")
    private String marketCurrencyLong;

    @JsonProperty("BaseCurrencyLong")
    private String baseCurrencyLong;

    @JsonProperty("MinTradeSize")
    private double minTradeSize;

    @JsonProperty("MarketName")
    private String marketName;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("Created")
    private Date created;

    public Market()
    {
    }

    public String getMarketCurrency()
    {
        return marketCurrency;
    }

    public void setMarketCurrency(String marketCurrency)
    {
        this.marketCurrency = marketCurrency;
    }

    public String getBaseCurrency()
    {
        return BaseCurrency;
    }

    public void setBaseCurrency(String baseCurrency)
    {
        BaseCurrency = baseCurrency;
    }

    public String getMarketCurrencyLong()
    {
        return marketCurrencyLong;
    }

    public void setMarketCurrencyLong(String marketCurrencyLong)
    {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    public String getBaseCurrencyLong()
    {
        return baseCurrencyLong;
    }

    public void setBaseCurrencyLong(String baseCurrencyLong)
    {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    public double getMinTradeSize()
    {
        return minTradeSize;
    }

    public void setMinTradeSize(double minTradeSize)
    {
        this.minTradeSize = minTradeSize;
    }

    public String getMarketName()
    {
        return marketName;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    @Override
    public String toString()
    {
        return "Market{" +
                "marketCurrency='" + marketCurrency + '\'' +
                ", BaseCurrency='" + BaseCurrency + '\'' +
                ", marketCurrencyLong='" + marketCurrencyLong + '\'' +
                ", baseCurrencyLong='" + baseCurrencyLong + '\'' +
                ", minTradeSize=" + minTradeSize +
                ", marketName='" + marketName + '\'' +
                ", isActive=" + isActive +
                ", created=" + created +
                '}';
    }
}
