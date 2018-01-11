package ru.akuna.strategy.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.strategy.task.TickerTask;
import ru.akuna.tools.JobScheduler;

/**
 * Created by Los Pepes on 12/27/2017.
 * jobCounter - сделан для того, чтобы симулировать продажу монеты.
 * Типо поставили, потом мониторили, и в конце концов продали.
 */
@Component
@Scope("prototype")
public class TickerJob implements Runnable
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
            tickerTask.start(marketString, abovePrice, belowPrice);
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

    public void setMarketString(String marketString)
    {
        this.marketString = marketString;
    }


    @Autowired
    private TickerTask tickerTask;

    @Autowired
    private JobScheduler jobScheduler;

    private Market market;
    private String marketString;

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

    private int jobCounter = 0;
}
