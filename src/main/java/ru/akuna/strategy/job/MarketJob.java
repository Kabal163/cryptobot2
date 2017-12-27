package ru.akuna.strategy.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.strategy.stockService.MarketService;
import ru.akuna.strategy.task.MarketTask;
import ru.akuna.tools.JobScheduler;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;

/*
* Джоба создает таску. Далее таска форкается.
*/

@Component
@Scope("prototype")
public class MarketJob implements Runnable
{
    @Override
    public void run()
    {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Phaser phaser = new Phaser();
        MarketTask task = new MarketTask(marketService.getAllMarkets(), phaser);
        phaser.register();
        forkJoinPool.invoke(task);
        phaser.arriveAndAwaitAdvance();
    }

    @Autowired
    private MarketService marketService;
}
