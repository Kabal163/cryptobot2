package ru.akuna.ui;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;
import ru.akuna.dao.BittrexMarket;
import ru.akuna.dao.MarketRepository;
import ru.akuna.ui.converters.BtcPriceToStringConverter;

@SpringComponent
@UIScope
public class MarketEditor extends VerticalLayout
{
    @Autowired
    public MarketEditor(MarketRepository repository)
    {
        this.repository = repository;

        fieldsSetup();

        addComponents(marketName, belowPrice, abovePrice, actions);

        bindsSetup();

        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.addClickListener(e -> repository.save(market));
        delete.addClickListener(e -> repository.delete(market));
        cancel.addClickListener(e -> editMarket(market));
        setVisible(false);
    }

    private void bindsSetup()
    {
        binder.forField(belowPrice)
                .withNullRepresentation("")
                .withConverter(new BtcPriceToStringConverter())
                .bind("belowPrice");

        binder.forField(abovePrice)
                .withNullRepresentation("")
                .withConverter(new BtcPriceToStringConverter())
                .bind("abovePrice");

        binder.bindInstanceFields(this);
    }

    private void fieldsSetup()
    {
        belowPrice.setCaption("Below Price");
        belowPrice.setDecimalSeparator('.');

        abovePrice.setCaption("Above Price");
        abovePrice.setDecimalSeparator('.');
    }

    public interface ChangeHandler
    {
        void onChange();
    }

    public final void editMarket(BittrexMarket market)
    {
        if (market == null)
        {
            setVisible(false);
            return;
        }

        final boolean persisted = market.getId() != null;

        if (persisted)
        {
            this.market = repository.findOne(market.getId());
        }
        else
        {
            this.market = market;
        }

        cancel.setVisible(true);

        binder.setBean(this.market);

        setVisible(true);

        save.focus();

        marketName.selectAll();
    }

    public void setChangeHandler(ChangeHandler handler)
    {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> handler.onChange());
        delete.addClickListener(e -> handler.onChange());
    }

    private final MarketRepository repository;

    private BittrexMarket market;

    //Fields to edit properties of Market
    private TextField marketName = new TextField("Market Name");
    private NumberField belowPrice = new NumberField();
    private NumberField abovePrice = new NumberField();

    //Action Buttons
    private Button save = new Button("Save", FontAwesome.SAVE);
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", FontAwesome.TRASH_O);
    private CssLayout actions = new CssLayout(save, cancel, delete);

    private Binder<BittrexMarket> binder = new Binder<>(BittrexMarket.class);
}
