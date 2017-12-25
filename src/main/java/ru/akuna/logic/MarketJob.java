package ru.akuna.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akuna.dto.Market;
import ru.akuna.logic.task.MarketTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;

public class MarketJob
{
    private List<Market> markets;

    public MarketJob(List<Market> markets)
    {
        this.markets = markets;
    }

    public void start()
    {
        log.info("Start Analysis");

        //Для теста перфоманса без форк джоина, остальное ниже закоментить
/*            for (Market market : markets)
            {
                market.testPerformance();
            }*/
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Phaser phaser = new Phaser();
        MarketTask task = new MarketTask(markets, phaser);
        phaser.register();
        forkJoinPool.invoke(task);
        phaser.arriveAndAwaitAdvance();

        log.info("Finish Analysis");
    }

    private static final Logger log = LoggerFactory.getLogger(MarketJob.class);
}
