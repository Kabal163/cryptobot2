package ru.akuna.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.akuna.wrappers.OrderBookWrapper;
import ru.akuna.wrappers.TickerWrapper;

import java.text.MessageFormat;

import static ru.akuna.BittrexUrls.GET_ORDER_BOOK;
import static ru.akuna.BittrexUrls.GET_TICKER;


public class MarketService
{
    @Autowired
    private RestTemplate restTemplate;

    public MarketJob createMarketJob(String market)
    {
        TickerWrapper ticker = getTicker(market);
        OrderBookWrapper orderBook = getOrderBook(market);

        return new MarketJob(ticker, orderBook);
    }

    private OrderBookWrapper getOrderBook(String market)
    {
        String getOrderBookUrl = MessageFormat.format(GET_ORDER_BOOK, market);

        return restTemplate.getForObject(getOrderBookUrl, OrderBookWrapper.class);
    }

    private TickerWrapper getTicker(String market)
    {
        String getTickerUrl = MessageFormat.format(GET_TICKER, market);

        return restTemplate.getForObject(getTickerUrl, TickerWrapper.class);
    }

    private static final Logger log = LoggerFactory.getLogger(MarketService.class);
}
