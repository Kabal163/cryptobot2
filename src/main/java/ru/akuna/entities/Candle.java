package ru.akuna.entities;

import ru.akuna.dto.Market;

/**
 * Created by Los Pepes on 2/3/2018.
 */
public class Candle
{
    private String marketName;
    private double maxPrice;
    private double minPrice;
    private double openPrice;
    private double lastPrice;
    private boolean isOpen;
    private int countOfUpdates;

    public Candle(String marketName)
    {
        this(0, marketName);
    }

    public Candle(double openPrice, String marketName)
    {
        this.openPrice = openPrice;
        this.lastPrice = openPrice;
        this.maxPrice = openPrice;
        this.minPrice = openPrice;
        this.marketName = marketName;
        this.isOpen = true;
    }

    public Candle(Market market)
    {
        this(market.getLast(), market.getMarketName());
    }

    public double getMaxPrice()
    {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice)
    {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice()
    {
        return minPrice;
    }

    public void setMinPrice(double minPrice)
    {
        this.minPrice = minPrice;
    }

    public double getOpenPrice()
    {
        return openPrice;
    }

    public void setOpenPrice(double openPrice)
    {
        this.openPrice = openPrice;
    }

    public double getLastPrice()
    {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice)
    {
        this.lastPrice = lastPrice;
    }

    public boolean isOpen()
    {
        return isOpen;
    }

    public void close()
    {
        isOpen = false;
    }

    public String getMarketName()
    {
        return marketName;
    }

    public void setMarketName(String marketName)
    {
        this.marketName = marketName;
    }

    public int getCountOfUpdates()
    {
        return countOfUpdates;
    }

    public void setCountOfUpdates(int countOfUpdates)
    {
        this.countOfUpdates = countOfUpdates;
    }

    public void incrementUpdateCounter()
    {
        this.countOfUpdates++;
    }

    @Override
    public String toString()
    {
        return "Candle{" +
                "marketName='" + marketName + '\'' +
                ", maxPrice=" + maxPrice +
                ", minPrice=" + minPrice +
                ", openPrice=" + openPrice +
                ", lastPrice=" + lastPrice +
                ", isOpen=" + isOpen +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Candle)) return false;

        Candle candle = (Candle) o;

        if (Double.compare(candle.getMaxPrice(), getMaxPrice()) != 0) return false;
        if (Double.compare(candle.getMinPrice(), getMinPrice()) != 0) return false;
        if (Double.compare(candle.getOpenPrice(), getOpenPrice()) != 0) return false;
        if (Double.compare(candle.getLastPrice(), getLastPrice()) != 0) return false;
        if (isOpen() != candle.isOpen()) return false;
        return getMarketName().equals(candle.getMarketName());
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        result = getMarketName().hashCode();
        temp = Double.doubleToLongBits(getMaxPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getMinPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getOpenPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLastPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isOpen() ? 1 : 0);
        return result;
    }
}
