package com.lisa.dorb.layout.order;

import com.lisa.dorb.model.StringLongModel;
import com.lisa.dorb.saved.UserInfo;
import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.model.NewOrder;
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
    OrderMaken orderMaken;
    @Autowired
    FactuurUI factuurUI;

    private List<StringLongModel> pallets = new ArrayList<>();
    private String fullAdres;

    //UI
    private VerticalLayout parent;
    private Grid<StringLongModel> palletsGrid = new Grid<>();
    private TextField straatnaamTxtField = new TextField("Straatnaam+Huisnummer");
    private TextField plaatsTxtField = new TextField("Plaats");
    private TextField postcodeTxtField = new TextField("Postcode");
    private TextField provincieTxtField = new TextField("Provincie");
    private TextField landTxtField = new TextField("Land");
    private DateField datumTxtField = new DateField("Datum van levering");
    private Button terugBtn = new Button("Annuleren");
    public Label send = new Label("");
    private Button toevoegenBtn = new Button("+");
    private TextField palletTxtField = new TextField("Wat wilt u bestellen?");
    private NumberField aantalTxtField = new NumberField("Hoeveel pallets?");
    private Button orderBtn = new Button("Order maken");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
        setGrid();
    }

    public void remove() {
        pallets.clear();
        straatnaamTxtField.clear();
        plaatsTxtField.clear();
        postcodeTxtField.clear();
        provincieTxtField.clear();
        landTxtField.clear();
        datumTxtField.clear();
        palletTxtField.clear();
        aantalTxtField.clear();
        palletsGrid.removeAllColumns();
        setGrid();
    }

    private void setGrid() {
        palletsGrid.setCaption("Pallets");
        palletsGrid.setSizeFull();
        palletsGrid.setSelectionMode(com.vaadin.ui.Grid.SelectionMode.NONE);
        ListDataProvider<StringLongModel> dataProvider =
                DataProvider.ofCollection(pallets);

        palletsGrid.setDataProvider(dataProvider);

        palletsGrid.addColumn(StringLongModel::getString)
                .setCaption("Wat?")
                .setExpandRatio(2);

        palletsGrid.addColumn(StringLongModel::get_long)
                .setCaption("Aantal")
                .setExpandRatio(2);

        palletsGrid.addColumn(person -> "Delete",
                new ButtonRenderer(clickEvent -> {
                    pallets.remove(clickEvent.getItem());
                    palletsGrid.setItems(pallets);
                }))
                .setExpandRatio(2);

        palletsGrid.getEditor().setEnabled(false);
    }

    // autofill + userId
    private void test() {
        UserInfo.user_Id = 141;
        straatnaamTxtField.setValue("ruisdaelstraat 36");
        plaatsTxtField.setValue("ede");
        postcodeTxtField.setValue("6717 tm");
        provincieTxtField.setValue("gelderland");
        landTxtField.setValue("nederland");
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> annulerenButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        orderBtn.addClickListener(event -> validation());
        toevoegenBtn.addClickListener(event -> toevoegen());
    }

    private void validation() {
        try {
            if (straatnaamTxtField.isEmpty() || plaatsTxtField.isEmpty() || postcodeTxtField.isEmpty() || provincieTxtField.isEmpty() || landTxtField.isEmpty() || datumTxtField.isEmpty()) {
                sendError("Iets is niet ingevuld!");
            } else if (aantalTxtField.getValue().contains(",") || aantalTxtField.getValue().contains(".")) {
                sendError("De pallets kunnen alleen in hele worden besteld!");
            } else if (pallets.isEmpty()) {
                sendError("Er moet meer dan 0 besteld worden!");
            } else {
                fullAdres = straatnaamTxtField.getValue() + plaatsTxtField.getValue() + postcodeTxtField.getValue() + provincieTxtField.getValue() + landTxtField.getValue();
                fullAdres = fullAdres.replaceAll("\\s+","");
                makeOrder();
            }
        }catch (Exception e){
            sendError("Er is iets foutgegaan!" + e);
        }
    }

    private void toevoegen(){
        long i = 0;
        for(StringLongModel pallets : pallets){
            i = i + pallets.get_long();
        }
        if(i + Integer.parseInt(aantalTxtField.getValue()) <= 20) {
            pallets.add(new StringLongModel(palletTxtField.getValue(), Integer.parseInt(aantalTxtField.getValue())));
            palletTxtField.clear();
            aantalTxtField.clear();
            palletsGrid.setItems(pallets);
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
            NewOrder order = orderMaken.makeOrder(Date.valueOf(datumTxtField.getValue()), fullAdres, landTxtField.getValue(), pallets);
            if(order != null) {
                factuurUI.setComponents(order);
                getUI().setContent(factuurUI);
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
        layout1.addComponents(straatnaamTxtField, plaatsTxtField, postcodeTxtField, provincieTxtField, landTxtField, datumTxtField);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addComponents(palletTxtField, aantalTxtField, toevoegenBtn, orderBtn, terugBtn, send);

        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.addComponents(layout1,layout2, palletsGrid);

        parent.addComponentsAndExpand(layout3);
    }

    private void annulerenButtonClick() {
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

