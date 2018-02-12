package ru.akuna.publishing.events;

import org.springframework.context.ApplicationEvent;

public class CandleCloseEvent extends ApplicationEvent
{
    private String marketName;

    public CandleCloseEvent(Object source, String marketName)
    {
        super(source);
        this.marketName = marketName;
    }

    public String getMarketName()
    {
        return marketName;
    }
}
