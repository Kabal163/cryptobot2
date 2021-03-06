package ru.akuna.solutions.stockService;

import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.akuna.BittrexUrls;
import ru.akuna.dto.Market;
import ru.akuna.dto.MarketSummaries;
import ru.akuna.dto.OrderBookWrapper;
import ru.akuna.dto.Ticker;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import static ru.akuna.BittrexUrls.GET_ORDER_BOOK;
import static ru.akuna.BittrexUrls.GET_TICKER;

@Component
public class MarketService
{
    @Autowired
    private RestTemplate restTemplate;
    private List<Market> cachedMarkets;

    public List<Market> getAllMarkets()
    {
        cachedMarkets = ImmutableList.copyOf(restTemplate.getForObject(BittrexUrls.GET_MARKET_SUMMARIES, MarketSummaries.class).getMarkets());
        return cachedMarkets;
    }

    private OrderBookWrapper getOrderBook(String market)
    {
        String getOrderBookUrl = MessageFormat.format(GET_ORDER_BOOK, market);

        return restTemplate.getForObject(getOrderBookUrl, OrderBookWrapper.class);
    }

    private Ticker getTicker(String market)
    {
        String getTickerUrl = MessageFormat.format(GET_TICKER, market);

        return restTemplate.getForObject(getTickerUrl, Ticker.class);
    }

    public List<Market> getMarketsFromCache()
    {
        return cachedMarkets != null? cachedMarkets : Collections.emptyList();
    }
}
