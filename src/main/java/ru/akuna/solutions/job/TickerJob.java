package ru.akuna.solutions.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.solutions.task.TickerTask;
import ru.akuna.tools.job.JobScheduler;

/**
 * Created by Los Pepes on 12/27/2017.
 * jobCounter - сделан для того, чтобы симулировать продажу монеты.
 * Типо поставили, потом мониторили, и в конце концов продали.
 */
@Component
@Scope("prototype")
public class TickerJob extends CryptoJob
{
    @Override
    public void run()
    {
        if (market != null)
        {
            tickerTask.start(market);
        }
        else
        {
            tickerTask.start(marketName, abovePrice, belowPrice);
        }

        jobCounter++;

/*        if (jobCounter == 10)
        {
            jobScheduler.stopJob(this);
        }*/
    }

    public void setMarket(Market market)
    {
        this.market = market;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
    }


    @Autowired
    private TickerTask tickerTask;

    @Autowired
    private JobScheduler jobScheduler;

    private Market market;
    private String marketName;

    private double abovePrice, belowPrice;

    public double getAbovePrice() {
        return abovePrice;
    }

    public void setAbovePrice(double abovePrice) {
        this.abovePrice = abovePrice;
    }

    public double getBelowPrice() {
        return belowPrice;
    }

    public void setBelowPrice(double belowPrice) {
        this.belowPrice = belowPrice;
    }

    public String getMarketName() {
        return marketName;
    }

    private int jobCounter = 0;
}
