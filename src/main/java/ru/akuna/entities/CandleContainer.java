package ru.akuna.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.ExceptionMessages;
import ru.akuna.dto.Market;
import ru.akuna.solutions.stockService.MarketService;
import ru.akuna.tools.properties.ApplicationProperties;

import javax.annotation.PostConstruct;
import java.util.*;


/**
 * Created by Los Pepes on 2/3/2018.
 * Таска будет управлять данным контейнером. Едиственное что нужно знать таске - это когда закрывать свечу.
 */
@Component
public class CandleContainer
{
    @PostConstruct
    public void init()
    {
        candlesNumber = strategyProperties.getIntProperty(CANDLES_NUMBER);
        List<Market> allMarkets = marketService.getAllMarkets();

        createWithInitSize(allMarkets.size());
        fillIn(allMarkets);

        candlesByMarketName = Collections.unmodifiableList(candlesByMarketName);
    }

    /**
     * The method edits current open candle. If candle is closed - exception is raised.
     * @param candle the source candle which we use to update the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    public void editCurrentCandle(Candle candle) throws Exception
    {
        edit(candle);
    }

    /**
     * If current candle is open method updates it firstly. After that candle will be closed.
     * @param candle the source candle which we use to close the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    public void updateAndCloseCandle(Candle candle) throws Exception
    {
        CandlesHolder holder = edit(candle);
        holder.candles.getLast().setOpen(false);
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

    private void fillIn(List<Market> allMarkets)
    {
        for(Market market : allMarkets)
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
        throw new IllegalArgumentException(ExceptionMessages.NO_SUCH_CANDLE);
    }

    private void checkIsOpen(Candle candle) throws Exception
    {
        if(!candle.isOpen()) throw new Exception(String.format(ExceptionMessages.CANDLE_IS_ALREADY_CLOSED, candle.getMarketName()));
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
