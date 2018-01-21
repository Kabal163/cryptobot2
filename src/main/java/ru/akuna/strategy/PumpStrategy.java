package ru.akuna.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.strategy.job.MarketJob;
import ru.akuna.tools.job.JobScheduler;
import ru.akuna.tools.properties.ApplicationProperties;

/**
 * Created by Los Pepes on 12/27/2017.
 * Далее здесь, скедулится джоба. Для этого используется кастомный класс JobScheduler
 * Джоба начинает выполняться
 */


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
