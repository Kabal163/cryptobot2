package ru.akuna.tools.properties;

import java.util.Properties;

/**
 * Created by Los Pepes on 12/23/2017.
 */
public interface ApplicationProperties
{
    String getProperty(String key);

    int getIntProperty(String key);
}
