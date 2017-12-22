package ru.akuna.task;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akuna.dto.MarketWrapper;

import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;

public class MarketTask extends RecursiveAction
{
    private static final Logger log = LoggerFactory.getLogger(MarketTask.class);

    private List<MarketWrapper> marketWrappers;

    public MarketTask(List<MarketWrapper> marketWrappers)
    {
        this.marketWrappers = marketWrappers;
    }

    @Override
    protected void compute()
    {
        if (marketWrappers.size() >= 2)
        {
            createSubTasks();
        }
        else
        {
            for (MarketWrapper marketWrapper : marketWrappers)
            {
                marketWrapper.testPerformance();
            }
        }
    }

    private void createSubTasks()
    {
        int new_size = marketWrappers.size() / 2;

        List<List<MarketWrapper>> list = Lists.partition(marketWrappers, new_size);

        for (List<MarketWrapper> subList : list)
        {
            new MarketTask(subList).fork();
        }
    }
}
