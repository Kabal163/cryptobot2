package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.akuna.dto.MarketWrapper;
import ru.akuna.logic.MarketService;
import ru.akuna.msg.MessageProvider;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.task.MarketTask;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@SpringBootApplication
public class Application
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private MarketService marketService;

    @Autowired
    private TextTools textTools;

    @Autowired
    private MathTools mathTools;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {
            sayHelllllo();

/*            marketService.createMarketJob(TopMarkets.USDT2BTC.toString()).start();*/

            List<MarketWrapper> marketWrappers = marketService.getAllMarkets();

            log.info("Start Analysis");

            //Для теста перфоманса без форк джоина, можно ориентироваться по Finish Analysis
 /*           for (MarketWrapper marketWrapper : marketWrappers)
            {
                marketWrapper.testPerformance();
            }*/


            ForkJoinPool forkJoinPool = new ForkJoinPool(10);
            MarketTask task = new MarketTask(marketWrappers);
            forkJoinPool.invoke(task);


            //Пока что в текущем варианте по этому логу нельзя ориентироваться в случае многопоточности, так как главный тред продолжает работать и не ждет форка
            log.info("Finish Analysis");

            //Нужен, чтобы Main Thread не закончился, пока как WA
            Thread.sleep(100000);
        };
    }

    private void sayHelllllo()
    {
        MessageProvider msgProvider = (MessageProvider) ApplicationContextProvider.getApplicationContext().getBean("TELEGRAM_MSG_PROVIDER");
        msgProvider.sendMessage("Helllllo!");
    }
}
