package com.lisa.dorb.layout;


import com.lisa.dorb.Saved.OrderItems;
import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.function.Route;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;

import javax.annotation.PostConstruct;
import java.sql.Date;

@SpringComponent
public class OrderUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    Route route;
    @Autowired
    OrderMaken orderMaken;

    private VerticalLayout parent;

    //UI
    private TextField straatnaam = new TextField("Straatnaam+Huisnummer");
    private TextField plaats = new TextField("Plaats");
    private TextField postcode = new TextField("Postcode");
    private TextField provincie = new TextField("Provincie");
    private TextField land = new TextField("Land");
    private DateField datum = new DateField("Datum van levering");
    private Button terugBtn = new Button("Annuleren");
    public Label send = new Label("");
    private TextField pallet = new TextField("Wat wilt u bestellen?");
    private NumberField aantal = new NumberField("Hoeveel pallets?");
    private Button order = new Button("Order maken");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addContent();
        addLayout();
        addOnclick();
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        order.addClickListener(event -> validation());
    }

    private void validation() {
        //try {
            if (straatnaam.isEmpty() || plaats.isEmpty() || postcode.isEmpty() || provincie.isEmpty() || land.isEmpty() || datum.isEmpty() || pallet.isEmpty() || aantal.isEmpty()) {
                send.setValue("Iets is niet ingevuld!");
            } else if (aantal.getValue().contains(",") || aantal.getValue().contains(".")) {
                send.setValue("De pallets kunnen alleen in hele worden besteld!");
            } else if (Integer.parseInt(aantal.getValue()) <= 0) {
                send.setValue("Er moet meer dan 0 besteld worden!");
            } else if (Integer.parseInt(aantal.getValue()) > 20) {
                send.setValue("Er kan niet meer dan 20 besteld worden!");
                Notification error = new Notification(send.getValue());
                error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
                error.show(send.getValue());
            } else {
                OrderItems.fullPalletAantal = Integer.parseInt(aantal.getValue());
                OrderItems.fullDate = Date.valueOf(datum.getValue());
                OrderItems.fullLand = land.getValue();
                OrderItems.fullPallet = pallet.getValue();
                //OrderItems.fullAdres = straatnaam.getValue() + plaats.getValue() + postcode.getValue() + provincie.getValue() + land.getValue();
                OrderItems.fullAdres = straatnaam.getValue();
                OrderItems.fullAdres = OrderItems.fullAdres.replaceAll("\\s+","");
                makeOrder();
            }
//        }catch (Exception e){
//            send.setValue(e+"");
//        }
    }

    private void makeOrder() {
            send.setValue(orderMaken.makeOrder(OrderItems.fullDate, OrderItems.fullAdres, OrderItems.fullLand, OrderItems.fullPalletAantal));
    }

    private void addContent() {

//        try {
////            route.getDistance("ede", "arnhem");
//            //send.setValue(route.getDistance("ede", "arnhem"));
//            ArrayList<String> km = new ArrayList<>();
//            km.add("nijmegen");
//            km.add("ede");
//            km.add("berlijn");
//            km.add("amsterdam");
//            //km.add("newyork"); //<-- werkt nie omdat het geen distance geeft
//            //route.ritLijst("arnhem", km);
//            //route.setMaps("arnhem", km);
//        } catch (Exception e) {
//            e.printStackTrace();
////            send.setValue("doet het niet");
//        }

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
        layout2.addComponents(pallet, aantal, order, terugBtn, send);

        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.addComponents(layout1,layout2);

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

