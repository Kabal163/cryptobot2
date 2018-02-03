package ru.akuna.solutions.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.dto.Ticker;
import ru.akuna.providers.msg.impls.TelegramMessageProvider;
import ru.akuna.solutions.stockService.TickerService;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;

/**
 * Created by Los Pepes on 12/27/2017.
 */
@Component
@Scope("prototype")
public class TickerTask
{
    public String start(Market market)
    {
        Ticker ticker = tickerService.getTicker(market.getMarketName());

        if (isFirstTime)
        {
            lastPrice = market.getLast();
        }

        String diff = "Price increased by " + getDiffInPercentages(lastPrice, ticker.getLast());
        String result = diff + "\n" + taskCounter++ + ". TICKER: ASK = " + ticker.getAsk()
                + ", BID = " + ticker.getBid()
                + ", LAST = " + ticker.getLast();
        lastPrice = ticker.getLast();
        isFirstTime = false;

        LOG.info(result);
        return result;
    }


    public String start(String marketString, double abovePrice, double belowPrice)
    {
        Ticker ticker = tickerService.getTicker(marketString);

        if (isFirstTime)
        {
            lastPrice = ticker.getLast();
        }

        String diffInPercent = String.valueOf(mathTools.roundToSpecifiedNumber(getDiffInPercentages(lastPrice, ticker.getLast()), 100d));
        String diff = "Price changed by " + textTools.removeExhibitor(mathTools.roundToSpecifiedNumber(getDiff(lastPrice, ticker.getLast()), 100000000d)) + ". Percent: " + diffInPercent;

        String result = "\n" + diff + "\n" + taskCounter++ + ". TICKER: ASK = " + textTools.removeExhibitor(ticker.getAsk())
                + ", BID = " + textTools.removeExhibitor(ticker.getBid())
                + ", LAST = " + textTools.removeExhibitor(ticker.getLast());
        lastPrice = ticker.getLast();
        isFirstTime = false;

        /*System.out.println(result);*/

        if (lastPrice >= abovePrice)
        {
            telegramMessageProvider.sendMessage("Warning: " + marketString + " price is above : " + textTools.removeExhibitor(abovePrice) + ". \n Current Price: " + textTools.removeExhibitor(lastPrice) + "\n"
            + LINK_TO_MARKET + marketString);
        }
        else if (lastPrice <= belowPrice)
        {
            telegramMessageProvider.sendMessage("Warning: " + marketString + " price is below: " + textTools.removeExhibitor(belowPrice) + ". \n Current Price: " + textTools.removeExhibitor(lastPrice)+ "\n"
            + LINK_TO_MARKET + marketString);
        }

        return result;
    }


    private double getDiffInPercentages(double lastPrice, double newPrice)
    {
        return (newPrice - lastPrice) / lastPrice * 100;
    }

    private double getDiff(double lastPrice, double newPrice)
    {
        return newPrice - lastPrice;
    }

    @Autowired
    private TickerService tickerService;

    @Autowired
    private TextTools textTools;

    @Autowired
    private MathTools mathTools;

    @Autowired
    private TelegramMessageProvider telegramMessageProvider;

    private static final Logger LOG = LoggerFactory.getLogger(TickerTask.class);
    private double lastPrice;
    private boolean isFirstTime = true;
    private int taskCounter = 0;
    private String LINK_TO_MARKET = "https://bittrex.com/Market/Index?MarketName=";
}