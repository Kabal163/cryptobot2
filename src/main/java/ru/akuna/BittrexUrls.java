package ru.akuna;

/**
 * Created by Los Pepes on 12/9/2017.
 */
public interface BittrexUrls
{
    String GET_MARKETS = "https://bittrex.com/api/v1.1/public/getmarkets";

    String GET_CURRENCIES = "https://bittrex.com/api/v1.1/public/getcurrencies";

    String GET_TICKER = "https://bittrex.com/api/v1.1/public/getticker?market=";

    String GET_ORDER_BOOK = "https://bittrex.com/api/v1.1/public/getorderbook?market={0}&type=both";

    String GET_MARKET_SUMMARIES = "https://bittrex.com/api/v1.1/public/getmarketsummaries";
}
