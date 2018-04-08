package com.lisa.dorb.layout;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.model.Manager;
import com.lisa.dorb.values.strings;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
public class CrudManagerUI extends VerticalLayout implements View {

    private VerticalLayout parent;
    HorizontalLayout table = new HorizontalLayout();

    @Autowired
    LoginUI loginUI;
    @Autowired
    CrudManagerUI crudManagerUI;
    @Autowired
    Crud crud;

    //UI
    private Button crudManagerBtn = new Button(strings.MANAGER);
    private Button crudKlantBtn = new Button(strings.KLANT);
    private Button crudChauffeurBtn = new Button(strings.CHAUFFEUR);
    private Button crudPlannerBtn = new Button(strings.PLANNER);
    private Button terugBtn = new Button("Terug");
    private Button addBtn = new Button("Toevoegen");
    private Button test = new Button("test");
    public Label send = new Label("");
    private List<Manager> managerList;
    private Grid<Manager> grid = new Grid<>();
    private Button deleteBtn= new Button("Verwijderen");
    private long rowId;
    private Object rowItem;

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        loadList();
        addLayout();
        startUI(strings.MANAGER);
    }

    private void test(){
        table.addComponentsAndExpand(test);
        parent.addComponentsAndExpand(table);
    }

    private void loadList() {
        managerList = crud.managerList();
    }

    private void addTableManagers() {
        crudManagerBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        grid.setCaption("Managers");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Manager> dataProvider =
                DataProvider.ofCollection(managerList);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        grid.addColumn(Manager::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Manager::getVoornaam)
                .setEditorComponent(taskField1, this::setManagerVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        grid.addColumn(Manager::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setManagerTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        grid.addColumn(Manager::getAchternaam)
                .setEditorComponent(taskField3, this::setManagerAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        grid.addColumn(Manager::getInlognaam)
                .setEditorComponent(taskField4, this::setManagerInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        grid.addColumn(Manager::getWachtwoord)
                .setEditorComponent(taskField5, this::setManagerWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
                .setCaption("Wachtwoord")
                .setExpandRatio(2);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        grid.getEditor().setEnabled(true);

        table.addComponentsAndExpand(grid);
        HorizontalLayout buttons = new HorizontalLayout();
        addBtn.addClickListener(event -> {
            toevoegen();
        });
        deleteBtn.addClickListener(event -> {
            delete(rowId, rowItem);
        });
        buttons.addComponents(addBtn, deleteBtn);
        parent.addComponent(buttons);
        parent.addComponentsAndExpand(table);
    }

    private void setID(long id, Object item) {
        rowId = id;
        rowItem = item;
    }

    private void setManagerWachtwoord(Manager manager, String s) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = crud.updateManagerWachtwoord(manager, s);
        if(snd == null) {
            manager.setWachtwoord(s);
            grid.setItems(managerList);
            send.setValue("");
        }else {
            send.setValue(snd);
        }
    }

    private void setManagerInlognaam(Manager manager, String s) {
        String snd = crud.updateManagerInlognaam(manager, s);
        if(snd == null) {
            manager.setInlognaam(s);
            grid.setItems(managerList);
            send.setValue("");
        }else {
            send.setValue(snd);
        }
    }

    private void setManagerAchternaam(Manager manager, String s) {
        crud.updateManagerAchternaam(manager, s);
        manager.setAchternaam(s);
        grid.setItems(managerList);
    }

    private void setManagerTussenvoegsel(Manager manager, String s) {
        crud.updateManagerTussenvoegsel(manager, s);
        manager.setTussenvoegsel(s);
        grid.setItems(managerList);
    }

    public void setManagerVoornaam(Manager manager, String s){
        crud.updateManagerVoornaam(manager, s);
        manager.setVoornaam(s);
        grid.setItems(managerList);
    }

    private void toevoegen() {
        String snd = crud.addManagerRow();
        if(snd == null) {
            long id = crud.getManagerId();
            Manager manager = new Manager(id, "", "", "", "", "");
            managerList.add(manager);
            grid.setItems(managerList);
            send.setValue("");
        }else {
            send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        crud.deleteManagerRow(id);
        managerList.remove(item);
        grid.setItems(managerList);
    }

    private void addHeader() {
        Label header = new Label("MANAGER");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        HorizontalLayout buttons = new HorizontalLayout();
        crudManagerBtn.addClickListener(event -> startUI(strings.MANAGER));
        crudKlantBtn.addClickListener(event -> startUI(strings.KLANT));
        crudChauffeurBtn.addClickListener(event -> startUI(strings.CHAUFFEUR));
        crudPlannerBtn.addClickListener(event -> startUI(strings.PLANNER));
        terugBtn.addClickListener(event -> terugButtonClick());
        buttons.addComponentsAndExpand(crudManagerBtn, crudKlantBtn, crudChauffeurBtn, crudPlannerBtn, terugBtn);
        parent.addComponent(buttons);
        parent.addComponent(send);
    }

    private void terugButtonClick() {
        getUI().setContent(loginUI);
    }

    private void startUI(String ui){
        removeAll();
        switch (ui) {
            case strings.MANAGER:
                addTableManagers();
                break;
            case strings.KLANT:
                test();
                break;
            case strings.CHAUFFEUR:
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
        grid.removeAllColumns();
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
