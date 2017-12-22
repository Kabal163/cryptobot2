package ru.akuna.tools.properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by Los Pepes on 12/23/2017.
 */
@Component
@Qualifier("PumpStrategyProperties")
public class PumpStrategyProperties extends AbstractApplicationProperties
{
    @Override
    public void init()
    {
        setPath(PATH);
        setFileName(FILE_NAME);
        super.init();
    }

    private static final String PATH = "config/properties/";
    private static final String FILE_NAME = "PumpStrategy.properties";
}
