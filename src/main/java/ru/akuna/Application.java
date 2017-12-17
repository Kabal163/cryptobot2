package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.akuna.logic.MarketService;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@SpringBootApplication
public class Application
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
/*
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        MessageProvider msgProvider = (MessageProvider) context.getBean("TELEGRAM_MSG_PROVIDER");
        msgProvider.sendMessage("Helllllo!");*/
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public MarketService marketService()
    {
        return new MarketService();
    }

    @Bean
    public CommandLineRunner run(MarketService marketService) throws Exception {
        return args -> {
            marketService.createMarketJob(TopMarkets.USDT2BTC.toString()).start();
        };
    }
}
