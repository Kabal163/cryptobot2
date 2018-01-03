package ru.akuna.tools.properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Qualifier("DemoAccountProperties")
public class DemoAccountProperties extends AbstractApplicationProperties
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
    private static final String FILE_NAME = "DemoAccount.properties";
}
