package ru.akuna.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akuna.dto.OrderBookWrapper;
import ru.akuna.dto.TickerWrapper;

public class MarketJob
{
    public MarketJob(TickerWrapper tickerWrapper, OrderBookWrapper orderBook)
    {
        this.tickerWrapper = tickerWrapper;
        this.orderBook = orderBook;
    }

    public void start()
    {
        log.info(tickerWrapper.toString());
        log.info(orderBook.toString());

        log.info("MarketWrapper Job have been started to scanning market");
    }

    private static final Logger log = LoggerFactory.getLogger(MarketJob.class);

    private TickerWrapper tickerWrapper;
    private OrderBookWrapper orderBook;
}
