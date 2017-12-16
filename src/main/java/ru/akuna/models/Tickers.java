package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tickers
{
    @JsonProperty("result")
    private Ticker ticker;

    private String message;
    private boolean success;

    public Ticker getTicker()
    {
        return ticker;
    }

    public void setTickers(Ticker ticker)
    {
        this.ticker = ticker;
    }

    public Tickers()
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

    @Override
    public String toString()
    {
        return "Tickers{" +
                "tickers=" + ticker +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
