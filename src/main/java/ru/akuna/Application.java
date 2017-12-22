package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.akuna.config.MainConfig;
import ru.akuna.dto.Market;
import ru.akuna.logic.MarketService;
import ru.akuna.msg.MessageProvider;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.tools.properties.ApplicationProperties;
import ru.akuna.tools.properties.PumpStrategyProperties;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@SpringBootApplication
public class Application
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ApplicationProperties pumpStrategyProperties;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {

            System.out.println(pumpStrategyProperties.getProperty("control_points_number"));

        };
    }
}
