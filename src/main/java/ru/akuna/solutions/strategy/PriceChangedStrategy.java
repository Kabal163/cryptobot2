package ru.akuna.solutions.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.entities.Candle;
import ru.akuna.entities.CandleManager;
import ru.akuna.providers.msg.impls.TelegramMessageProvider;
import ru.akuna.publishing.listeners.CandleCloseEventListener;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;
import ru.akuna.tools.properties.StrategyProperties;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.function.BiFunction;


@Component
public class PriceChangedStrategy
{
    @PostConstruct
    public void init()
    {
        priceChangedPercent = strategyProperties.getDoubleProperty(PRICE_CHANGED_IN_PERCENT);
    }

    public void run(String marketName)
    {
        Double firstCandleMaxPrice = getFirstCandleMaxPrice(marketName);
        Double lastCandleMaxPrice = getLastCandleMaxPrice(marketName);

        if (isPriceIncreased(firstCandleMaxPrice, lastCandleMaxPrice))
        {
            telegramMessageProvider.sendMessage(MessageFormat.format(WARNING_MESSAGE_FOR_PRICE_CHANGE, marketName, "increased", priceChangedPercent, textTools.removeExhibitor(firstCandleMaxPrice), textTools.removeExhibitor(lastCandleMaxPrice)));
        }
        else if (isPriceDecreased(firstCandleMaxPrice, lastCandleMaxPrice))
        {
            telegramMessageProvider.sendMessage(MessageFormat.format(WARNING_MESSAGE_FOR_PRICE_CHANGE, marketName, "decreased", priceChangedPercent, textTools.removeExhibitor(firstCandleMaxPrice), textTools.removeExhibitor(lastCandleMaxPrice)));
        }
    }

    private boolean isPriceIncreased(Double firstPrice, Double lastPrice)
    {
        return comparePriceByFunction(firstPrice, lastPrice, (a, b) ->  b > getPriceWithPercent(a));
    }

    private boolean isPriceDecreased(Double firstPrice, Double lastPrice)
    {
        return comparePriceByFunction(firstPrice, lastPrice, (a, b) ->  a > getPriceWithPercent(b));
    }

    private Double getPriceWithPercent(Double price)
    {
        return mathTools.getPriceWithPercent(price, priceChangedPercent);
    }

    private Double getFirstCandleMaxPrice(String marketName)
    {
        Candle firstCandle = candleManager.getFirstClosedCandle(marketName);

        return firstCandle.getMaxPrice();
    }

    private Double getLastCandleMaxPrice(String marketName)
    {
        Candle lastCandle = candleManager.getLastClosedCandle(marketName);

        return lastCandle.getMaxPrice();
    }

    private boolean comparePriceByFunction(Double maxPrice1, Double maxPrice2, BiFunction<Double, Double, Boolean> function)
    {
        return function.apply(maxPrice1,  maxPrice2);
    }

    private static final Logger LOG = LoggerFactory.getLogger(CandleCloseEventListener.class);

    @Autowired
    private CandleManager candleManager;

    @Autowired
    private MathTools mathTools;

    @Autowired
    private TelegramMessageProvider telegramMessageProvider;

    @Autowired
    private TextTools textTools;

    @Autowired
    private StrategyProperties strategyProperties;

    private String PRICE_CHANGED_IN_PERCENT = "price_changed_in_percent";
    private String WARNING_MESSAGE_FOR_PRICE_CHANGE = "Warning: {0} price {1} by {2}%. Old price: {3}. New price: {4}.";
    private static Double priceChangedPercent;
}
