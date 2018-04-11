package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.repository.ChauffeurRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class ChauffeurCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    ChauffeurRepository repository;

    public List<Chauffeur> list; //define inside methode otherwise null
    private Button deleteBtn;
    private Button addBtn;
    private Grid<Chauffeur> grid;
    private long rowId;
    private Object rowItem;

    public void addTable() {
        list = repository.findAll();
        grid = crudUI.gridChauffeur;
        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
        addBtn = new Button("Toevoegen");

        grid.setCaption("Chauffeurs");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Chauffeur> dataProvider =
                DataProvider.ofCollection(list);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();
        TextField taskField6 = new TextField();
        TextField taskField7 = new TextField();

        grid.addColumn(Chauffeur::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getRijbewijs)
                .setEditorComponent(taskField6, this::setRijbewijs)
                .setCaption("Rijbewijs")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getWerkdagen)
                .setEditorComponent(taskField7, this::setWerkdagen)
                .setCaption("Werkdagen")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getWachtwoord)
                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
                .setCaption("Wachtwoord")
                .setExpandRatio(2);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        grid.getEditor().setEnabled(true);

        crudUI.table.addComponentsAndExpand(grid);
        addBtn.addClickListener(event -> {
            toevoegen();
        });
        deleteBtn.addClickListener(event -> {
            delete(rowId, rowItem);
        });

        crudUI.table.addComponents(addBtn, deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setID(long id, Object item) {
        rowId = id;
        rowItem = item;
    }

    private void setWachtwoord(Chauffeur model, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        long id = model.getID();
        String snd;
        try {
            repository.updateWachtwoord(wachtwoord, id);
            model.setWachtwoord(wachtwoord);
            grid.setItems(list);
            snd = "";
        } catch (Exception e) {
            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        crudUI.send.setValue(snd);
    }

    private void setInlognaam(Chauffeur model, String inlognaam) {
        String snd;
        long id = model.getID();
        try {
            repository.updateInlognaam(inlognaam, id);
            model.setInlognaam(inlognaam);
            grid.setItems(list);
            snd = "";
        } catch (Exception e) {
            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        crudUI.send.setValue(snd);
    }

    private void setAchternaam(Chauffeur model, String achternaam) {
        long id = model.getID();
        repository.updateAchternaam(achternaam, id);
        model.setAchternaam(achternaam);
        grid.setItems(list);
    }

    private void setTussenvoegsel(Chauffeur model, String tussenvoegsel) {
        long id = model.getID();
        repository.updateTussenvoegsel(tussenvoegsel, id);
        model.setTussenvoegsel(tussenvoegsel);
        grid.setItems(list);
    }

    public void setVoornaam(Chauffeur model, String voornaam){
        long id = model.getID();
        repository.updateVoornaam(voornaam, id);
        model.setVoornaam(voornaam);
        grid.setItems(list);
    }

    public void setRijbewijs(Chauffeur model, String rijbewijs){
        String snd;
        long id = model.getID();
        try {
            repository.updateRijbewijs(rijbewijs, id);
            model.setRijbewijs(rijbewijs);
            grid.setItems(list);
            snd = "";
        }catch (Exception e) {
            snd = "Rijbewijs kan alleen C of D zijn!";
        }
        crudUI.send.setValue(snd);
    }

    public void setWerkdagen(Chauffeur model, String werkdagen){
        Long newWerkdagen = Long.parseLong(werkdagen);
        long id = model.getID();
        repository.updateWerkdagen(newWerkdagen, id);
        model.setWerkdagen(newWerkdagen);
        grid.setItems(list);
    }

    private void toevoegen() {
        String snd;
        try {
            repository.addRow();
            long i = getDBId();
            Chauffeur model = new Chauffeur(i, "", "", "", "", 0, "", "");
            list.add(model);
            grid.setItems(list);
            snd = "";
        } catch (Exception e) {
            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        crudUI.send.setValue(snd);
    }

    private void delete(long id, Object item) {
        repository.deleteRow(id);
        list.remove(item);
        grid.setItems(list);
    }

    private long getDBId() {
        return repository.getId();
    }

}