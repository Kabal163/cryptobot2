package ru.akuna.tools.properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Los Pepes on 12/23/2017.
 */
@Component
@Qualifier("StrategyProperties")
public class StrategyProperties extends AbstractApplicationProperties
{
    @Override
    @PostConstruct
    public void init()
    {
        setPath(PATH);
        setFileName(FILE_NAME);
        super.init();
    }

    private static final String PATH = "config/properties/";
    private static final String FILE_NAME = "strategy.properties";
}
