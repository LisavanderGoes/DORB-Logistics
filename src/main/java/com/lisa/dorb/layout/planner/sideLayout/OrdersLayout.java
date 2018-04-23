package com.lisa.dorb.layout.planner.sideLayout;

import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.layout.order.FactureUI;
import com.lisa.dorb.layout.planner.PlannerUI;
import com.lisa.dorb.model.OrderPlanner;
import com.lisa.dorb.model.db.Order;
import com.lisa.dorb.model.db.Pallet;
import com.lisa.dorb.model.db.Rit;
import com.lisa.dorb.model.db.users.User;
import com.lisa.dorb.repository.*;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class OrdersLayout extends VerticalLayout {

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
    VrachtwagenTypeRepository vrachtwagenTypeRepository;
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PalletRepository palletRepository;
    @Autowired
    PlannerUI plannerUI;


    private List<OrderPlanner> orderList = new ArrayList<>();
    private List<Pallet> palletsList = new ArrayList<>();

    private long rowId;
    private Object rowItem;


    //UI
    private HorizontalLayout gridLayout = new HorizontalLayout();
    public Grid<OrderPlanner> grid = new Grid<>();
    private Grid<Pallet> gridDetail = new Grid<>();
    private Button chauffeurBtn = new Button("Verander chauffeur");
    private Button vrachtwagenBtn = new Button("Verander vrachtwagen/ in onderhoud zetten");
    private Button detailsBtn = new Button("Details");
    public Label send = new Label("");


    public void laadOrderPlanner() {
        addOnclick();
        addLayout();
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

    private void laadDetails(long rowId) {
        palletsList = palletRepository.getAllByOrder_Id(rowId);
        gridDetail.setItems(palletsList);
    }

    public void setGridOrder() {
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
        gridLayout.addComponentsAndExpand(grid);
    }

    private void setGridDetails() {
        Grid<Pallet> grid = gridDetail;
        grid.setCaption("Details");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Pallet> dataProvider =
                DataProvider.ofCollection(palletsList);

        grid.setDataProvider(dataProvider);

        TextField taskField = new TextField();

        grid.addColumn(Pallet::getWat)
                .setCaption("Wat?")
                .setExpandRatio(2);

        grid.addColumn(Pallet::getAantal)
                .setEditorComponent(taskField, this::setAantal)
                .setCaption("Aantal")
                .setExpandRatio(2);

        grid.getEditor().setEnabled(true);
        gridLayout.addComponentsAndExpand(grid);
    }

    private void setAantal(Pallet pallet, String aantal) {
        int allAantalInOrder = Integer.parseInt(aantal) - Integer.parseInt(pallet.getAantal());
        for(Pallet pallets : palletRepository.getAllByOrder_Id(Integer.parseInt(pallet.getOrder_Id()))){
            allAantalInOrder = allAantalInOrder + Integer.parseInt(pallets.getAantal());
        }
        if(allAantalInOrder <= 20) {
            long ruimte = vrachtwagenTypeRepository.getRuimteById(vrachtwagenRepository.getTyp_IdById(Integer.parseInt(ritRepository.getById(Integer.parseInt(orderRepository.getAllById(Integer.parseInt(pallet.getOrder_Id())).getRit_Id())).getVrachtwagen_Id())));
            if (allAantalInOrder > ruimte) {
                Order order = orderRepository.getAllById(Integer.parseInt(pallet.getOrder_Id()));
                List<Long> vrachtwagen = orderMaken.findVrachtwagen(Date.valueOf(order.getDatum()), allAantalInOrder, 1, 0);
                List<Long> chauffeurs = orderMaken.findChauffeur(Date.valueOf(order.getDatum()), vrachtwagen.get(0), order.getLand_Id());
                ritRepository.updateChauffeur_Id(chauffeurs.get(0), Integer.parseInt(order.getRit_Id()));
                ritRepository.updateVrachtwagen_Id(vrachtwagen.get(0), Integer.parseInt(order.getRit_Id()));
                reload();
            }
        }
        palletRepository.updateAantal(Integer.parseInt(aantal), pallet.getID());
        pallet.setAantal(Integer.parseInt(aantal));
        gridDetail.setItems(palletsList);
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
        vrachtwagenRepository.updateStatus("onderhoud", Integer.parseInt(rit.getVrachtwagen_Id()));
        reload();
    }

    private void reload(){
        orderList.clear();
        laadOrderPlanner();
    }

    private void addOnclick() {
        chauffeurBtn.addClickListener(event -> changeChauffeur((int) rowId));
        vrachtwagenBtn.addClickListener(event -> changeVrachtwagen((int) rowId));
        detailsBtn.addClickListener(event -> details());
    }

    private void details() {
        palletsList.clear();
        gridDetail.removeAllColumns();
        laadDetails(rowId);
        setGridDetails();
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void addLayout() {
        gridLayout = plannerUI.gridLayout;
        grid = plannerUI.gridOrders;
        gridDetail = plannerUI.gridDetail;
        orderList = plannerUI.orderList;
        palletsList = plannerUI.palletsList;

        HorizontalLayout layout2 = plannerUI.buttons;
        layout2.addComponents(chauffeurBtn, vrachtwagenBtn, detailsBtn, send);

        plannerUI.parent.addComponent(layout2);
    }
}

