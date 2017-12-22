package ru.akuna.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import ru.akuna.logic.MarketService;
import ru.akuna.msg.MessageProvider;
import ru.akuna.tools.properties.MsgProperties;
import ru.akuna.msg.impls.TelegramMessageProvider;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Los Pepes on 12/16/2017.
 */
@Configuration
public class MainConfig
{
    @Bean
    @Scope("prototype")
    public Properties properties(){
        return new Properties();
    }

    @Bean
    public MarketService marketService()
    {
        return new MarketService();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }   

    @Bean
    public TextTools textTools()
    {
        return new TextTools();
    }


    @Bean
    public MathTools mathTools()
    {
        return new MathTools();
    }

    @Bean
    public ApplicationContextProvider applicationContextProvider()
    {
        return new ApplicationContextProvider();
    }

    @Bean
    public ExecutorService executorService()
    {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("SomeName of Thread") //todo implement thread naming
                .setDaemon(true)
                .build();
        return Executors.newFixedThreadPool(48, threadFactory);
    }
}
