package ru.akuna.solutions.stockService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.akuna.BittrexUrls;
import ru.akuna.dto.Ticker;

/**
 * Created by Los Pepes on 12/27/2017.
 */
@Component
@Scope("prototype")
public class TickerService
{
    @Autowired
    private RestTemplate restTemplate;

    public Ticker getTicker(String market)
    {
        return restTemplate.getForObject(BittrexUrls.GET_TICKER + market, Ticker.class);
    }

}
