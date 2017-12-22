package ru.akuna.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketSummariesWrapper
{
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;


    @JsonProperty("result")
    private List<MarketWrapper> marketWrappers;

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

    public List<MarketWrapper> getMarketWrappers()
    {
        return marketWrappers;
    }

    public void setMarketWrappers(List<MarketWrapper> marketWrappers)
    {
        this.marketWrappers = marketWrappers;
    }
}
