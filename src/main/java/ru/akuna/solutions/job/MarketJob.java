package ru.akuna.solutions.job;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.solutions.stockService.MarketService;
import ru.akuna.solutions.task.CandleManagementTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;
import java.util.concurrent.RecursiveAction;

/*
* Джоба создает таску. Далее таска форкается.
*/

@Component
@Scope("prototype")
public class MarketJob extends CryptoJob
{
    @Override
    public void run()
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Phaser phaser = new Phaser();
        MarketAction action = new MarketAction(marketService.getAllMarkets(), phaser);
        phaser.register();
        forkJoinPool.invoke(action);
        phaser.arriveAndAwaitAdvance();
    }

    @Autowired
    private MarketService marketService;


    class MarketAction extends RecursiveAction
    {
        MarketAction(List<Market> markets, Phaser phaser)
        {
            this.markets = markets;
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        protected void compute()
        {
            if (markets.size() > 2)
            {
                createSubTasks();
            }
            else
            {
                for (Market market : markets)
                {
                    start(market);
                }
            }

            phaser.arrive();
        }

        private void createSubTasks()
        {
            int new_size = markets.size() / 2;

            List<List<Market>> subLists = Lists.partition(markets, new_size);

            for (List<Market> subList : subLists)
            {
                MarketAction task = new MarketAction(subList, phaser);
                task.fork();
            }
        }

        private void start(Market market)
        {
            //Старое поведение
            //new OldMarketTask(market).start();
            candleManagementTask.run(market);

            //Здесь будет идти сначала generic таска по созданию свечей, а затем запуск стратегий по этим свечам в for-each цикле
        }

        private List<Market> markets;
        private Phaser phaser;
    }

    @Autowired
    private CandleManagementTask candleManagementTask;
}


