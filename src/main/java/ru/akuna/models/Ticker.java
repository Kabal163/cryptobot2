package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Los Pepes on 12/9/2017.
 */
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

    public Ticker()
    {
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

    public double getLast()
    {
        return last;
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
                ", bid=" + bid +
                ", ask=" + ask +
                ", last=" + last +
                '}';
    }
}
