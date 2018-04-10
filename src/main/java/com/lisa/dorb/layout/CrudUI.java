package com.lisa.dorb.layout;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.crudUI.*;
import com.lisa.dorb.model.*;
import com.lisa.dorb.values.strings;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.List;

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
    Crud crud;

    //UI
    public VerticalLayout table = new VerticalLayout();


    public Grid<Admin> gridAdmin = new Grid<>();
    public Grid<Klant> gridKlant = new Grid<>();
    public Grid<Chauffeur> gridChauffeur = new Grid<>();
    public Grid<Planner> plannerGrid = new Grid<>();
    public Grid<Manager> managerGrid = new Grid<>();

    public Button crudAdminBtn = new Button(strings.ADMIN);
    public Button crudKlantBtn = new Button(strings.KLANT);
    public Button crudChauffeurBtn = new Button(strings.CHAUFFEUR);
    public Button crudPlannerBtn = new Button(strings.PLANNER);
    public Button crudManagerBtn = new Button(strings.MANAGER);
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
        Label header = new Label("ADMIN");
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
        terugBtn.addClickListener(event -> terugButtonClick());
        buttons.addComponentsAndExpand(crudAdminBtn, crudKlantBtn, crudChauffeurBtn, crudPlannerBtn, crudManagerBtn, terugBtn);
        parent.addComponent(buttons);
        parent.addComponent(send);
    }

    private void terugButtonClick() {
        getUI().setContent(loginUI);
    }

    private void startUI(String ui){
        removeAll();
        switch (ui) {
            case strings.ADMIN:
                adminCrud.addTable();
                break;
            case strings.KLANT:
                klantCrud.addTable();
                break;
            case strings.CHAUFFEUR:
                chauffeurCrud.addTable();
                break;
            case strings.PLANNER:
                plannerCrud.addTable();
                break;
            case strings.MANAGER:
                managerCrud.addTable();
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
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
