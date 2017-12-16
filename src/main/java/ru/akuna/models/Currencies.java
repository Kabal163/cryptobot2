package ru.akuna.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currencies
{
    private boolean success;
    private String message;

    @JsonProperty("result")
    private Collection<Currency> currencies;

    public Currencies()
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

    public Collection<Currency> getCurrencies()
    {
        return currencies;
    }

    public void setCurrencies(Collection<Currency> currencies)
    {
        this.currencies = currencies;
    }

    @Override
    public String toString()
    {
        return "Currencies{" +
                "currencies=" + currencies +
                '}';
    }
}
