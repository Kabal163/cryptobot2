package ru.akuna.msg.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.akuna.msg.MessageProvider;
import ru.akuna.tools.properties.ApplicationProperties;
import ru.akuna.tools.properties.MsgProperties;

import javax.annotation.Resource;

/**
 * Created by Los Pepes on 12/16/2017.
 */
@Component
@Scope("prototype")
@EnableAsync
public class TelegramMessageProvider implements MessageProvider
{
    @Override
    public boolean sendMessage(String message)
    {
        String token = msgProperties.getProperty("msg.telegram.token");
        String chat_id = msgProperties.getProperty("msg.telegram.chat_id");
        String url = msgProperties.getProperty("msg.telegram.url");
        String method = "/sendMessage?";
        String param1 = "chat_id=";
        String param2 = "&text=";
        String path = url + token + method + param1 + chat_id + param2 + message;

        restTemplate.getForObject(path, String.class);
        return false;
    }

    @Autowired
    private ApplicationProperties msgProperties;

    @Autowired
    private RestTemplate restTemplate;
}
