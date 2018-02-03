package ru.akuna.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.solutions.stockService.MarketService;
import ru.akuna.tools.properties.ApplicationProperties;

import javax.annotation.PostConstruct;
import javax.xml.ws.Holder;
import java.util.*;
import java.util.concurrent.ExecutionException;


/**
 * Created by Los Pepes on 2/3/2018.
 * Таска будет управлять данным контейнером. Едиственное что нужно знать таске - это когда закрывать свечу.
 * То есть у нас всего два публичных метода - это обновление свечи и ее закрытие.
 */
@Component
public class CandleContainer
{
    @PostConstruct
    public void init()
    {
        candlesNumber = strategyProperties.getIntProperty(CANDLES_NUMBER);
        List<Market> cachedMarkets = marketService.getMarketsFromCache();

        createWithInitSize(cachedMarkets.size());
        fillIn(cachedMarkets);

        candlesByMarketName = Collections.unmodifiableList(candlesByMarketName);
    }

    public void editCurrentCandle(Candle candle) throws Exception
    {
        edit(candle);
    }

    public void closeCandle(Candle candle) throws Exception
    {
        CandlesHolder holder = edit(candle);
        holder.candles.getLast().setOpen(false);
        startNewCandle(holder);
    }

    private CandlesHolder edit(Candle candle) throws Exception
    {
        CandlesHolder holder = getCandleHolder(candle.getMarketName());
        Candle candleToBeUpdated = holder.candles.getLast();
        checkIsOpen(candleToBeUpdated);
        candleToBeUpdated.setLastPrice(candle.getLastPrice());
        setMinAndMaxPrice(candleToBeUpdated, candle);
        return holder;
    }

    private void createWithInitSize(int initSize)
    {
        if(initSize > 0)
        {
            candlesByMarketName = new ArrayList<>(initSize);
        }
        else
        {
            candlesByMarketName = new ArrayList<>(DEFAULT_SIZE);
        }
    }

    private void fillIn(List<Market> cachedMarkets)
    {
        for(Market market : cachedMarkets)
        {
            CandlesHolder holder = new CandlesHolder();
            holder.candles = new LinkedList<>();
            holder.marketName = market.getMarketName();

            candlesByMarketName.add(holder);
            startNewCandle(holder);
        }
    }

    private void startNewCandle(CandlesHolder holder)
    {
        if(holder.candles.size() == 0)
        {
            openFirstCandle(holder);
        }
        else
        {
            openBasedOnPrevious(holder);
        }
    }

    private void openFirstCandle(CandlesHolder holder)
    {
       holder.candles.add(new Candle(holder.marketName));
    }

    private void openBasedOnPrevious(CandlesHolder holder)
    {
        Candle lastCandle = holder.candles.getLast();

        if(holder.candles.size() == candlesNumber)
        {
            holder.candles.removeFirst();
        }
        Candle candle = new Candle(lastCandle.getLastPrice(), holder.marketName);
        holder.candles.add(candle);
    }

    private CandlesHolder getCandleHolder(String marketName)
    {
        for(CandlesHolder holder : candlesByMarketName)
        {
            if(holder.marketName.equals(marketName))
            {
                return holder;
            }
        }
        return null;
    }

    private void checkIsOpen(Candle candle) throws Exception
    {
        if(!candle.isOpen()) throw new Exception("The candle of " + candle.getMarketName() + " market is already closed");
    }

    private void setMinAndMaxPrice(Candle candleToBeUpdated, Candle freshCandle)
    {
        double oldMinPrice = candleToBeUpdated.getMinPrice();
        double oldMaxPrice = candleToBeUpdated.getMaxPrice();

        double newMinPrice = freshCandle.getMinPrice();
        double newMaxPrice = freshCandle.getMaxPrice();

        if(oldMaxPrice < newMaxPrice) candleToBeUpdated.setMaxPrice(newMaxPrice);
        if(oldMinPrice > newMinPrice) candleToBeUpdated.setMinPrice(newMinPrice);
    }

    private class CandlesHolder
    {
        String marketName;
        LinkedList<Candle> candles;
    }

    @Autowired
    private MarketService marketService;
    @Autowired
    private ApplicationProperties strategyProperties;

    private List<CandlesHolder> candlesByMarketName;
    private int candlesNumber;

    private final static String CANDLES_NUMBER = ""; //todo
    private final static int DEFAULT_SIZE = 50;

}
