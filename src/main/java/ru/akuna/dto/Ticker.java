package ru.akuna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticker
{

    @JsonProperty("result")
    public void setTickerFromJson(Map<String, Double> data)
    {
        if (success)
        {
            bid = data.get("Bid");
            ask = data.get("Ask");
            last = data.get("Last");
        }
    }

    public double getBid()
    {
        return bid;
    }

    public double getAsk()
    {
        return ask;
    }

    public double getLast()
    {
        return last;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "Ticker{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", bid=" + bid +
                ", ask=" + ask +
                ", last=" + last +
                '}';
    }

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;

    private double bid, ask, last;

    private static final Logger log = LoggerFactory.getLogger(Ticker.class);
}
