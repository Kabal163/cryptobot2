package ru.akuna.msg.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ru.akuna.msg.MessageProvider;
import ru.akuna.msg.impls.MsgProperties;

/**
 * Created by Los Pepes on 12/16/2017.
 */

public class TelegramMessageProvider implements MessageProvider
{
    @Autowired
    private MsgProperties properties;

    @Autowired
    private RestTemplate restTemplate;

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

}
