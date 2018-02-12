package ru.akuna.publishing.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.akuna.publishing.events.CandleCloseEvent;

@Component
public class CandleCloseEventListener implements ApplicationListener<CandleCloseEvent>
{

    @Override
    public void onApplicationEvent(CandleCloseEvent candleCloseEvent)
    {
        //todo run strategy
    }
}
