package ru.akuna.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TextTools
{
    private static DecimalFormat df;

    private static final Logger log = LoggerFactory.getLogger(TextTools.class);

    public String removeExhibitor(double price)
    {
        if (df == null)
        {
            df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
        }

        return df.format(price);
    }
}
