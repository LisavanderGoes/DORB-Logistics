package com.lisa.dorb.layout.Order;

import com.lisa.dorb.saved.UserInfo;
import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.function.Route;
import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.model.NewOrder;
import com.lisa.dorb.model.Pallets;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;
import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class OrderUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    Route route;
    @Autowired
    OrderMaken orderMaken;
    @Autowired
    FactureUI factureUI;

    private VerticalLayout parent;
    private List<Pallets> pallets = new ArrayList<>();
    private String fullAdres;

    //UI
    public Grid<Pallets> grid = new Grid<>();
    private TextField straatnaam = new TextField("Straatnaam+Huisnummer");
    private TextField plaats = new TextField("Plaats");
    private TextField postcode = new TextField("Postcode");
    private TextField provincie = new TextField("Provincie");
    private TextField land = new TextField("Land");
    private DateField datum = new DateField("Datum van levering");
    private Button terugBtn = new Button("Annuleren");
    public Label send = new Label("");
    public Button addBtn = new Button("+");
    private TextField pallet = new TextField("Wat wilt u bestellen?");
    private NumberField aantal = new NumberField("Hoeveel pallets?");
    private Button order = new Button("Order maken");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
        setGrid();
    }

    private void setGrid() {
        grid.setCaption("Pallets");
        grid.setSizeFull();
        grid.setSelectionMode(com.vaadin.ui.Grid.SelectionMode.NONE);
        ListDataProvider<Pallets> dataProvider =
                DataProvider.ofCollection(pallets);

        grid.setDataProvider(dataProvider);

        grid.addColumn(Pallets::getWat)
                .setCaption("Wat?")
                .setExpandRatio(2);

        grid.addColumn(Pallets::getAantal)
                .setCaption("Aantal")
                .setExpandRatio(2);

        grid.addColumn(person -> "Delete",
                new ButtonRenderer(clickEvent -> {
                    pallets.remove(clickEvent.getItem());
                    grid.setItems(pallets);
                }))
                .setExpandRatio(2);

        grid.getEditor().setEnabled(false);
    }

    private void test() {
        UserInfo.user_Id = 141;
        straatnaam.setValue("ruisdaelstraat 36");
        plaats.setValue("ede");
        postcode.setValue("6717 tm");
        provincie.setValue("gelderland");
        land.setValue("nederland");
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        order.addClickListener(event -> validation());
        addBtn.addClickListener(event -> add());
    }

    private void validation() {
        try {
            if (straatnaam.isEmpty() || plaats.isEmpty() || postcode.isEmpty() || provincie.isEmpty() || land.isEmpty() || datum.isEmpty()) {
                sendError("Iets is niet ingevuld!");
            } else if (aantal.getValue().contains(",") || aantal.getValue().contains(".")) {
                sendError("De pallets kunnen alleen in hele worden besteld!");
            } else if (pallets.isEmpty()) {
                sendError("Er moet meer dan 0 besteld worden!");
            } else {
                fullAdres = straatnaam.getValue() + plaats.getValue() + postcode.getValue() + provincie.getValue() + land.getValue();
                fullAdres = fullAdres.replaceAll("\\s+","");
                makeOrder();
            }
        }catch (Exception e){
            sendError("Er is iets foutgegaan!");
        }
    }

    private void add(){
        long i = 0;
        for(Pallets pallets : pallets){
            i = i + pallets.getAantal();
        }
        if(i + Integer.parseInt(aantal.getValue()) <= 20) {
            pallets.add(new Pallets(pallet.getValue(), Integer.parseInt(aantal.getValue())));
            pallet.clear();
            aantal.clear();
            grid.setItems(pallets);
        } else{
            sendError("Er kan niet meer dan 20 besteld worden!");
        }
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void makeOrder() {
            NewOrder order = orderMaken.makeOrder(Date.valueOf(datum.getValue()), fullAdres, land.getValue(), pallets);
            if(order != null) {
                factureUI.setComponents(order);
                getUI().setContent(factureUI);
            } else{
                sendError("Geen mogelijkheden kunnen vinden!");
            }
    }

    private void addHeader() {
        Label header = new Label("ORDER");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        VerticalLayout layout1 = new VerticalLayout();
        layout1.addComponents(straatnaam, plaats, postcode, provincie, land, datum);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addComponents(pallet, aantal, addBtn, order, terugBtn, send);

        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.addComponents(layout1,layout2, grid);

        parent.addComponentsAndExpand(layout3);
    }

    private void terugButtonClick() {
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

