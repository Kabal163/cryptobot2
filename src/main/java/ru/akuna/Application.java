package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.akuna.dto.Market;
import ru.akuna.logic.MarketService;
import ru.akuna.cuncurrency.StrategyFork;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;
import ru.akuna.tools.properties.ApplicationProperties;

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
    private ApplicationProperties pumpStrategyProperties;

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

            List<Market> marketWrappers = marketService.getAllMarkets();

            log.info("Start Analysis");

            //Для теста перфоманса без форк джоина, можно ориентироваться по логу Finish Analysis
 /*           for (MarketWrapper marketWrapper : marketWrappers)
            {
                marketWrapper.testPerformance();
            }*/

            ForkJoinPool forkJoinPool = new ForkJoinPool(15);
            StrategyFork task = new StrategyFork(marketWrappers);
            forkJoinPool.invoke(task);

            //Пока что в текущем варианте по этому логу нельзя ориентироваться в случае многопоточности, так как главный тред продолжает работать и не ждет форка
            log.info("Finish Analysis");

            System.out.println(pumpStrategyProperties.getProperty("control_points_number"));

            //Нужен, чтобы Main Thread не закончился, пока как WA
            Thread.sleep(100000);
        };
    }
}
