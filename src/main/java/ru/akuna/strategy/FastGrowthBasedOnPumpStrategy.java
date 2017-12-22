package ru.akuna.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.tools.properties.ApplicationProperties;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Los Pepes on 12/22/2017.
 */
@Component
@Qualifier("PumpStrategy")
public class FastGrowthBasedOnPumpStrategy
{
    public void run()
    {
        lifeStatistics = new LinkedList();
        int controlPointsNumber = properties.getIntProperty("control_points_number");


    }

    private void startAnalyse()
    {

    }

    private void fetchLifeStatistics()
    {

    }

    @Resource
    @Qualifier("PumpStrategyProperties")
    private ApplicationProperties properties;

    @Autowired
    private ExecutorService executorService;

    private List<List<Market>> lifeStatistics;
}
