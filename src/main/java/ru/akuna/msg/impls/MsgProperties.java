package ru.akuna.msg.impls;

import org.springframework.beans.factory.annotation.Autowired;
import ru.akuna.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by Los Pepes on 12/17/2017.
 */
public class MsgProperties
{
    @Autowired
    private Properties properties;

    public void init()
    {
        String path = "";
        URL url = getClass().getClassLoader().getResource("msg.properties");
        if(url != null) path = url.getFile().replaceAll("%20", " ");

        try(FileInputStream fis = new FileInputStream(path))
        {
            properties.load(fis);
        }
        catch (IOException e)
        {
            tryToFind();
        }
    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    private void tryToFind()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource("config").getFile().replaceAll("%20", " ");
        File rootConfig = new File(path);
        findRecursively(rootConfig);
    }

    private void findRecursively(File folder)
    {
        List<File> files = new ArrayList<>();
        List<File> folders = Arrays.stream(folder.listFiles())
                                    .filter(File::isDirectory)
                                    .collect(Collectors.toList());;

        if(folders.size() > 0 && properties.isEmpty())
        {
            for(File f : folders)
            {
                findRecursively(f);
            }
        }
        else
        {
            files = Arrays.stream(folder.listFiles())
                                .filter(f -> f.getName().equals("msg.properties"))
                                .collect(Collectors.toList());
        }

        if(files.size() == 1)
        {
            File msgConfig = files.get(0);
            try(FileInputStream fis = new FileInputStream(msgConfig.getPath()))
            {
                properties.load(fis);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

}