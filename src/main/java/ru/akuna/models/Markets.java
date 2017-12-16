package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Markets
{
    private boolean success;
    private String message;

    @JsonProperty("result")
    private Collection<Market> markets;

    public Markets()
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

    public Collection<Market> getMarkets()
    {
        return markets;
    }

    public void setMarkets(Collection<Market> markets)
    {
        this.markets = markets;
    }

    @Override
    public String toString()
    {
        return "Markets{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", markets=" + markets +
                '}';
    }
}
