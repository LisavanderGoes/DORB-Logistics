package com.lisa.dorb.layout.Order;

import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.model.*;
import com.lisa.dorb.model.DB.Order;
import com.lisa.dorb.model.DB.Pallet;
import com.lisa.dorb.model.DB.Rit;
import com.lisa.dorb.repository.*;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.List;

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
    @Autowired
    RitRepository ritRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PalletRepository palletRepository;

    private VerticalLayout parent;
    private NewOrder newOrder;

    //UI
    private Label klant = new Label("");
    private Label reknmr = new Label("");
    private Label adres = new Label("");
    private Label prijs = new Label("");
    private Label datum = new Label("");
    private Label pallet = new Label("");
    private Label palletAantal = new Label("");
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

    void setComponents(NewOrder order){
        klant.setValue(userRepository.findAllById(klantRepository.getUser_IdById(Long.parseLong(order.getOrder().getKlant_Id()))).getVoornaam() +" " +
                userRepository.findAllById(klantRepository.getUser_IdById(Long.parseLong(order.getOrder().getKlant_Id()))).getTussenvoegsel() +" " +
                userRepository.findAllById(klantRepository.getUser_IdById(Long.parseLong(order.getOrder().getKlant_Id()))).getAchternaam() +" ");
        reknmr.setValue(klantRepository.findAllById(Long.parseLong(order.getOrder().getKlant_Id())).getRekeningnummer());
        adres.setValue(order.getOrder().getAdres());
        prijs.setValue(order.getOrder().getPrijs() + " euro");
        datum.setValue(order.getOrder().getDatum());
        for(Pallet pallets : order.getPallet()) {
            pallet.setValue(pallet.getValue() + pallets.getWat() + "/");
        }
        palletAantal.setValue(order.getOrder().getPalletAantal());
        newOrder = order;
    }

    private void akoord() {
        Long rit_Id;
        if(newOrder.getRit().getID() != 0){
            Rit rit = newOrder.getRit();
            ritRepository.updateAll(Integer.parseInt(rit.getChauffeur_Id()), Integer.parseInt(rit.getVrachtwagen_Id()), Integer.parseInt(rit.getRuimte()), rit.getID());
            rit_Id = rit.getID();
        } else {
            Rit rit = newOrder.getRit();
            //error: [solved] chauffeur_Id is niet het user_Id
            ritRepository.addRow(Integer.parseInt(rit.getChauffeur_Id()), Integer.parseInt(rit.getVrachtwagen_Id()), Integer.parseInt(rit.getRuimte()), Date.valueOf(rit.getDatum()));
            rit_Id = ritRepository.getId();
        }
        Order order = newOrder.getOrder();
        //in order model is rit_Id nog steeds 0
        //error: [solved] klant_Id is niet het user_Id
        orderRepository.addRow(order.getAdres(), order.getPrijs(), Integer.parseInt(order.getKlant_Id()), Date.valueOf(order.getDatum()), rit_Id, order.getLand_Id(), Integer.parseInt(order.getPalletAantal()));
        long order_Id = orderRepository.getId();
        List<Pallet> pallets = newOrder.getPallet();
        //in pallet model is order_Id nog steeds 0
        for(Pallet pallet : pallets) {
            palletRepository.addRow(pallet.getWat(), order_Id, Integer.parseInt(pallet.getAantal()));
        }
        send.setValue("U bent akoord gegaan!");
        terugBtn.setCaption("Terug naar login");
    }

    private void addHeader() {
        Label header = new Label("FACATURE");
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
        layout1.addComponents(klant, reknmr, adres, prijs, datum, pallet, palletAantal);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addComponents(order, terugBtn, send);

        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.addComponents(layout1,layout2);

        parent.addComponentsAndExpand(layout3);
    }

    private void terugButtonClick() {
        send.setValue("");
        terugBtn.setCaption("Annuleren");
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

