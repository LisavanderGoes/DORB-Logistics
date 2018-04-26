package com.lisa.dorb.layout.planner.sideLayout;

import com.lisa.dorb.function.OrderMaken;
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


    //UI
    private HorizontalLayout gridLayout = new HorizontalLayout();
    public Grid<OrderPlanner> orderGrid = new Grid<>();
    private Grid<Pallet> detailGrid = new Grid<>();
    private Button chauffeurBtn = new Button("Verander chauffeur");
    private Button vrachtwagenBtn = new Button("Verander vrachtwagen");
    private Button detailsBtn = new Button("Details");
    private ComboBox chauffeurBox = new ComboBox();
    private ComboBox vrachtwagenBox = new ComboBox();
    public Label send = new Label("");


    public void start() {
        addOnclick();
        addLayout();
        laadOrderPlanner();
    }

    private void laadOrderPlanner() {
        for(Order order : orderRepository.findAll()){
            User chauffeur = userRepository.findAllById(chauffeurRepository.findUser_IdAllById(Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getChauffeur_Id())));
            User klant = userRepository.findAllById(klantRepository.findAllById(Integer.parseInt(order.getKlant_Id())).getUser_Id());
            orderList.add(new OrderPlanner(order.getID(),
                    Integer.parseInt(order.getRit_Id()),
                    klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " + klant.getAchternaam(),
                    Date.valueOf(order.getDatum()),
                    vrachtwagenRepository.getKentekenById(Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getVrachtwagen_Id())),
                    order.getAdres(),
                    chauffeur.getVoornaam() + " " + chauffeur.getTussenvoegsel() + " " + chauffeur.getAchternaam(),
                    Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getRuimte()),
                    Double.parseDouble(order.getPrijs())));
        }
        orderGrid.setItems(orderList);
    }

    /**
     * @param rowId = order_Id
     */
    private void laadDetails(long rowId) {
        palletsList = palletRepository.getAllByOrder_Id(rowId);
        detailGrid.setItems(palletsList);
    }

    public void setGridOrder() {
        orderGrid.setCaption("Orders");
        orderGrid.setSizeFull();
        orderGrid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<OrderPlanner> dataProvider =
                DataProvider.ofCollection(orderList);

        orderGrid.setDataProvider(dataProvider);


        orderGrid.addColumn(OrderPlanner::getRit_Id)
                .setCaption("Rit Id")
                .setExpandRatio(2);

        orderGrid.addColumn(OrderPlanner::getRuimte)
                .setCaption("Pallets in rit")
                .setExpandRatio(2);

        orderGrid.addColumn(OrderPlanner::getKlantnaam)
                .setCaption("Naam klant")
                .setExpandRatio(2);


        orderGrid.addColumn(OrderPlanner::getAdres)
                .setCaption("Adressen")
                .setExpandRatio(2);

        orderGrid.addColumn(OrderPlanner::getPrijs)
                .setCaption("Prijs")
                .setExpandRatio(2);

        orderGrid.addColumn(OrderPlanner::getDatum)
                .setCaption("Datum")
                .setExpandRatio(2);


        orderGrid.addColumn(OrderPlanner::getKenteken)
                .setCaption("Vrachtwagen")
                .setExpandRatio(2);

        orderGrid.addColumn(OrderPlanner::getNaamChauffeur)
                .setCaption("Chauffeur")
                .setExpandRatio(2);

        orderGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        orderGrid.addItemClickListener(event ->
                setID(event.getItem().getId()));

        orderGrid.getEditor().setEnabled(false);
        gridLayout.addComponentsAndExpand(orderGrid);
    }

    private void setGridDetails() {
        Grid<Pallet> grid = detailGrid;
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

    /**
     * @param pallet = pallet(model)
     * @param aantal = nieuwe aantal
     */
    private void setAantal(Pallet pallet, String aantal) {
        int allAantalInOrder = Integer.parseInt(aantal);
        for(Pallet pallets : palletRepository.getAllByOrder_Id(Integer.parseInt(pallet.getOrder_Id()))){
            allAantalInOrder = allAantalInOrder + Integer.parseInt(pallets.getAantal());
        }
        if(allAantalInOrder <= 20) {
            if (Integer.parseInt(orderRepository.getAllById(Integer.parseInt(pallet.getOrder_Id())).getPalletAantal()) + Integer.parseInt(aantal) <= 30) {
                long ruimte = vrachtwagenTypeRepository.getRuimteById(vrachtwagenRepository.getTyp_IdById(Integer.parseInt(ritRepository.getById(Integer.parseInt(orderRepository.getAllById(Integer.parseInt(pallet.getOrder_Id())).getRit_Id())).getVrachtwagen_Id())));
                if (allAantalInOrder > ruimte) {
                    Order order = orderRepository.getAllById(Integer.parseInt(pallet.getOrder_Id()));
                    List<Long> vrachtwagen = orderMaken.findVrachtwagen(Date.valueOf(order.getDatum()), allAantalInOrder, 1, 0);
                    List<Long> chauffeurs = orderMaken.findChauffeur(Date.valueOf(order.getDatum()), vrachtwagen.get(0), order.getLand_Id());
                    ritRepository.updateChauffeur_Id(chauffeurs.get(0), Integer.parseInt(order.getRit_Id()));
                    ritRepository.updateVrachtwagen_Id(vrachtwagen.get(0), Integer.parseInt(order.getRit_Id()));
                    reload();
                }
                palletRepository.updateAantal(Integer.parseInt(aantal), pallet.getID());
                orderRepository.updateAantal(Integer.parseInt(orderRepository.getAllById(Integer.parseInt(pallet.getOrder_Id())).getPalletAantal()) + Integer.parseInt(aantal), Integer.parseInt(pallet.getOrder_Id()));
            }
        }
        pallet.setAantal(Integer.parseInt(aantal));
        detailGrid.setItems(palletsList);
    }

    private void setID(long id) {
        rowId = id;
        laadCombobox(rowId);
    }

    private void reload(){
        orderList.clear();
        laadOrderPlanner();
    }

    private void addOnclick() {
        vrachtwagenBtn.addClickListener(event -> setVrachtwagen(String.valueOf(vrachtwagenBox.getValue()), rowId));
        chauffeurBtn.addClickListener(event -> setChauffeur(String.valueOf(chauffeurBox.getValue()), rowId));
        detailsBtn.addClickListener(event -> details());
    }

    /**
     * @param chauffeur = nieuwe chauffeur
     * @param rowId = order_Id
     */
    private void setChauffeur(String chauffeur, long rowId) {
        long chauffeur_Id = chauffeurRepository.getIdByUser_Id(userRepository.getIdByInlognaam(chauffeur));
        ritRepository.updateChauffeur_Id(chauffeur_Id, Integer.parseInt(orderRepository.getAllById(rowId).getRit_Id()));
        reload();
    }

    /**
     * @param vrachtwagen = nieuwe vrachtwagen
     * @param rowId = order_Id
     */
    private void setVrachtwagen(String vrachtwagen, long rowId) {
        long vrachtwagen_Id = vrachtwagenRepository.getIdByKenteken(vrachtwagen);
        ritRepository.updateVrachtwagen_Id(vrachtwagen_Id, Integer.parseInt(orderRepository.getAllById(rowId).getRit_Id()));
        reload();
    }

    private void details() {
        palletsList.clear();
        detailGrid.removeAllColumns();
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
        orderGrid = plannerUI.ordersGrid;
        detailGrid = plannerUI.palletsGrid;
        orderList = plannerUI.orderList;
        palletsList = plannerUI.palletsList;

        HorizontalLayout layout2 = plannerUI.buttons;
        layout2.addComponents(detailsBtn, send);

        VerticalLayout layout3 = new VerticalLayout();
        layout2.addComponents(chauffeurBox, chauffeurBtn, vrachtwagenBox, vrachtwagenBtn);
        gridLayout.addComponent(layout3);

        plannerUI.parent.addComponent(layout2);
    }

    /**
     * @param rowId = order_Id
     */
    private void laadCombobox(long rowId){
        List<String> chauffeurList = new ArrayList<>();
        Order order = orderRepository.getAllById(rowId);
        List<Long> chauffeurs = orderMaken.findChauffeur(Date.valueOf(order.getDatum()), Integer.parseInt(ritRepository.getById(Integer.parseInt(order.getRit_Id())).getVrachtwagen_Id()), order.getLand_Id());
        for(Long chauffeur_Id : chauffeurs){
            User user = userRepository.findAllById(chauffeurRepository.findUser_IdAllById(chauffeur_Id));
            chauffeurList.add(user.getInlognaam());
        }
        chauffeurBox.setItems(chauffeurList);
        List<String> vrachtwagenList = new ArrayList<>();
        long rit_Id = Integer.parseInt(order.getRit_Id());
        Rit rit = ritRepository.getById(rit_Id);
        List<Long> vrachtwagen = orderMaken.findVrachtwagen(Date.valueOf(order.getDatum()), Integer.parseInt(rit.getRuimte()), 1, 0);
        if(vrachtwagen != null) {
            for (Long vrachtwagen_Id : vrachtwagen) {
                vrachtwagenList.add(vrachtwagenRepository.getKentekenById(vrachtwagen_Id));
            }
        }
        vrachtwagenBox.setItems(vrachtwagenList);
    }

}

