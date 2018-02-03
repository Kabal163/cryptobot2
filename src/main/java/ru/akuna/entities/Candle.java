package ru.akuna.entities;

import org.springframework.stereotype.Component;

/**
 * Created by Los Pepes on 2/3/2018.
 */
@Component
public class Candle
{
    private double maxPrice;
    private double minPrice;
    private double openPrice;
    private double lastPrice;
    private boolean isOpen;

    public Candle()
    {
        this(0);
    }

    public Candle(double openPrice)
    {
        this.openPrice = openPrice;
        this.lastPrice = openPrice;
        this.maxPrice = openPrice;
        this.minPrice = openPrice;
        this.isOpen = true;
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

    public void setOpen(boolean open)
    {
        isOpen = open;
    }

    @Override
    public String toString()
    {
        return "Candle{" +
                "maxPrice=" + maxPrice +
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
        return isOpen() == candle.isOpen();
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getMaxPrice());
        result = (int) (temp ^ (temp >>> 32));
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
