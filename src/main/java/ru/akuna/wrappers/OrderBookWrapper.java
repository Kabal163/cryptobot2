package ru.akuna.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderBookWrapper
{
    @JsonProperty("result")
    public void setOrderBooksFromJson(Map<String, Collection<Map<String, Double>>> data)
    {
        if (success)
        {
            getBuyOrderBook = data.get("buy");
            getSellOrderBook = data.get("sell");
        }
    }

    public Collection<Map<String, Double>> getGetBuyOrderBook()
    {
        return getBuyOrderBook;
    }

    public Collection<Map<String, Double>> getGetSellOrderBook()
    {
        return getSellOrderBook;
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

    private Collection<Map<String, Double>> getBuyOrderBook, getSellOrderBook;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @Override
    public String toString()
    {
        return "OrderBookWrapper{" +
                "getBuyOrderBook=" + getBuyOrderBook +
                ", getSellOrderBook=" + getSellOrderBook +
                '}';
    }
}
