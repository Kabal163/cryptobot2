package ru.akuna.msg.impls;

import org.springframework.beans.factory.annotation.Autowired;
import ru.akuna.msg.MessageProvider;

/**
 * Created by Los Pepes on 12/16/2017.
 */

public class TelegramMessageProvider implements MessageProvider
{
    @Autowired
    private MsgProperties properties;

    @Override
    public boolean sendMessage(String message)
    {
        System.out.println(properties.getProperty("msg.url.telegram") + message);
        return false;
    }

}
