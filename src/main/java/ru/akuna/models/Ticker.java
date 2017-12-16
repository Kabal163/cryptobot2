package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker
{
    private boolean success;
    private String message;

    @JsonProperty("Bid")
    private double bid;

    @JsonProperty("Ask")
    private double ask;

    @JsonProperty("Last")
    private double last;

    private DecimalFormat df;


    public Ticker()
    {
        df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public double getBid()
    {
        return bid;
    }

    public String getStringBid()
    {
        return df.format(bid);
    }

    public void setBid(double bid)
    {
        this.bid = bid;
    }

    public double getAsk()
    {
        return ask;
    }

    public String getStringAsk()
    {
        return df.format(ask);
    }

    public void setAsk(double ask)
    {
        this.ask = ask;
    }

    public double getLast()
    {
        return last;
    }

    public String getStringLast()
    {
        return df.format(last);
    }

    public void setLast(double last)
    {
        this.last = last;
    }

    @Override
    public String toString()
    {
        return "Ticker{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", bid=" + getStringBid() +
                ", ask=" + getStringAsk() +
                ", last=" + getStringLast() +
                '}';
    }
}
