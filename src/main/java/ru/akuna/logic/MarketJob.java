package ru.akuna.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.logic.task.MarketTask;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;

/*
* Джоба теперь вызывается только после того, как мы получили результаты с биржи
* За это отвечает MarketServiceAspect
*/
@Component
public class MarketJob
{
    public void start()
    {
        log.info("Start Analysis");

        this.markets = marketService.getAllMarkets();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Phaser phaser = new Phaser();
        MarketTask task = new MarketTask(markets, phaser);
        phaser.register();
        forkJoinPool.invoke(task);
        phaser.arriveAndAwaitAdvance();

        log.info("Finish Analysis");
    }

    @Autowired
    private MarketService marketService;
    private List<Market> markets;
    private static final Logger log = LoggerFactory.getLogger(MarketJob.class);
}
