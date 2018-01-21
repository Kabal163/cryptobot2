package ru.akuna.tools;

public class MathTools
{
    public double getPriceWithPercent(double price, double percent)
    {
        return Math.round(((price * percent / 100) + price) * 100000000d) / 100000000d;
    }

    public double roundToSpecifiedNumber(double price, double numberOfSymbols)
    {
        return Math.round(price * numberOfSymbols) / numberOfSymbols;
    }
}
