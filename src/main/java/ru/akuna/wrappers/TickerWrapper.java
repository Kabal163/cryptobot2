package ru.akuna.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerWrapper
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

    //TODO: Make separate singleton bean for this function, need to remove expanant from double value, for reading

    public TickerWrapper()
    {
        df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
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
        return "TickerWrapper{" +
                "bid=" + df.format(bid) +
                ", ask=" + df.format(ask) +
                ", last=" + df.format(last) +
                '}';
    }

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private boolean success;

    private double bid, ask, last;

    private DecimalFormat df;
    private static final Logger log = LoggerFactory.getLogger(TickerWrapper.class);
}
