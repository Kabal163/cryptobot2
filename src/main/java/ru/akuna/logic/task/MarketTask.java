package ru.akuna.logic.task;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.akuna.dto.Market;
import ru.akuna.msg.MessageProvider;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Phaser;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.Lock;


public class MarketTask extends RecursiveAction
{
    private static final Logger log = LoggerFactory.getLogger(MarketTask.class);
    private static MathTools mathTools = new MathTools();
    private static TextTools textTools = new TextTools();
    private static Map<String, Double> market2last = new HashMap<>();

    private List<Market> markets;
    private Phaser phaser;

    public MarketTask(List<Market> markets, Phaser phaser)
    {
        this.markets = markets;
        this.phaser = phaser;
        phaser.register();
    }

    @Override
    protected void compute()
    {
        if (markets.size() > 2)
        {
            createSubTasks();
        }
        else
        {
            for (Market market : markets)
            {
                doSomeLogic(market);
            }
        }

        phaser.arrive();
    }

    private void doSomeLogic(Market market)
    {
        // Test strategy
        String marketName = market.getMarketName();
        Double oldLastPrice = getResultValue(marketName);
        Double currentLastPrice = market.getLast();

        if (oldLastPrice != null)
        {
            if (isPriceGetBiggerOnPercent(oldLastPrice, currentLastPrice, 5.0))
            {
                sendMessage(marketName, oldLastPrice, currentLastPrice);
            }
        }

        addResult(marketName, currentLastPrice);
    }

    private void sendMessage(String marketName, Double oldLast, Double currentLastPrice)
    {
        double percent = (currentLastPrice - oldLast) / oldLast * 100;

        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        synchronized (context)
        {
            MessageProvider telegramMessageProvider = (MessageProvider) context.getBean("telegramMessageProvider");
            telegramMessageProvider.sendMessage("Market: " + marketName + " has increased price by: " + percent + "%\n" +
                    "Old price: " + textTools.removeExhibitor(oldLast) + "\n" +
                    "New price: " + textTools.removeExhibitor(currentLastPrice));
        }
    }

    private boolean isPriceGetBiggerOnPercent(Double oldLastPrice, Double currentLastPrice, Double percent)
    {
        double priceWithPercent = mathTools.getPriceWithPercent(oldLastPrice, percent);

        return currentLastPrice >= priceWithPercent;
    }

    private void createSubTasks()
    {
        int new_size = markets.size() / 2;

        List<List<Market>> subLists = Lists.partition(markets, new_size);

        for (List<Market> subList : subLists)
        {
            MarketTask task = new MarketTask(subList, phaser);
            task.fork();
        }
    }

    private Double getResultValue(String marketName)
    {
        synchronized (market2last)
        {
            return market2last.get(marketName);
        }
    }

    private void addResult(String marketName, Double currency)
    {
        synchronized (market2last)
        {
            market2last.put(marketName, currency);
        }
    }

}
