package ru.akuna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.akuna.demo.DemoAccount;
import ru.akuna.dto.Market;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.strategy.PumpStrategy;
import ru.akuna.strategy.job.TickerJob;
import ru.akuna.tools.JobScheduler;
import ru.akuna.tools.properties.ApplicationProperties;
import ru.akuna.tools.properties.FollowCoinsProperties;
import ru.akuna.tools.properties.JsonApplicationProperties;

import java.util.List;

/**
 * Created by Los Pepes on 12/9/2017.
 */
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner run() throws Exception {
        return args -> {
            ApplicationContext context = ApplicationContextProvider.getApplicationContext();
            FollowCoinsProperties properties = context.getBean("followCoinsProperties", FollowCoinsProperties.class);
            properties.init();

            List<Market> markets = properties.getMarketsFromProperty();

            for (Market market : markets)
            {
                /*runTickerJob(market.getMarketName(), market.getAbovePrice(), market.getBelowPrice());*/
            }
        };
    }

    private void runTickerJob(String market, double abovePrice, double belowPrice)
    {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        JobScheduler jobScheduler = context.getBean("jobScheduler", JobScheduler.class);
        TickerJob tickerJob = context.getBean("tickerJob", TickerJob.class);
        ApplicationProperties properties = context.getBean("pumpStrategyProperties", ApplicationProperties.class);

        tickerJob.setMarketString(market);
        tickerJob.setAbovePrice(abovePrice);
        tickerJob.setBelowPrice(belowPrice);

        jobScheduler.scheduleAtFixedRate(tickerJob, properties.getLongProperty("ticker_job_time_rate"));
    }

    @Autowired
    private PumpStrategy pumpStrategy;

    @Autowired
    private DemoAccount demoAccount;
}
