package com.lisa.dorb.layout;

import com.lisa.dorb.Saved.OrderItems;
import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.function.Route;
import com.lisa.dorb.model.NewOrder;
import com.lisa.dorb.model.VrachtwagenType;
import com.lisa.dorb.repository.*;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;

import javax.annotation.PostConstruct;
import java.sql.Date;

@SpringComponent
public class FactureUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    UserRepository userRepository;
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    VrachtwagenTypeRepository vrachtwagenTypeRepository;
    @Autowired
    LandenRepository landenRepository;

    private VerticalLayout parent;

    //UI
    private Label klant = new Label("");
    private Label reknmr = new Label("");
    private Label adres = new Label("");
    private Label prijs = new Label("");
    private Label datum = new Label("");
    private Label pallet = new Label("");
    private Label palletAantal = new Label("");
    private Label land = new Label("");

    private Label send = new Label("");
    private Button terugBtn = new Button("Annuleren");
    private Button order = new Button("Akoord");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        order.addClickListener(event -> akoord());
    }

    public void setComponents(NewOrder order){
        klant.setValue(userRepository.findAllById(Long.parseLong(order.getOrder().getKlant_Id())).getVoornaam() +" " +
                userRepository.findAllById(Long.parseLong(order.getOrder().getKlant_Id())).getTussenvoegsel() +" " +
                userRepository.findAllById(Long.parseLong(order.getOrder().getKlant_Id())).getAchternaam() +" ");
        reknmr.setValue(klantRepository.findAllById(Long.parseLong(order.getOrder().getKlant_Id())).getRekeningnummer());
        adres.setValue(order.getOrder().getAdres());
        prijs.setValue(order.getOrder().getPrijs());
        datum.setValue(order.getOrder().getDatum());
        pallet.setValue(order.getOrder().getPallet_Id()); //en stuff
        palletAantal.setValue(order.getOrder().getPalletAantal());
        land.setValue(landenRepository.getLandenByLand_Id(order.getOrder().getLand_Id()));
    }

    private void akoord() {
        send.setValue("U bent akoord gegaan!");
        //to db
        //first rit
        //then pallet
        //get id from both
        //set new id in order
        //order to db
    }

    private void addHeader() {
        Label header = new Label("ORDER");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        VerticalLayout layout1 = new VerticalLayout();
        klant.setCaption("Klant:");
        reknmr.setCaption("Rekeningnummer:");
        adres.setCaption("Adres:");
        prijs.setCaption("Prijs:");
        datum.setCaption("Datum:");
        pallet.setCaption("Bestelt:");
        palletAantal.setCaption("Aantal:");
        land.setCaption("Land:");
        layout1.addComponents(klant, reknmr, adres, prijs, datum, pallet, palletAantal, land);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addComponents(order, terugBtn, send);

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

