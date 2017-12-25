package ru.akuna.cuncurrency;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akuna.dto.Market;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class StrategyFork extends RecursiveAction
{
    public StrategyFork(List<Market> markets)
    {
        this.markets = markets;
    }

    @Override
    protected void compute()
    {
        if (markets.size() >= 4)
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
            new StrategyFork(subList).fork();
        }
    }

    private static final Logger log = LoggerFactory.getLogger(StrategyFork.class);
    private List<Market> markets;
}
