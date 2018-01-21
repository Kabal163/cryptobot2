package ru.akuna.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import ru.akuna.dao.BittrexMarket;
import ru.akuna.dao.MarketRepository;
import ru.akuna.ui.renderers.BtcPriceRenderer;

@SpringUI
public class VaadinUI extends UI
{
    @Autowired
    public VaadinUI(MarketRepository repo, MarketEditor marketEditor)
    {
        this.repo = repo;
        this.marketEditor = marketEditor;
        this.grid = new Grid<>(BittrexMarket.class);
        this.filter = new TextField();
        addNewMarketButton = new Button("New Market", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        //Build layouts
        HorizontalLayout actions = new HorizontalLayout(filter, addNewMarketButton);
        HorizontalLayout tableAndEditForm = new HorizontalLayout(grid, marketEditor);
        VerticalLayout mainLayout = new VerticalLayout(actions, tableAndEditForm);
        setContent(mainLayout);

        //Setup
        gridSetup();
        filterSetup();

        // Connect selected Market to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            marketEditor.editMarket(e.getValue());
        });

        // Instantiate and edit new Market the new button is clicked
        addNewMarketButton.addClickListener(e -> marketEditor.editMarket(new BittrexMarket()));

        // Listen changes made by the editor, refresh data from backend
        marketEditor.setChangeHandler(() -> {
            marketEditor.setVisible(false);
            listMarkets(filter.getValue());
        });

        //Show
        listMarkets(null);
    }

    private void filterSetup()
    {
        filter.setPlaceholder("Filter by Market Name");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listMarkets(e.getValue()));
    }

    private void gridSetup()
    {
        grid.setHeight(gridHeight, Unit.PIXELS);
        grid.setColumns(id, marketName, belowPrice, abovePrice);
        setCustomPriceRenderer();
    }

    private void setCustomPriceRenderer()
    {
        grid.getColumn(belowPrice).setRenderer(new BtcPriceRenderer());
        grid.getColumn(abovePrice).setRenderer(new BtcPriceRenderer());
    }

    private void listMarkets(String marketName)
    {
        if (StringUtils.isEmpty(marketName))
        {
            grid.setItems(repo.findAll());
        }
        else
        {
            grid.setItems(repo.findByMarketNameContains(marketName));
        }
    }

    private MarketRepository repo;

    private Grid<BittrexMarket> grid;

    private MarketEditor marketEditor;

    private TextField filter;

    private final Button addNewMarketButton;

    private String id = "id";
    private String marketName = "marketName";
    private String belowPrice = "belowPrice";
    private String abovePrice = "abovePrice";

    //Default Height of grid
    private float gridHeight = 450;
}
