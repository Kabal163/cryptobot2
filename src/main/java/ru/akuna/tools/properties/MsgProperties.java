package ru.akuna.tools.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Los Pepes on 12/17/2017.
 */
@Component
@Qualifier("MsgProperties")
public class MsgProperties extends AbstractApplicationProperties
{
    @Override
    public void init()
    {
        setPath(PATH);
        setFileName(FILE_NAME);
        super.init();
    }

    private static final String PATH = "config/properties/";
    private static final String FILE_NAME = "Msg.properties";
}
