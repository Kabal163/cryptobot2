package ru.akuna.tools.job;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.akuna.dao.BittrexMarket;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.strategy.job.TickerJob;
import ru.akuna.tools.properties.ApplicationProperties;

@Component
public class TickerJobScheduler extends JobScheduler
{
    public void updateTickerJob(BittrexMarket market)
    {
        TickerJob job = (TickerJob) getJobById(market.getId());

        if (job != null)
        {
            job.setMarketName(market.getMarketName());
            job.setBelowPrice(market.getBelowPrice());
            job.setAbovePrice(market.getAbovePrice());
        }
    }

    public void createAndScheduleTickerJob(BittrexMarket market)
    {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        TickerJob tickerJob = context.getBean("tickerJob", TickerJob.class);
        ApplicationProperties properties = context.getBean("pumpStrategyProperties", ApplicationProperties.class);

        tickerJob.setMarketName(market.getMarketName());
        tickerJob.setAbovePrice(market.getAbovePrice());
        tickerJob.setBelowPrice(market.getBelowPrice());
        tickerJob.setId(market.getId());

        scheduleAtFixedRate(tickerJob, properties.getLongProperty("ticker_job_time_rate"));
    }
}
