package ru.akuna.solutions.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import ru.akuna.dto.Market;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.solutions.job.TickerJob;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;
import ru.akuna.tools.job.JobScheduler;
import ru.akuna.tools.properties.ApplicationProperties;

import java.util.IdentityHashMap;
import java.util.Map;

@Deprecated
public class OldMarketTask
{
    private Market market;

    public OldMarketTask(Market market)
    {
        this.market = market;
    }

    public void start()
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
        ApplicationProperties properties = context.getBean("strategyProperties", ApplicationProperties.class);
        tickerJob.setMarket(market);
        jobScheduler.scheduleAtFixedRate(tickerJob, properties.getLongProperty("ticker_job_time_rate"));
    }

    private void sendMessage(String marketName, Double oldLast, Double currentLastPrice)
    {
        double percent = (currentLastPrice - oldLast) / oldLast * 100;

/*        synchronized (context)
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

    private final Logger LOG = LoggerFactory.getLogger(OldMarketTask.class);

    private MathTools mathTools = new MathTools();
    private TextTools textTools = new TextTools();
    private Map<String, Double> market2last = new IdentityHashMap<>();
}
