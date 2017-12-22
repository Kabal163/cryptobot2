package ru.akuna.task;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akuna.dto.Market;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MarketTask extends RecursiveAction
{
    private static final Logger log = LoggerFactory.getLogger(MarketTask.class);

    private List<Market> markets;

    public MarketTask(List<Market> markets)
    {
        this.markets = markets;
    }

    @Override
    protected void compute()
    {
        if (markets.size() >= 2)
        {
            createSubTasks();
        }
        else
        {
            for (Market market : markets)
            {
                market.testPerformance();
            }
        }
    }

    private void createSubTasks()
    {
        int new_size = markets.size() / 2;

        List<List<Market>> subLists = Lists.partition(markets, new_size);

        for (List<Market> subList : subLists)
        {
            new MarketTask(subList).fork();
        }
    }
}
