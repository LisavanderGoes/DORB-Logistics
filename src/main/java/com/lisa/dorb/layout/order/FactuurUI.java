package com.lisa.dorb.layout.order;

import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.model.*;
import com.lisa.dorb.model.db.Order;
import com.lisa.dorb.model.db.Pallet;
import com.lisa.dorb.model.db.Rit;
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
public class FactuurUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    UserRepository userRepository;
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    LandenRepository landenRepository;
    @Autowired
    RitRepository ritRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    PalletRepository palletRepository;
    @Autowired
    PrijsRepository prijsRepository;
    @Autowired
    OrderUI orderUI;

    private NewOrder newOrder;

    //UI
    private VerticalLayout parent;
    private Label klantLbl = new Label("");
    private Label reknmrLbl = new Label("");
    private Label kmLbl = new Label("");
    private Label adresLbl = new Label("");
    private Label prijsLbl = new Label("");
    private Label datumLbl = new Label("");
    private Label palletLbl = new Label("");
    private Label palletAantalLbl = new Label("");
    private Label send = new Label("");
    private Button annulerenBtn = new Button("Annuleren");
    private Button akoordBtn = new Button("Akoord");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
    }

    private void addOnclick() {
        annulerenBtn.addClickListener(event -> terugButtonClick());
        annulerenBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        akoordBtn.addClickListener(event -> akoord());
    }

    void setComponents(NewOrder order){
        klantLbl.setValue(userRepository.findAllById(klantRepository.getUser_IdById(Long.parseLong(order.getOrder().getKlant_Id()))).getVoornaam() +" " +
                userRepository.findAllById(klantRepository.getUser_IdById(Long.parseLong(order.getOrder().getKlant_Id()))).getTussenvoegsel() +" " +
                userRepository.findAllById(klantRepository.getUser_IdById(Long.parseLong(order.getOrder().getKlant_Id()))).getAchternaam() +" ");
        reknmrLbl.setValue(klantRepository.findAllById(Long.parseLong(order.getOrder().getKlant_Id())).getRekeningnummer());
        adresLbl.setValue(order.getOrder().getAdres());
        prijsLbl.setValue(order.getOrder().getPrijs() + " euro");
        datumLbl.setValue(order.getOrder().getDatum());
        kmLbl.setValue(String.valueOf(findKm(Double.parseDouble(order.getOrder().getPrijs()), landenRepository.getLandenByLand_Id(order.getOrder().getLand_Id()), Integer.parseInt(order.getOrder().getPalletAantal()))));
        for(Pallet pallets : order.getPallet()) {
            palletLbl.setValue(palletLbl.getValue() + pallets.getWat() + "/");
        }
        palletAantalLbl.setValue(order.getOrder().getPalletAantal());
        newOrder = order;
    }

    /**
     * @param prijs = prijs van order, double met punt
     * @param land = land van bestemming
     * @param palletAantal = aantal pallets in order
     * @return km in double met punt
     */
    private double findKm(double prijs, String land, long palletAantal){
        double prijsLand = Double.valueOf(prijsRepository.getPrijsByWat(land));
        double prijsPallets;
        if(palletAantal<= 6) {
            prijsPallets = Double.valueOf(prijsRepository.getPrijsByWat(palletAantal + "Pallet"));
        } else {
            prijsPallets = Double.valueOf(prijsRepository.getPrijsByWat(6 + "Pallet"));
        }
        return (prijs / prijsPallets) - prijsLand;
    }

    // naar DB
    private void akoord() {
        Long rit_Id;
        if(newOrder.getRit().getID() != 0){
            Rit rit = newOrder.getRit();
            ritRepository.updateAll(Integer.parseInt(rit.getChauffeur_Id()), Integer.parseInt(rit.getVrachtwagen_Id()), Integer.parseInt(rit.getRuimte()), rit.getID());
            rit_Id = rit.getID();
        } else {
            Rit rit = newOrder.getRit();
            ritRepository.addRow(Integer.parseInt(rit.getChauffeur_Id()), Integer.parseInt(rit.getVrachtwagen_Id()), Integer.parseInt(rit.getRuimte()), Date.valueOf(rit.getDatum()));
            rit_Id = ritRepository.getId();
        }
        Order order = newOrder.getOrder();
        orderRepository.addRow(order.getAdres(), order.getPrijs(), Integer.parseInt(order.getKlant_Id()), Date.valueOf(order.getDatum()), rit_Id, order.getLand_Id(), Integer.parseInt(order.getPalletAantal()));
        long order_Id = orderRepository.getId();
        List<Pallet> pallets = newOrder.getPallet();
        for(Pallet pallet : pallets) {
            palletRepository.addRow(pallet.getWat(), order_Id, Integer.parseInt(pallet.getAantal()));
        }
        send.setValue("U bent akoord gegaan!");
        annulerenBtn.setCaption("Terug naar login");
        palletLbl.setValue("");
        orderUI.remove();
    }

    private void addHeader() {
        Label header = new Label("FACTUUR");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        VerticalLayout layout1 = new VerticalLayout();
        klantLbl.setCaption("Klant:");
        reknmrLbl.setCaption("Rekeningnummer:");
        adresLbl.setCaption("Adres:");
        prijsLbl.setCaption("Prijs:");
        kmLbl.setCaption("Kilometers:");
        datumLbl.setCaption("Datum:");
        palletLbl.setCaption("Bestelt:");
        palletAantalLbl.setCaption("Aantal:");
        layout1.addComponents(klantLbl, reknmrLbl, adresLbl, kmLbl, prijsLbl, datumLbl, palletLbl, palletAantalLbl);

        VerticalLayout layout2 = new VerticalLayout();
        layout2.addComponents(akoordBtn, annulerenBtn, send);

        HorizontalLayout layout3 = new HorizontalLayout();
        layout3.addComponents(layout1,layout2);

        parent.addComponentsAndExpand(layout3);
    }

    private void terugButtonClick() {
        send.setValue("");
        annulerenBtn.setCaption("Annuleren");
        getUI().setContent(loginUI);
        palletLbl.setValue("");
        orderUI.remove();
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

