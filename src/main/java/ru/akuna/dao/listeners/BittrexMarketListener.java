package ru.akuna.dao.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.akuna.dao.BittrexMarket;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.strategy.job.TickerJob;
import ru.akuna.tools.job.JobScheduler;
import ru.akuna.tools.job.TickerJobScheduler;
import ru.akuna.tools.properties.ApplicationProperties;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class BittrexMarketListener
{
    @PostPersist
    private void doAfterInsertMarket(BittrexMarket market)
    {
        tickerJobScheduler.createAndScheduleTickerJob(market);
    }

    @PostUpdate
    private void doAfterUpdateMarket(BittrexMarket market)
    {
        tickerJobScheduler.updateTickerJob(market);
    }

    @PostRemove
    private void doAfterRemoveMarket(BittrexMarket market)
    {
        tickerJobScheduler.stopJobById(market.getId());
    }

    @Autowired
    public void init(TickerJobScheduler tickerJobScheduler)
    {
        BittrexMarketListener.tickerJobScheduler = tickerJobScheduler;
    }

    private static TickerJobScheduler tickerJobScheduler;
}
