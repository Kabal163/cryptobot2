package ru.akuna.tools.properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Qualifier("FollowCoinsProperties")
public class FollowCoinsProperties extends JsonApplicationProperties
{
    @PostConstruct
    public void init() throws IOException
    {
        setPath(PATH);
        setFileName(FILE_NAME);
    }

    private static final String PATH = "config/properties/";
    private static final String FILE_NAME = "FollowCoins.properties";
}




