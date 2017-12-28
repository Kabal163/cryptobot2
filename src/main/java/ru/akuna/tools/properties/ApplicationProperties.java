package ru.akuna.tools.properties;

/**
 * Created by Los Pepes on 12/23/2017.
 */
public interface ApplicationProperties
{
    String getProperty(String key);

    int getIntProperty(String key);

    long getLongProperty(String key);

    double getDoubleProperty(String key);
}
