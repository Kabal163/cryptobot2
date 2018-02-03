package ru.akuna.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.strategy.job.MarketJob;
import ru.akuna.tools.job.JobScheduler;
import ru.akuna.tools.properties.ApplicationProperties;

@Component
public class PumpStrategy
{
    public void run()
    {
        long timeRate = pumpStrategyProperties.getLongProperty("market_job_time_rate");
        jobScheduler.scheduleAtFixedRate(marketJob, timeRate);
    }

    @Autowired
    private MarketJob marketJob;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private ApplicationProperties pumpStrategyProperties;
}
