package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Klant;
import com.lisa.dorb.repository.KlantRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class KlantCrud extends VerticalLayout {
//
//    @Autowired
//    CrudUI crudUI;
//    @Autowired
//    KlantRepository repository;
//
//    public List<Klant> list; //define inside methode otherwise null
//    public Button deleteBtn;
//    public Button addBtn;
//    private Grid<Klant> grid;
//    private long rowId;
//    private Object rowItem;
//
//    public void addTable() {
//        list = repository.findAll();
//        grid = crudUI.gridKlant;
//        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
//        addBtn = new Button("Toevoegen");
//
//        grid.setCaption("Klanten");
//        grid.setSizeFull();
//        grid.setSelectionMode(Grid.SelectionMode.NONE);
//        ListDataProvider<Klant> dataProvider =
//                DataProvider.ofCollection(list);
//
//        grid.setDataProvider(dataProvider);
//
//        TextField taskField1 = new TextField();
//        TextField taskField2 = new TextField();
//        TextField taskField3 = new TextField();
//        TextField taskField6 = new TextField();
//        TextField taskField4 = new TextField();
//        TextField taskField5 = new TextField();
//
//        grid.addColumn(Klant::getID)
//                .setCaption("Id")
//                .setExpandRatio(2);
//
//        grid.addColumn(Klant::getVoornaam)
//                .setEditorComponent(taskField1, this::setVoornaam)
//                .setCaption("Voornaam")
//                .setExpandRatio(2);
//
//        grid.addColumn(Klant::getTussenvoegsel)
//                .setEditorComponent(taskField2, this::setTussenvoegsel)
//                .setCaption("Tussenvoegsel")
//                .setExpandRatio(2);
//
//        grid.addColumn(Klant::getAchternaam)
//                .setEditorComponent(taskField3, this::setAchternaam)
//                .setCaption("Achternaam")
//                .setExpandRatio(2);
//
//        grid.addColumn(Klant::getRekeningnummer)
//                .setEditorComponent(taskField6, this::setRekeningnummer)
//                .setCaption("Rekeningnummer")
//                .setExpandRatio(2);
//
//        grid.addColumn(Klant::getInlognaam)
//                .setEditorComponent(taskField4, this::setInlognaam)
//                .setCaption("Inlognaam")
//                .setExpandRatio(2);
//
//        grid.addColumn(Klant::getWachtwoord)
//                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
//                .setCaption("Wachtwoord")
//                .setExpandRatio(2);
//
//        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
//
//        grid.addItemClickListener(event ->
//                setID(event.getItem().getID(), event.getItem()));
//
//        grid.getEditor().setEnabled(true);
//
//        crudUI.table.addComponentsAndExpand(grid);
//        addBtn.addClickListener(event -> {
//            toevoegen();
//        });
//        deleteBtn.addClickListener(event -> {
//            delete(rowId, rowItem);
//        });
//
//        crudUI.table.addComponents(addBtn, deleteBtn);
//        crudUI.parent.addComponentsAndExpand(crudUI.table);
//    }
//
//    private void setRekeningnummer(Klant klant, String rekeningnummer) {
//        long id = klant.getID();
//        repository.updateRekeningummer(rekeningnummer, id);
//        klant.setRekeningnummer(rekeningnummer);
//        crudUI.gridKlant.setItems(list);
//    }
//
//    private void setID(long id, Object item) {
//        rowId = id;
//        rowItem = item;
//    }
//
//    private void setWachtwoord(Klant klant, String wachtwoord) {
//        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
//        long id = klant.getID();
//        String snd;
//        try {
//            repository.updateWachtwoord(wachtwoord, id);
//            klant.setWachtwoord(wachtwoord);
//            grid.setItems(list);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setInlognaam(Klant klant, String inlognaam) {
//        String snd;
//        long id = klant.getID();
//        try {
//            repository.updateInlognaam(inlognaam, id);
//            klant.setInlognaam(inlognaam);
//            grid.setItems(list);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setAchternaam(Klant klant, String achternaam) {
//        long id = klant.getID();
//        repository.updateAchternaam(achternaam, id);
//        klant.setAchternaam(achternaam);
//        grid.setItems(list);
//    }
//
//    private void setTussenvoegsel(Klant klant, String tussenvoegsel) {
//        long id = klant.getID();
//        repository.updateTussenvoegsel(tussenvoegsel, id);
//        klant.setTussenvoegsel(tussenvoegsel);
//        grid.setItems(list);
//    }
//
//    public void setVoornaam(Klant klant, String voornaam){
//        long id = klant.getID();
//        repository.updateVoornaam(voornaam, id);
//        klant.setVoornaam(voornaam);
//        grid.setItems(list);
//    }
//
//    private void test(String s){
//        if(s == "b") {
//            crudUI.send.setValue(crudUI.send.getValue() + "D   ");
//        }
//    }
//
//    private void toevoegen() { //set er 2 keer onclick op in deze scope. dus haal onclick ff weg ofzo
//        String snd;
//        try {
//            repository.addRow();
//            long id = getDBId();
//            Klant model = new Klant(id, "", "", "", "", "", "");
//            list.add(model);
//            grid.setItems(list);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void delete(long id, Object item) {
//        repository.deleteRow(id);
//        list.remove(item);
//        grid.setItems(list);
//    }
//
//    private long getDBId() {
//        return repository.getId();
//    }

}
