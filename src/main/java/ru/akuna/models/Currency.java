package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency
{
    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("CurrencyLong")
    private String currencyLong;

    @JsonProperty("MinConfirmation")
    private int minConfirmation;

    @JsonProperty("TxFee")
    private double txFee;

    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("CoinType")
    private String coinType;

    @JsonProperty("BaseAddress")
    private String baseAddress;

    public Currency()
    {
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getCurrencyLong()
    {
        return currencyLong;
    }

    public void setCurrencyLong(String currencyLong)
    {
        this.currencyLong = currencyLong;
    }

    public int getMinConfirmation()
    {
        return minConfirmation;
    }

    public void setMinConfirmation(int minConfirmation)
    {
        this.minConfirmation = minConfirmation;
    }

    public double getTxFee()
    {
        return txFee;
    }

    public void setTxFee(double txFee)
    {
        this.txFee = txFee;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public String getCoinType()
    {
        return coinType;
    }

    public void setCoinType(String coinType)
    {
        this.coinType = coinType;
    }

    public String getBaseAddress()
    {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress)
    {
        this.baseAddress = baseAddress;
    }

    @Override
    public String toString()
    {
        return "Currency{" +
                "currency='" + currency + '\'' +
                ", currencyLong='" + currencyLong + '\'' +
                ", minConfirmation='" + minConfirmation + '\'' +
                ", txFee='" + txFee + '\'' +
                ", coinType='" + coinType + '\'' +
                ", baseAddress='" + baseAddress + '\'' +
                '}';
    }
}
