package ru.akuna.ui.converters;

import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.converter.AbstractStringToNumberConverter;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.tools.TextTools;

public class BtcPriceToStringConverter extends AbstractStringToNumberConverter<Double>
{
    public BtcPriceToStringConverter() {
        this((Double)null, "Incorrect Price");
    }

    public BtcPriceToStringConverter(Double emptyValue, String errorMessage) {
        super(emptyValue, errorMessage);
    }

    public Result<Double> convertToModel(String value, ValueContext context)
    {
        Double d = Double.parseDouble(value);

        return Result.ok(d);
    }

    @Override
    public String convertToPresentation(Double value, ValueContext valueContext)
    {
        return textTools.removeExhibitor(value);
    }

    private TextTools textTools = ApplicationContextProvider.getApplicationContext().getBean(TextTools.class);
}
