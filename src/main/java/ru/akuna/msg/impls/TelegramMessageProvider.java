package ru.akuna.msg.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
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
@Qualifier("TelegramMessageProvider")
public class TelegramMessageProvider implements MessageProvider
{
    @Override
    public boolean sendMessage(String message)
    {
        String token = properties.getProperty("msg.telegram.token");
        String chat_id = properties.getProperty("msg.telegram.chat_id");
        String url = properties.getProperty("msg.telegram.url");
        String method = "/sendMessage?";
        String param1 = "chat_id=";
        String param2 = "&text=";
        String path = url + token + method + param1 + chat_id + param2 + message;

        restTemplate.getForObject(path, String.class);
        return false;
    }

    @Resource
    @Qualifier("MsgProperties")
    private ApplicationProperties properties;

    @Autowired
    private RestTemplate restTemplate;
}
