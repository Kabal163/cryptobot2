package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.akuna.logic.MarketService;
import ru.akuna.tools.properties.ApplicationProperties;

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

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {

            marketService.createMarketJob().start();

            System.out.println(pumpStrategyProperties.getProperty("control_points_number"));
        };
    }
}
