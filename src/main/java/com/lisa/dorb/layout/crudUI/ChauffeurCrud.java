package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Chauffeur;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class ChauffeurCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    Crud crud;

    public List<Chauffeur> chauffeurList; //define inside methode otherwise null
    private Grid<Chauffeur> grid;

    public void addTable() {
        chauffeurList = crud.chauffeursList();
        grid = crudUI.gridChauffeur;
        grid.setCaption("Chauffeurs");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Chauffeur> dataProvider =
                DataProvider.ofCollection(chauffeurList);

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
        crudUI.addBtn.addClickListener(event -> {
            toevoegen();
        });
        crudUI.deleteBtn.addClickListener(event -> {
            delete(crudUI.rowId, crudUI.rowItem);
        });

        crudUI.parent.addComponents(crudUI.addBtn, crudUI.deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setID(long id, Object item) {
        crudUI.rowId = id;
        crudUI.rowItem = item;
    }

    private void setWachtwoord(Chauffeur model, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = crud.updateChauffeurWachtwoord(model, wachtwoord);
        if(snd == null) {
            model.setWachtwoord(wachtwoord);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Chauffeur model, String inlognaam) {
        String snd = crud.updateChauffeurInlognaam(model, inlognaam);
        if(snd == null) {
            model.setInlognaam(inlognaam);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Chauffeur model, String achternaam) {
        crud.updateChauffeurAchternaam(model, achternaam);
        model.setAchternaam(achternaam);
        grid.setItems(chauffeurList);
    }

    private void setTussenvoegsel(Chauffeur model, String tussenvoegsel) {
        crud.updateChauffeurTussenvoegsel(model, tussenvoegsel);
        model.setTussenvoegsel(tussenvoegsel);
        grid.setItems(chauffeurList);
    }

    public void setVoornaam(Chauffeur model, String voornaam){
        crud.updateChauffeurVoornaam(model, voornaam);
        model.setVoornaam(voornaam);
        grid.setItems(chauffeurList);
    }

    public void setRijbewijs(Chauffeur model, String rijbewijs){
        String snd = crud.updateChauffeurRijbewijs(model, rijbewijs);
        if(snd == null) {
            model.setRijbewijs(rijbewijs);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    public void setWerkdagen(Chauffeur model, String werkdagen){
        Long newWerkdagen = Long.parseLong(werkdagen);
        crud.updateChauffeurWerkdagen(model, newWerkdagen);
        model.setWerkdagen(newWerkdagen);
        grid.setItems(chauffeurList);
    }

    private void toevoegen() {
        String snd = crud.addChauffeurRow();
        if(snd == null) {
            long id = crud.getChauffeurId();
            Chauffeur admin = new Chauffeur(id, "", "", "", "", 0, "", "");
            chauffeurList.add(admin);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        crud.deleteChauffeurRow(id);
        chauffeurList.remove(item);
        grid.setItems(chauffeurList);
    }
}
