package com.lisa.dorb.layout.chauffeur;

import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.function.Route;
import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.layout.Order.FactureUI;
import com.lisa.dorb.model.DB.Order;
import com.lisa.dorb.model.DB.Rit;
import com.lisa.dorb.model.Werkrooster;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.saved.UserInfo;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class WerkroosterUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    Route route;
    @Autowired
    OrderMaken orderMaken;
    @Autowired
    FactureUI factureUI;
    @Autowired
    RitRepository ritRepository;
    @Autowired
    ChauffeurRepository chauffeurRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    VrachtwagenRepository vrachtwagenRepository;
    @Autowired
    LandenRepository landenRepository;

    private VerticalLayout parent;
    private List<Werkrooster> werkrooster = new ArrayList<>();


    //UI
    public Grid<Werkrooster> grid = new Grid<>();
    private Button terugBtn = new Button("Terug");
    public Label send = new Label("");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
//        setGrid();
    }

    public void laadWerkrooster() {
        long chauffeur_Id = chauffeurRepository.getIdByUser_Id(UserInfo.user_Id);
        for(Rit rit: ritRepository.getByChauffeur_Id(chauffeur_Id)){
            String kenteken = vrachtwagenRepository.getAllById(Integer.parseInt(rit.getVrachtwagen_Id()));
            String adressen = "";
            long i = 0;
            List<String> adres = new ArrayList<>();
            for(Order order : orderRepository.getAllByRit_Id(rit.getID())){
                if(!adres.contains(landenRepository.getLandenByLand_Id(order.getLand_Id()))) {
                    adres.add(landenRepository.getLandenByLand_Id(order.getLand_Id()));
                }
                i++;
            }
            for(String adr :adres) {
                adressen = adressen + adr + "/";
            }
            werkrooster.add(new Werkrooster(Date.valueOf(rit.getDatum()), kenteken, adressen, i));
        }
        grid.setItems(werkrooster);
    }

    public void setGrid() {
        grid.setCaption("Pallets");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Werkrooster> dataProvider =
                DataProvider.ofCollection(werkrooster);

        grid.setDataProvider(dataProvider);

        grid.addColumn(Werkrooster::getAantal)
                .setCaption("Aantaal orders")
                .setExpandRatio(0);


        grid.addColumn(Werkrooster::getAdres)
                .setCaption("Adressen")
                .setExpandRatio(2);

        grid.addColumn(Werkrooster::getKenteken)
                .setCaption("Vrachtwagen")
                .setExpandRatio(2);

        grid.addColumn(Werkrooster::getDatum)
                .setCaption("Datum")
                .setExpandRatio(2);

        grid.getEditor().setEnabled(false);
        parent.addComponentsAndExpand(grid);
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void addHeader() {
        Label header = new Label("WERKROOSTER");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.addComponents( terugBtn, send);

        parent.addComponent(layout2);
    }

    private void terugButtonClick() {
        werkrooster.clear();
        grid.removeAllColumns();
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

