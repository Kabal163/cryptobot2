package ru.akuna.tools;

public class MathTools
{
    public double getPriceWithPercent(double price, double percent)
    {
        return Math.round(((price * percent) + price) * 100000000d) / 100000000d;
    }
}
