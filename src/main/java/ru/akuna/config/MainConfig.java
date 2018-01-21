package ru.akuna.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.tools.MathTools;
import ru.akuna.tools.TextTools;

import java.util.Properties;
import java.util.concurrent.*;

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

    @Bean(destroyMethod="shutdown")
    public Executor scheduledExecutorService()
    {
        return Executors.newScheduledThreadPool(10);
    }
}
