package ru.akuna.ui.renderers;

import com.vaadin.ui.renderers.TextRenderer;
import elemental.json.JsonValue;
import ru.akuna.providers.ApplicationContextProvider;
import ru.akuna.tools.TextTools;


public class BtcPriceRenderer extends TextRenderer
{
    @Override
    public JsonValue encode(Object value)
    {
        String strValue = textTools.getBtcDecimalFormat().format(value);

        return encode(strValue, String.class);
    }

    private TextTools textTools = ApplicationContextProvider.getApplicationContext().getBean(TextTools.class);
}
