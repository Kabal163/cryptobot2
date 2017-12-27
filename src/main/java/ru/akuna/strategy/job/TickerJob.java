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
        String ticker = tickerTask.start(market);
        jobCounter++;
        if(jobCounter == 10)
        {
            jobScheduler.stopJob(this);
        }
    }

    public void setMarket(Market market)
    {
        this.market = market;
    }


    @Autowired
    private TickerTask tickerTask;
    @Autowired
    private JobScheduler jobScheduler;
    private Market market;
    private int jobCounter = 0;
}
