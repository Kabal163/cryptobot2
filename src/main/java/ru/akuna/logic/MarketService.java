package ru.akuna.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.akuna.BittrexUrls;
import ru.akuna.dto.MarketSummaries;
import ru.akuna.dto.Market;
import ru.akuna.dto.OrderBookWrapper;
import ru.akuna.dto.TickerWrapper;

import java.text.MessageFormat;
import java.util.Collection;

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

    public Collection<Market> getAllMarkets()
    {
        return restTemplate.getForObject(BittrexUrls.GET_MARKET_SUMMARIES, MarketSummaries.class).getMarkets();
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
