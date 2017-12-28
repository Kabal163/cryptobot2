package ru.akuna.strategy.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.dto.Ticker;
import ru.akuna.strategy.stockService.TickerService;

/**
 * Created by Los Pepes on 12/27/2017.
 */
@Component
@Scope("prototype")
public class TickerTask
{
    public String start(Market market)
    {
        Ticker ticker = tickerService.getTicker(market.getMarketName());

        if (isFirstTime)
        {
            lastPrice = market.getLast();
        }

        String diff = "Price increased by " + getDiff(lastPrice, ticker.getLast());
        String result = diff + "\n" + taskCounter++ + ". TICKER: ASK = " + ticker.getAsk()
                + ", BID = " + ticker.getBid()
                + ", LAST = " + ticker.getLast();
        lastPrice = ticker.getLast();
        isFirstTime = false;

        LOG.info(result);
        return result;
    }

    private double getDiff(double lastPrice, double newPrice)
    {
        return (newPrice - lastPrice) / lastPrice * 100;
    }

    @Autowired
    private TickerService tickerService;
    private static final Logger LOG = LoggerFactory.getLogger(TickerTask.class);
    private double lastPrice;
    private boolean isFirstTime = true;
    private int taskCounter = 0;
}