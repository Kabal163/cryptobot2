package ru.akuna.solutions.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.akuna.dto.Market;
import ru.akuna.entities.Candle;
import ru.akuna.entities.CandleContainer;

/**
 * Created by Los Pepes on 2/3/2018.
 */
@Component
@Scope("prototype")
public class CandleManagementTask
{

    public void run(Market market)
    {
        Candle candle = new Candle(market);
        candleContainer.updateCandle(candle);
    }

    @Autowired
    private CandleContainer candleContainer;
}
