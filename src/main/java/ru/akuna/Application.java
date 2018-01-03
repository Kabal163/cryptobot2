package ru.akuna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.akuna.demo.DemoAccount;
import ru.akuna.strategy.PumpStrategy;

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
            //Первым делом вызывается стратегия
       /*     pumpStrategy.run();*/

            demoAccount.deposit("LTC", 42.3);
            demoAccount.deposit("DOGE", 1263000.0);
            demoAccount.withdraw("LTC", 15.0);

            System.out.println(demoAccount.getTotalBalanceInBtc());
            System.out.println(demoAccount.getChangeOfBalance());
            System.out.println(demoAccount.getChangeOfBalanceInPercent());
        };
    }

    @Autowired
    private PumpStrategy pumpStrategy;

    @Autowired
    private DemoAccount demoAccount;
}
