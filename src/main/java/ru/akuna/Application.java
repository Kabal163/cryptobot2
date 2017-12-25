package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.akuna.logic.MarketJob;
import ru.akuna.logic.MarketService;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.properties.ApplicationProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class Application
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private MarketService marketService;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {

            /*MarketJob marketJob = marketService.createMarketJob();

            while (true)
            {
                marketJob.start();
                Thread.sleep(30000);
            }*/

         /*   System.out.println(pumpStrategyProperties.getProperty("control_points_number"));*/
        };
    }
}
