package com.lisa.dorb.layout;

import com.lisa.dorb.layout.crudUI.*;
import com.lisa.dorb.model.DB.*;
import com.lisa.dorb.values.strings;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
public class CrudUI extends VerticalLayout implements View {

    public VerticalLayout parent;

    @Autowired
    LoginUI loginUI;
    @Autowired
    CrudUI crudUI;
    @Autowired
    AdminCrud adminCrud;
    @Autowired
    KlantCrud klantCrud;
    @Autowired
    ChauffeurCrud chauffeurCrud;
    @Autowired
    PlannerCrud plannerCrud;
    @Autowired
    ManagerCrud managerCrud;
    @Autowired
    OrderCrud orderCrud;
    @Autowired
    VrachtwagenCrud vrachtwagenCrud;
    @Autowired
    PrijsCrud prijsCrud;
    @Autowired
    PalletCrud palletCrud;
    @Autowired
    RitCrud ritCrud;

    //UI
    public VerticalLayout table = new VerticalLayout();


    public Grid<Admin> gridAdmin = new Grid<>();
    public Grid<Klant> gridKlant = new Grid<>();
    public Grid<Chauffeur> gridChauffeur = new Grid<>();
    public Grid<Planner> plannerGrid = new Grid<>();
    public Grid<Manager> managerGrid = new Grid<>();
    public Grid<Order> gridOrder = new Grid<>();
    public Grid<Vrachtwagen> vrachtwagenGrid = new Grid<>();
    public Grid<Prijs> prijsGrid = new Grid<>();
    public Grid<Pallet> palletGrid = new Grid<>();
    public Grid<Rit> ritGrid = new Grid<>();

    public Button crudAdminBtn = new Button(strings.ADMIN);
    public Button crudKlantBtn = new Button(strings.KLANT);
    public Button crudChauffeurBtn = new Button(strings.CHAUFFEUR);
    public Button crudPlannerBtn = new Button(strings.PLANNER);
    public Button crudManagerBtn = new Button(strings.MANAGER);
    public Button crudOrderBtn = new Button("Order");
    public Button crudVrachtwagenBtn = new Button("Vrachtwagen");
    public Button crudPrijsBtn = new Button("Prijs");
    public Button crudPalletBtn = new Button("Pallet");
    public Button crudRitBtn = new Button("Rit");
    public Button terugBtn = new Button("Terug");
    public Label send = new Label("");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        startUI(strings.ADMIN);
    }

    private void addHeader() {
        Label header = new Label("ADMINISTRATION");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        HorizontalLayout buttons = new HorizontalLayout();
        crudAdminBtn.addClickListener(event -> startUI(strings.ADMIN));
        crudKlantBtn.addClickListener(event -> startUI(strings.KLANT));
        crudChauffeurBtn.addClickListener(event -> startUI(strings.CHAUFFEUR));
        crudPlannerBtn.addClickListener(event -> startUI(strings.PLANNER));
        crudManagerBtn.addClickListener(event -> startUI(strings.MANAGER));
        crudVrachtwagenBtn.addClickListener(event -> startUI("Vrachtwagen"));
        crudOrderBtn.addClickListener(event -> startUI("Order"));
        crudPrijsBtn.addClickListener(event -> startUI("Prijs"));
        crudPalletBtn.addClickListener(event -> startUI("Pallet"));
        crudRitBtn.addClickListener(event -> startUI("Rit"));
        terugBtn.addClickListener(event -> terugButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttons.addComponentsAndExpand(crudAdminBtn, crudKlantBtn, crudChauffeurBtn, crudPlannerBtn, crudManagerBtn, crudOrderBtn, crudVrachtwagenBtn, crudPrijsBtn, crudPalletBtn, crudRitBtn, terugBtn);
        parent.addComponent(buttons);
        parent.addComponent(send);
    }

    private void terugButtonClick() {
        getUI().setContent(loginUI);
    }

    private void startUI(String ui){
        removeAll();
        switch (ui) {
//            case strings.ADMIN:
//                adminCrud.addTable();
//                break;
//            case strings.KLANT:
//                klantCrud.addTable();
//                break;
//            case strings.CHAUFFEUR:
//                chauffeurCrud.addTable();
//                break;
//            case strings.PLANNER:
//                plannerCrud.addTable();
//                break;
//            case strings.MANAGER:
//                managerCrud.addTable();
//                break;
            case "Order":
                orderCrud.addTable();
                break;
            case "Vrachtwagen":
                vrachtwagenCrud.addTable();
                break;
            case "Prijs":
                prijsCrud.addTable();
                break;
            case "Pallet":
                palletCrud.addTable();
                break;
            case "Rit":
                ritCrud.addTable();
                break;
            default:
                break;
        }
    }

    private void removeAll(){
        table.removeAllComponents();
        gridAdmin.removeAllColumns();
        gridKlant.removeAllColumns();
        gridChauffeur.removeAllColumns();
        plannerGrid.removeAllColumns();
        managerGrid.removeAllColumns();
        gridOrder.removeAllColumns();
        palletGrid.removeAllColumns();
        prijsGrid.removeAllColumns();
        ritGrid.removeAllColumns();
        vrachtwagenGrid.removeAllColumns();
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
