package ru.akuna.publishing.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.akuna.publishing.events.CandleCloseEvent;
import ru.akuna.solutions.strategy.PriceChangedStrategy;

@Component
public class CandleCloseEventListener implements ApplicationListener<CandleCloseEvent>
{

    @Override
    public synchronized void onApplicationEvent(CandleCloseEvent candleCloseEvent)
    {
        priceChangedStrategy.run(candleCloseEvent.getMarketName());
    }

    private static final Logger LOG = LoggerFactory.getLogger(CandleCloseEventListener.class);

    @Autowired
    private PriceChangedStrategy priceChangedStrategy;
}
