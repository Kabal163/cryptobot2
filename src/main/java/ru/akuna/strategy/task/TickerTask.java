package ru.akuna.strategy.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.dto.Ticker;
import ru.akuna.strategy.stockService.TickerService;

import static ru.akuna.tools.MathTools.*;

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
        String diff = "Price increased by " + getDiff(market.getLast(), ticker.getLast());
        String result = diff + "\n" + taskCounter++ + ". TICKER: ASK = " + ticker.getAsk()
                + ", BID = " + ticker.getBid()
                + ", LAST = " + ticker.getLast();
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
    private int taskCounter = 0;
}
