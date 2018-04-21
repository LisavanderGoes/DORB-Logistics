package com.lisa.dorb.layout;

import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.function.Route;
import com.lisa.dorb.layout.order.FactureUI;
import com.lisa.dorb.model.OrderPlanner;
import com.lisa.dorb.model.db.Order;
import com.lisa.dorb.model.db.Rit;
import com.lisa.dorb.model.db.users.User;
import com.lisa.dorb.repository.*;
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
public class PlannerUI extends VerticalLayout implements View {

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
    KlantRepository klantRepository;
    @Autowired
    UserRepository userRepository;

    private VerticalLayout parent;
    private List<OrderPlanner> orderList = new ArrayList<>();

    private long rowId;
    private Object rowItem;


    //UI
    public Grid<OrderPlanner> grid = new Grid<>();
    private Button terugBtn = new Button("Terug");
    private Button chauffeurBtn = new Button("Verander chauffeur");
    private Button vrachtwagenBtn = new Button("Verander vrahtwagen");
    public Label send = new Label("");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
    }

    public void laadOrderPlanner() {
        for(Order order : orderRepository.findAll()){
            User chauffeur = userRepository.findAllById(chauffeurRepository.findUser_IdAllById(Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getChauffeur_Id())));
            User klant = userRepository.findAllById(klantRepository.findAllById(Integer.parseInt(order.getKlant_Id())).getUser_Id());
            orderList.add(new OrderPlanner(order.getID(),
                    Integer.parseInt(order.getRit_Id()),
                    klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " + klant.getAchternaam(),
                    Date.valueOf(order.getDatum()),
                    vrachtwagenRepository.getKentekenById(Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getVrachtwagen_Id())),
                    order.getAdres(),
                    chauffeur.getVoornaam() + " " + chauffeur.getTussenvoegsel() + " " + chauffeur.getAchternaam()));
        }
        grid.setItems(orderList);
    }

    public void setGridOrder() { //rit_Id erbij
        grid.setCaption("Pallets");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<OrderPlanner> dataProvider =
                DataProvider.ofCollection(orderList);

        grid.setDataProvider(dataProvider);

        grid.addColumn(OrderPlanner::getRit_Id)
                .setCaption("Rit Id")
                .setExpandRatio(2);

        grid.addColumn(OrderPlanner::getKlantnaam)
                .setCaption("Naam klant")
                .setExpandRatio(2);


        grid.addColumn(OrderPlanner::getAdres)
                .setCaption("Adressen")
                .setExpandRatio(2);

        grid.addColumn(OrderPlanner::getDatum)
                .setCaption("Datum")
                .setExpandRatio(2);


        grid.addColumn(OrderPlanner::getKenteken)
                .setCaption("Vrachtwagen")
                .setExpandRatio(2);

        grid.addColumn(OrderPlanner::getNaamChauffeur)
                .setCaption("Chauffeur")
                .setExpandRatio(2);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event ->
                setID(event.getItem().getId(), event.getItem()));

        grid.getEditor().setEnabled(false);
        parent.addComponentsAndExpand(grid);
    }

    private void setID(long id, Object item) {
        rowId = id;
        rowItem = item;
    }

    private void changeChauffeur(int order_Id) {
        Order order = orderRepository.getAllById(order_Id);
        List<Long> chauffeurs = orderMaken.findChauffeur(Date.valueOf(order.getDatum()), Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getVrachtwagen_Id()), order.getLand_Id());
        long rit_Id = Integer.parseInt(order.getRit_Id());
        long chauffeur_Id = chauffeurs.get(0);
        ritRepository.updateChauffeur_Id(chauffeur_Id, rit_Id);
        reload();
    }

    private void changeVrachtwagen(int order_Id) {
        Order order = orderRepository.getAllById(order_Id);
        long rit_Id = Integer.parseInt(order.getRit_Id());
        Rit rit = ritRepository.getById(rit_Id);
        List<Long> vrachtwagen = orderMaken.findVrachtwagen(Date.valueOf(order.getDatum()), Integer.parseInt(rit.getRuimte()), 1, 0);
        long vrachtwagen_Id = vrachtwagen.get(0);
        ritRepository.updateVrachtwagen_Id(vrachtwagen_Id, rit_Id);
        reload();
    }

    private void reload(){
        orderList.clear();
        laadOrderPlanner();
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        chauffeurBtn.addClickListener(event -> changeChauffeur((int) rowId));
        vrachtwagenBtn.addClickListener(event -> changeVrachtwagen((int) rowId));
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
        layout2.addComponents(chauffeurBtn, vrachtwagenBtn, terugBtn, send);

        parent.addComponent(layout2);
    }

    private void terugButtonClick() {
        orderList.clear();
        grid.removeAllColumns();
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

