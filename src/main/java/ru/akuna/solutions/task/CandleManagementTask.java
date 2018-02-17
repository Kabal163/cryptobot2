package ru.akuna.solutions.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.entities.Candle;
import ru.akuna.entities.CandleManager;
import ru.akuna.entities.listeners.BittrexMarketListener;

/**
 * Created by Los Pepes on 2/3/2018.
 */
@Component
@Scope("prototype")
public class CandleManagementTask
{
    private static final Logger LOG = LoggerFactory.getLogger(CandleManagementTask.class);

    public void run(Market market)
    {
        Candle candle = new Candle(market);
        LOG.info("Update Candle: " + candle);
        candleManager.updateCandle(candle);
    }

    private CandleManager candleManager;


    @Autowired
    public void initCandleManager(CandleManager candleManager)
    {
        this.candleManager = candleManager;
    }
}
