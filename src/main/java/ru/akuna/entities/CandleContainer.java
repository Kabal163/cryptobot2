package ru.akuna.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.strategy.stockService.MarketService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Los Pepes on 2/3/2018.
 */
@Component
public class CandleContainer
{
    @PostConstruct
    public void init()
    {
        int initSize = marketService.getMarketsFromCache().size();
        candlesByMarket = new HashMap<>(initSize);
    }

    @Autowired
    private MarketService marketService;
    private Map<String, List<Candle>> candlesByMarket;

}
