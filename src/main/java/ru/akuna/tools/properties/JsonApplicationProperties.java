package ru.akuna.tools.properties;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.akuna.dto.Market;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

//Создавал класс для парсинга json файла для Follow Coin бота, потом переделал под бд, решил оставить класс навсякий
public class JsonApplicationProperties implements ApplicationProperties
{
    public List<Market> getMarketsFromProperty() throws IOException
    {
        URL url = getClass().getClassLoader().getResource(path + fileName);

        if (url != null)
        {
            file = new File( url.getFile().replaceAll("%20", " "));
        }

        ObjectMapper objectMapper = new ObjectMapper();

       return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Market.class));
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }


    //Don't used
    @Override
    public String getProperty(String key) {
        return null;
    }

    //Don't used
    @Override
    public int getIntProperty(String key) {
        return 0;
    }

    //Don't used
    @Override
    public long getLongProperty(String key) {
        return 0;
    }

    //Don't used
    @Override
    public double getDoubleProperty(String key) {
        return 0;
    }


    private String path;
    private String fileName;
    private File file;

}
