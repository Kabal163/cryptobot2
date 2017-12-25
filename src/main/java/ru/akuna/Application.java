package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.akuna.logic.MarketJob;
import ru.akuna.logic.MarketService;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.properties.ApplicationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@SpringBootApplication
public class Application
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static Map<String, Double> market2ask = new HashMap<>();
    public static Map<String, Double> market2bid = new HashMap<>();
    public static Map<String, Double> market2last = new HashMap<>();

    @Autowired
    private ApplicationProperties pumpStrategyProperties;

    @Autowired
    private MathTools mathTools;

    @Autowired
    private MarketService marketService;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {

            MarketJob marketJob = marketService.createMarketJob();

            while (true)
            {
                marketJob.start();
                Thread.sleep(30000);
            }

         /*   System.out.println(pumpStrategyProperties.getProperty("control_points_number"));*/
        };
    }
}
