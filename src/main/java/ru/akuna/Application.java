package ru.akuna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import ru.akuna.models.Tickers;

import static ru.akuna.BittrexUrls.GET_TICKER;

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
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
          /*  Markets markets = restTemplate.getForObject(
                    GET_MARKETS, Markets.class);
            log.info(markets.toString());
        *//*    for (Market market : markets.getMarkets())
            {
                log.info("market: " + market.toString());
            }*//*

            Ticker ticker = restTemplate.getForObject(GET_TICKER,  Ticker.class);
            log.info(ticker.toString());*/

            Object[] possibleValues = TopMarkets.values();



            while(true)
            {
                Tickers tickers = restTemplate.getForObject(GET_TICKER + TopMarkets.BTC2XRP.toString(), Tickers.class);
                log.info(tickers.toString());
            }

        };
    }
}
