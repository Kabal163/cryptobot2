package ru.akuna.strategy.task;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.akuna.dto.Market;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.strategy.job.TickerJob;
import ru.akuna.tools.JobScheduler;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;
import ru.akuna.tools.properties.ApplicationProperties;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Phaser;
import java.util.concurrent.RecursiveAction;

public class MarketTask extends RecursiveAction
{
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
                start(market);
            }
        }

        phaser.arrive();
    }

    private void start(Market market)
    {
        String marketName = market.getMarketName();
        Double lastPrice = getResultValue(marketName);
        Double currentPrice = market.getLast();

        if (lastPrice != null)
        {
            if (isPriceGetBiggerOnPercent(lastPrice, currentPrice, 2.0))
            {
                sendMessage(marketName, lastPrice, currentPrice);
                runTickerJob(market);
            }
        }

        addResult(marketName, currentPrice);
    }

    //В случае, если мы находим памп, то скедулим новую джобу, которая уже мониторит конкретную монету
    private void runTickerJob(Market market)
    {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        JobScheduler jobScheduler = context.getBean("jobScheduler", JobScheduler.class);
        TickerJob tickerJob = context.getBean("tickerJob", TickerJob.class);
        ApplicationProperties properties = context.getBean("pumpStrategyProperties", ApplicationProperties.class);
        tickerJob.setMarket(market);
        jobScheduler.scheduleAtFixedRate(tickerJob, properties.getLongProperty("ticker_job_time_rate"));
    }

    private void sendMessage(String marketName, Double oldLast, Double currentLastPrice)
    {
        double percent = (currentLastPrice - oldLast) / oldLast * 100;

        /*synchronized (context)
        {
            MessageProvider telegramMessageProvider = (MessageProvider) context.getBean("telegramMessageProvider");
            telegramMessageProvider.sendMessage("BittrexMarket: " + marketName + " has increased price by: " + percent + "%\n" +
                    "Old price: " + textTools.removeExhibitor(oldLast) + "\n" +
                    "New price: " + textTools.removeExhibitor(currentLastPrice));
        }*/

        LOG.info("BittrexMarket: " + marketName + " has increased price by: " + percent + "%\n" +
                "Old price: " + textTools.removeExhibitor(oldLast) + "\n" +
                "New price: " + textTools.removeExhibitor(currentLastPrice));
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

    private static final Logger LOG = LoggerFactory.getLogger(MarketTask.class);
    private static MathTools mathTools = new MathTools();
    private static TextTools textTools = new TextTools();
    private static Map<String, Double> market2last = new IdentityHashMap<>();

    private List<Market> markets;
    private Phaser phaser;

}
