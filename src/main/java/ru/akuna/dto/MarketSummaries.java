package ru.akuna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketSummaries
{
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;


    @JsonProperty("result")
    private Collection<Market> markets;

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

    public Collection<Market> getMarkets()
    {
        return markets;
    }

    public void setMarkets(Collection<Market> markets)
    {
        this.markets = markets;
    }
}
