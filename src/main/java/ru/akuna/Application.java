package ru.akuna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.akuna.dao.BittrexMarket;
import ru.akuna.dao.MarketRepository;
import ru.akuna.demo.DemoAccount;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.strategy.PumpStrategy;
import ru.akuna.strategy.job.TickerJob;
import ru.akuna.tools.job.JobScheduler;
import ru.akuna.tools.job.TickerJobScheduler;
import ru.akuna.tools.properties.ApplicationProperties;

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
            runTickerJobs();
        };
    }

    private void runTickerJobs()
    {
        for (BittrexMarket market : marketRepository.findAll())
        {
            tickerJobScheduler.createAndScheduleTickerJob(market);
        }
    }

    @Autowired
    private PumpStrategy pumpStrategy;

    @Autowired
    private DemoAccount demoAccount;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private TickerJobScheduler tickerJobScheduler;
}
