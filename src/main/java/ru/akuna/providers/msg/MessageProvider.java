package ru.akuna.providers.msg;

/**
 * Created by Los Pepes on 12/16/2017.
 * MessageProvider provides with opportunity to send messages to a destination
 * The destination depends on the implementation
 */
public interface MessageProvider
{
    /**
     * @param message - text that you want to send to a destination
     */
    boolean sendMessage(String message);
}
