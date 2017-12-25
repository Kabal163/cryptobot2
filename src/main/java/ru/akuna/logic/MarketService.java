package ru.akuna.logic;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.akuna.BittrexUrls;
import ru.akuna.dto.Market;
import ru.akuna.dto.MarketSummaries;
import ru.akuna.dto.OrderBookWrapper;
import ru.akuna.dto.TickerWrapper;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.akuna.BittrexUrls.GET_ORDER_BOOK;
import static ru.akuna.BittrexUrls.GET_TICKER;

/*
* Теперь сервис скедулится благодаря спрингу. Каждые 30 секунд он достает данные с биржи
* Затем на это дело реагирует MarketServiceAspect
*/
@Component
//@EnableAsync
//@EnableCaching
//@Scope("prototype")
public class MarketService
{
    @Autowired
    private RestTemplate restTemplate;

    public List<Market> getAllMarkets()
    {
        return markets;
    }

    @Scheduled(fixedDelay = 30000)
    public void updateMarkets()
    {
        markets = ImmutableList.copyOf(restTemplate.getForObject(BittrexUrls.GET_MARKET_SUMMARIES, MarketSummaries.class).getMarkets());
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

    private List<Market> markets;
    private static final Logger log = LoggerFactory.getLogger(MarketService.class);

}
