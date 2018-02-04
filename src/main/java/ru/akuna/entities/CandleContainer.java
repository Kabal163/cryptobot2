package ru.akuna.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.ExceptionMessages;
import ru.akuna.tools.properties.ApplicationProperties;

import java.util.*;


/**
 * Created by Los Pepes on 2/3/2018.
 * Таска будет управлять данным контейнером. Едиственное что нужно знать таске - это когда закрывать свечу.
 */
@Component
public class CandleContainer
{
    public void init()
    {
        candlesNumber = strategyProperties.getIntProperty(CANDLES_NUMBER);
        candlesByMarketName = new LinkedList<>();
    }

    /**
     * The method edits current open candle. If candle is closed - exception is raised.
     * @param candle the source candle which we use to update the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    public void updateCandle(Candle candle) throws Exception
    {
        update(candle);
    }

    /**
     * If current candle is open method updates it firstly. After that candle will be closed.
     * @param candle the source candle which we use to close the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    public void updateAndCloseCandle(Candle candle) throws Exception
    {
        CandlesHolder holder = update(candle);
        holder.candles.getLast().close();
        startNewCandle(holder);
    }

    /**
     * @param marketName the name of the market which we use to find appropriate candle.
     * @return last closed candle for this market.
     */
    public Candle getLastClosedCandle(String marketName)
    {
        LinkedList<Candle> candles = getCandleHolder(marketName).candles;
        Candle lastCandle = candles.getLast();
        if(!lastCandle.isOpen())
        {
            return lastCandle;
        }
        else
        {
            return candles.get(candles.size() - 2);
        }
    }

    /**
     * @param marketName the name of the market which we use to find appropriate candle.
     * @return last open candle for this market.
     */
    public Candle getOpenCandle(String marketName)
    {
        return getCandleHolder(marketName).candles.getLast();
    }

    /**
     * @param marketName the name of the market which we use to find appropriate candles.
     * @return all candles for this market.
     */
    public List<Candle> getAllCandles(String marketName)
    {
        return getCandleHolder(marketName).candles;
    }

    /**
     * @param marketName the name of the market which we use to find appropriate candles.
     * @param amountOf amount of last CLOSED candles which we want to get
     * @return specified number of closed candles for this market.
     */
    public List<Candle> getSpecifiedLastCandles(String marketName, int amountOf)
    {
        CandlesHolder holder = getCandleHolder(marketName);
        Candle[] c1 = new Candle[holder.candles.size()];
        c1 = holder.candles.toArray(c1);

        int startIndex = holder.candles.size() - amountOf;
        int lastIndex = holder.candles.size() - 1;

        Candle[] c2 = Arrays.copyOfRange(c1, startIndex, lastIndex);
        return Arrays.asList(c2);
    }

    private CandlesHolder update(Candle candle) throws Exception
    {
        CandlesHolder holder = getCandleHolder(candle.getMarketName());
        Candle candleToBeUpdated = holder.candles.getLast();
        setPrices(candleToBeUpdated, candle);

        return holder;
    }

    private void startNewCandle(CandlesHolder holder)
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
        throw new IllegalArgumentException(ExceptionMessages.NO_SUCH_CANDLE);
    }

    private void setPrices(Candle candleToBeUpdated, Candle freshCandle)
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
        String marketName;
        LinkedList<Candle> candles;
    }

    @Autowired
    private ApplicationProperties strategyProperties;

    private List<CandlesHolder> candlesByMarketName;
    private int candlesNumber;

    private final static String CANDLES_NUMBER = ""; //todo
}
