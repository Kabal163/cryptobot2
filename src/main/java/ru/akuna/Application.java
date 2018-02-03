package ru.akuna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.akuna.entities.BittrexMarket;
import ru.akuna.dao.MarketRepository;
import ru.akuna.demo.DemoAccount;
import ru.akuna.solutions.job.MarketJob;
import ru.akuna.tools.job.JobScheduler;
import ru.akuna.tools.job.TickerJobScheduler;
import ru.akuna.tools.properties.AbstractApplicationProperties;

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
    public CommandLineRunner run() throws Exception
    {
        return args -> {
           /* runTickerJobs();*/
            runMarketJob();
        };
    }

    private void runMarketJob()
    {
        long timeRate = strategyProperties.getLongProperty("market_job_time_rate");
        jobScheduler.scheduleAtFixedRate(marketJob, timeRate);
    }

    private void runTickerJobs()
    {
        for (BittrexMarket market : marketRepository.findAll())
        {
            tickerJobScheduler.createAndScheduleTickerJob(market);
        }
    }

    @Autowired
    private DemoAccount demoAccount;

    @Autowired
    private MarketRepository marketRepository;

    @Autowired
    private TickerJobScheduler tickerJobScheduler;

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private AbstractApplicationProperties strategyProperties;

    @Autowired
    private MarketJob marketJob;
}
