package ru.akuna.entities;

import java.util.List;

/**
 * Created by Los Pepes on 2/4/2018.
 */
public interface ICandleContainer
{

    /**
     * The method edits current open candle. If candle is closed - exception is raised.
     * @param candle the source candle which we use to update the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    void editCurrentCandle(Candle candle) throws Exception;


    /**
     * If current candle is open method updates it firstly. After that candle will be closed.
     * @param candle the source candle which we use to close the distinct one.
     *               The distinct candle is found by marketName from source candle.
     */
    void updateAndCloseCandle(Candle candle) throws Exception;


    /**
     * @param marketName the name of the market which we use to find
     *                   appropriate candle.
     * @return last closed candle for this market.
     */
    Candle getLastClosedCandle(String marketName);


    /**
     * @param marketName the name of the market which we use to find
     *                   appropriate candle.
     * @return last open candle for this market.
     */
    Candle getOpenCandle(String marketName);


    /**
     * @param marketName the name of the market which we use to find
     *                   appropriate candles.
     * @return all candles for this market.
     */
    public List<Candle> getAllCandles(String marketName);


    /**
     * @param marketName the name of the market which we use to find
     *                   appropriate candles.
     * @param amountOf amount of last CLOSED candles which we want to get
     * @return specified number of closed candles for this market.
     */
    List<Candle> getSpecifiedLastCandles(String marketName, int amountOf);
}
