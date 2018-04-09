package com.lisa.dorb.layout;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.crudUI.AdminCrud;
import com.lisa.dorb.layout.crudUI.ChauffeurCrud;
import com.lisa.dorb.layout.crudUI.KlantCrud;
import com.lisa.dorb.model.Admin;
import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.model.Klant;
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
    Crud crud;

    //UI
    public HorizontalLayout table = new HorizontalLayout();
    public Grid<Admin> gridAdmin = new Grid<>();
    public Grid<Klant> gridKlant = new Grid<>();
    public Grid<Chauffeur> gridChauffeur = new Grid<>();

    public Button crudAdminBtn = new Button(strings.ADMIN);
    public Button crudKlantBtn = new Button(strings.KLANT);
    public Button crudChauffeurBtn = new Button(strings.CHAUFFEUR);
    public Button crudPlannerBtn = new Button(strings.PLANNER);
    public Button terugBtn = new Button("Terug");
    public Button addBtn = new Button("Toevoegen");
    public Button test = new Button("test");
    public Label send = new Label("");
    public Button deleteBtn= new Button("Verwijderen");
    public long rowId;
    public Object rowItem;

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        startUI(strings.ADMIN);
    }

    private void test(){
        table.addComponentsAndExpand(test);
        parent.addComponentsAndExpand(table);
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
        terugBtn.addClickListener(event -> terugButtonClick());
        buttons.addComponentsAndExpand(crudAdminBtn, crudKlantBtn, crudChauffeurBtn, crudPlannerBtn, terugBtn);
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
                break;
            default:
                break;
        }
    }

    private void removeAll(){
        for (int i = 0; i < table.getComponentCount(); i++) {
            Component c = table.getComponent(i);
            table.removeComponent(c);
        }
        gridAdmin.removeAllColumns();
        gridKlant.removeAllColumns();
        gridChauffeur.removeAllColumns();
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
