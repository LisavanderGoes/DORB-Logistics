package com.lisa.dorb.atrash.crudUI;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;

@SpringComponent
public class KlantCrud extends VerticalLayout {
//
//    @Autowired
//    CrudUI crudUI;
//    @Autowired
//    KlantRepository repository;
//
//    public List<Klant> vrachtwagenList; //define inside methode otherwise null
//    public Button deleteBtn;
//    public Button toevoegenBtn;
//    private werkroosterGrid<Klant> werkroosterGrid;
//    private long rowId;
//    private Object rowItem;
//
//    public void addTable() {
//        vrachtwagenList = repository.findAll();
//        werkroosterGrid = crudUI.gridKlant;
//        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
//        toevoegenBtn = new Button("Toevoegen");
//
//        werkroosterGrid.setCaption("Klanten");
//        werkroosterGrid.setSizeFull();
//        werkroosterGrid.setSelectionMode(werkroosterGrid.SelectionMode.NONE);
//        ListDataProvider<Klant> dataProvider =
//                DataProvider.ofCollection(vrachtwagenList);
//
//        werkroosterGrid.setDataProvider(dataProvider);
//
//        TextField taskField1 = new TextField();
//        TextField taskField2 = new TextField();
//        TextField taskField3 = new TextField();
//        TextField taskField6 = new TextField();
//        TextField taskField4 = new TextField();
//        TextField taskField5 = new TextField();
//
//        werkroosterGrid.addColumn(Klant::getID)
//                .setCaption("Id")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Klant::getVoornaam)
//                .setEditorComponent(taskField1, this::setVoornaam)
//                .setCaption("Voornaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Klant::getTussenvoegsel)
//                .setEditorComponent(taskField2, this::setTussenvoegsel)
//                .setCaption("Tussenvoegsel")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Klant::getAchternaam)
//                .setEditorComponent(taskField3, this::setAchternaam)
//                .setCaption("Achternaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Klant::getRekeningnummer)
//                .setEditorComponent(taskField6, this::setRekeningnummer)
//                .setCaption("Rekeningnummer")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Klant::getUser_Id)
//                .setEditorComponent(taskField4, this::setUser_Id)
//                .setCaption("Inlognaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Klant::getWachtwoord)
//                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
//                .setCaption("Wachtwoord")
//                .setExpandRatio(2);
//
//        werkroosterGrid.setSelectionMode(werkroosterGrid.SelectionMode.SINGLE);
//
//        werkroosterGrid.addItemClickListener(event ->
//                setID(event.getItem().getID(), event.getItem()));
//
//        werkroosterGrid.getEditor().setEnabled(true);
//
//        crudUI.table.addComponentsAndExpand(werkroosterGrid);
//        toevoegenBtn.addClickListener(event -> {
//            toevoegen();
//        });
//        deleteBtn.addClickListener(event -> {
//            delete(rowId, rowItem);
//        });
//
//        crudUI.table.addComponents(toevoegenBtn, deleteBtn);
//        crudUI.parent.addComponentsAndExpand(crudUI.table);
//    }
//
//    private void setRekeningnummer(Klant klant, String rekeningnummer) {
//        long id = klant.getID();
//        repository.updateRekeningummer(rekeningnummer, id);
//        klant.setRekeningnummer(rekeningnummer);
//        crudUI.gridKlant.setItems(vrachtwagenList);
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
//            repository.updateRol(wachtwoord, id);
//            klant.setWachtwoord(wachtwoord);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setUser_Id(Klant klant, String inlognaam) {
//        String snd;
//        long id = klant.getID();
//        try {
//            repository.updateInlognaam(inlognaam, id);
//            klant.setUser_Id(inlognaam);
//            werkroosterGrid.setItems(vrachtwagenList);
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
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void setTussenvoegsel(Klant klant, String tussenvoegsel) {
//        long id = klant.getID();
//        repository.updateTussenvoegsel(tussenvoegsel, id);
//        klant.setTussenvoegsel(tussenvoegsel);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    public void setVoornaam(Klant klant, String voornaam){
//        long id = klant.getID();
//        repository.updateVoornaam(voornaam, id);
//        klant.setVoornaam(voornaam);
//        werkroosterGrid.setItems(vrachtwagenList);
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
//            vrachtwagenList.add(model);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void delete(long id, Object item) {
//        repository.deleteRow(id);
//        vrachtwagenList.remove(item);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private long getDBId() {
//        return repository.getId();
//    }

}
