package ru.akuna.tools.properties;

import org.springframework.beans.factory.annotation.Autowired;

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
 * Created by Los Pepes on 12/23/2017.
 */
public abstract class AbstractApplicationProperties implements ApplicationProperties
{
    public void init()
    {
        URL url = getClass().getClassLoader().getResource(path + fileName);
        if(url != null) file = new File(url.getFile().replaceAll("%20", " "));

        try(FileInputStream fis = new FileInputStream(file.getPath()))
        {
            properties.load(fis);
        }
        catch (IOException | NullPointerException e)
        {
            tryToFind();
        }
    }

    public String getProperty(String key)
    {
        return properties.getProperty(key);
    }

    public int getIntProperty(String key)
    {
        return Integer.parseInt(properties.getProperty(key));
    }

    public long getLongProperty(String key)
    {
        return Long.parseLong(properties.getProperty(key));
    }

    private void tryToFind()
    {
        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(ROOT_CONFIG_FOLDER).getFile().replaceAll("%20", " ");
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
                    .filter(f -> f.getName().equals(fileName))
                    .collect(Collectors.toList());
        }

        if(files.size() == 1)
        {
            File msgConfig = files.get(0);
            file = new File(msgConfig.getPath());
            try(FileInputStream fis = new FileInputStream(file.getPath()))
            {
                properties.load(fis);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    protected void setPath(String path)
    {
        this.path = path;
    }

    @Autowired
    private Properties properties;
    private File file;
    private String fileName;
    private String path;
    private static final String ROOT_CONFIG_FOLDER = "config";
}
