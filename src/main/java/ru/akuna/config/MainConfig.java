package ru.akuna.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.akuna.msg.MessageProvider;
import ru.akuna.msg.impls.MsgProperties;
import ru.akuna.msg.impls.TelegramMessageProvider;

import java.util.Properties;

/**
 * Created by Los Pepes on 12/16/2017.
 */
@Configuration
public class MainConfig
{
    @Bean(name = "TELEGRAM_MSG_PROVIDER")
    @Scope("prototype")
    public MessageProvider messageProvider()
    {
        return new TelegramMessageProvider();
    }

    @Bean(initMethod = "init")
    public MsgProperties msgProperties()
    {
        return new MsgProperties();
    }

    @Bean
    public Properties properties(){
        return new Properties();
    }
}
