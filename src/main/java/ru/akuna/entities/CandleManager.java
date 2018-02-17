package ru.akuna.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.akuna.publishing.events.CandleCloseEvent;
import ru.akuna.tools.properties.ApplicationProperties;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Los Pepes on 2/3/2018.
 */
@Component
public class CandleManager
{
    @PostConstruct
    public void init()
    {
        candleHandlerSize = strategyProperties.getIntProperty(CANDLE_HANDLER_SIZE);
        countOfCandleUpdatesBeforeClose = strategyProperties.getIntProperty(COUNT_OF_CANDLE_UPDATES_BEFORE_CLOSE);

        candlesByMarketName = new LinkedList<>();
    }



    /**
     * The method edits current open candle. If candle is closed - exception is raised.
     * @param candle the source candle which we use to update the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    public synchronized void updateCandle(Candle candle)
    {
        CandlesHolder holder = getCandleHolder(candle.getMarketName());

        if (holder == null)
        {
            createNewCandleHolder(candle);
        }
        else
        {
            updateExistsCandleHolder(candle, holder);
        }
    }



    /**
     * @param marketName the name of the market which we use to find appropriate candle.
     * @return last closed candle for this market.
     */
    public synchronized Candle getLastClosedCandle(String marketName)
    {
        CandlesHolder candlesHolder = getCandleHolder(marketName);

        if (candlesHolder != null)
        {
            LinkedList<Candle> candles = candlesHolder.candles;

            Candle lastCandle = candles.getLast();

            if (!lastCandle.isOpen())
            {
                return lastCandle;
            }
/*            else
            {
                return candles.get(candles.size() - 2);
            }*/
        }

        return null;
    }

    public synchronized Candle getFirstClosedCandle(String marketName)
    {
        CandlesHolder candlesHolder = getCandleHolder(marketName);

        if (candlesHolder != null)
        {
            LinkedList<Candle> candles = candlesHolder.candles;

            Candle lastCandle = candles.getFirst();

            if (!lastCandle.isOpen())
            {
                return lastCandle;
            }
/*            else
            {
                return candles.get(candles.size() - 2);
            }*/
        }

        return null;
    }



    /**
     * @param marketName the name of the market which we use to find appropriate candle.
     * @return last open candle for this market.
     */
    public synchronized Candle getOpenCandle(String marketName)
    {
        CandlesHolder candlesHolder = getCandleHolder(marketName);

        if (candlesHolder != null)
        {
            return candlesHolder.candles.getLast();
        }

        return null;
    }



    /**
     * @param marketName the name of the market which we use to find appropriate candles.
     * @return all candles for this market.
     */
    public synchronized List<Candle> getAllCandles(String marketName)
    {
        CandlesHolder holder = getCandleHolder(marketName);

        if (holder != null)
        {
            return holder.candles;
        }

        return null;
    }



    /**
     * @param marketName the name of the market which we use to find appropriate candles.
     * @param amountOf amount of last CLOSED candles which we want to get
     * @return specified number of closed candles for this market.
     */
    public synchronized List<Candle> getSpecifiedLastCandles(String marketName, int amountOf)
    {
        CandlesHolder holder = getCandleHolder(marketName);

        if (holder == null)
        {
            return null;
        }

        Candle[] c1 = new Candle[holder.candles.size()];
        c1 = holder.candles.toArray(c1);

        int startIndex = holder.candles.size() - amountOf;
        int lastIndex = holder.candles.size() - 1;

        Candle[] c2 = Arrays.copyOfRange(c1, startIndex, lastIndex);
        return Arrays.asList(c2);
    }



    private synchronized CandlesHolder updateExistsCandleHolder(Candle candle, CandlesHolder holder)
    {
        Candle candleToBeUpdated = holder.candles.getLast();

        if (candleToBeUpdated.isOpen())
        {
            setPrices(candleToBeUpdated, candle);
            candleToBeUpdated.incrementUpdateCounter();

            if (isReadyToClose(candleToBeUpdated))
            {
                candleToBeUpdated.close();
                throwCandleCloseEvent(candleToBeUpdated);
                startNewCandle(holder);
            }
        }

        return holder;
    }

    private void throwCandleCloseEvent(Candle candle)
    {
        applicationEventPublisher.publishEvent(new CandleCloseEvent(candle, candle.getMarketName()));
    }


    private synchronized boolean isReadyToClose(Candle candle)
    {
        return countOfCandleUpdatesBeforeClose == candle.getCountOfUpdates();
    }



    private synchronized CandlesHolder createNewCandleHolder(Candle candle)
    {
        CandlesHolder holder = new CandlesHolder(candle);
        candlesByMarketName.add(holder);

        return holder;
    }



    private synchronized void startNewCandle(CandlesHolder holder)
    {
        Candle lastCandle = holder.candles.getLast();

        if (holder.candles.size() == candleHandlerSize)
        {
            holder.candles.removeFirst();
        }

        Candle candle = new Candle(lastCandle.getLastPrice(), holder.marketName);
        holder.candles.add(candle);
    }



    private synchronized CandlesHolder getCandleHolder(String marketName)
    {
        for (CandlesHolder holder : candlesByMarketName)
        {
            if (marketName.equals(holder.marketName))
            {
                return holder;
            }
        }

        return null;
    }



    private synchronized void setPrices(Candle candleToBeUpdated, Candle freshCandle)
    {
        double oldMinPrice = candleToBeUpdated.getMinPrice();
        double oldMaxPrice = candleToBeUpdated.getMaxPrice();

        double newMinPrice = freshCandle.getMinPrice();
        double newMaxPrice = freshCandle.getMaxPrice();

        if(oldMaxPrice < newMaxPrice) candleToBeUpdated.setMaxPrice(newMaxPrice);
        if(oldMinPrice > newMinPrice) candleToBeUpdated.setMinPrice(newMinPrice);

        candleToBeUpdated.setLastPrice(freshCandle.getLastPrice());
    }



    private class CandlesHolder
    {
        CandlesHolder(Candle candle)
        {
            candles = new LinkedList<>();
            candles.add(candle);
            marketName = candle.getMarketName();
        }

        String marketName;
        LinkedList<Candle> candles;
    }



    @Autowired
    private ApplicationProperties strategyProperties;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private List<CandlesHolder> candlesByMarketName;
    private int candleHandlerSize, countOfCandleUpdatesBeforeClose;

    private final static String CANDLE_HANDLER_SIZE = "candle_handler_size";
    private final static String COUNT_OF_CANDLE_UPDATES_BEFORE_CLOSE = "count_of_candle_updates_before_close";
}
