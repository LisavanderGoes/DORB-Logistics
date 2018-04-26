package com.lisa.dorb.atrash.crudUI;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;

@SpringComponent
public class ChauffeurCrud extends VerticalLayout {

//    @Autowired
//    CrudUI crudUI;
//    @Autowired
//    ChauffeurRepository repository;
//
//    public List<chauffeur> vrachtwagenList; //define inside methode otherwise null
//    private Button deleteBtn;
//    private Button toevoegenBtn;
//    private werkroosterGrid<chauffeur> werkroosterGrid;
//    private long rowId;
//    private Object rowItem;
//
//    public void addTable() {
//        vrachtwagenList = repository.findAll();
//        werkroosterGrid = crudUI.gridChauffeur;
//        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
//        toevoegenBtn = new Button("Toevoegen");
//
//        werkroosterGrid.setCaption("Chauffeurs");
//        werkroosterGrid.setSizeFull();
//        werkroosterGrid.setSelectionMode(werkroosterGrid.SelectionMode.NONE);
//        ListDataProvider<chauffeur> dataProvider =
//                DataProvider.ofCollection(vrachtwagenList);
//
//        werkroosterGrid.setDataProvider(dataProvider);
//
//        TextField taskField1 = new TextField();
//        TextField taskField2 = new TextField();
//        TextField taskField3 = new TextField();
//        TextField taskField4 = new TextField();
//        TextField taskField5 = new TextField();
//        TextField taskField6 = new TextField();
//        TextField taskField7 = new TextField();
//
//        werkroosterGrid.addColumn(chauffeur::getID)
//                .setCaption("Id")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getVoornaam)
//                .setEditorComponent(taskField1, this::setVoornaam)
//                .setCaption("Voornaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getTussenvoegsel)
//                .setEditorComponent(taskField2, this::setTussenvoegsel)
//                .setCaption("Tussenvoegsel")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getAchternaam)
//                .setEditorComponent(taskField3, this::setAchternaam)
//                .setCaption("Achternaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getRijbewijs)
//                .setEditorComponent(taskField6, this::setRijbewijs)
//                .setCaption("Rijbewijs")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getWerkdagen)
//                .setEditorComponent(taskField7, this::setWerkdagen)
//                .setCaption("Werkdagen")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getUser_Id)
//                .setEditorComponent(taskField4, this::setUser_Id)
//                .setCaption("Inlognaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(chauffeur::getWachtwoord)
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
//    private void setID(long id, Object item) {
//        rowId = id;
//        rowItem = item;
//    }
//
//    private void setWachtwoord(chauffeur model, String wachtwoord) {
//        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
//        long id = model.getID();
//        String snd;
//        try {
//            repository.updateRol(wachtwoord, id);
//            model.setWachtwoord(wachtwoord);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setUser_Id(chauffeur model, String inlognaam) {
//        String snd;
//        long id = model.getID();
//        try {
//            repository.updateInlognaam(inlognaam, id);
//            model.setUser_Id(inlognaam);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setAchternaam(chauffeur model, String achternaam) {
//        long id = model.getID();
//        repository.updateAchternaam(achternaam, id);
//        model.setAchternaam(achternaam);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void setTussenvoegsel(chauffeur model, String tussenvoegsel) {
//        long id = model.getID();
//        repository.updateTussenvoegsel(tussenvoegsel, id);
//        model.setTussenvoegsel(tussenvoegsel);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    public void setVoornaam(chauffeur model, String voornaam){
//        long id = model.getID();
//        repository.updateVoornaam(voornaam, id);
//        model.setVoornaam(voornaam);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    public void setRijbewijs(chauffeur model, String rijbewijs){
//        String snd;
//        long id = model.getID();
//        try {
//            repository.updateRijbewijs(rijbewijs, id);
//            model.setRijbewijs(rijbewijs);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        }catch (Exception e) {
//            snd = "Rijbewijs kan alleen C of D zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    public void setWerkdagen(chauffeur model, String werkdagen){
//        Long newWerkdagen = Long.parseLong(werkdagen);
//        long id = model.getID();
//        repository.updateWerkdagen(newWerkdagen, id);
//        model.setWerkdagen(newWerkdagen);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void toevoegen() {
//        String snd;
//        try {
//            repository.addRow();
//            long i = getDBId();
//            chauffeur model = new chauffeur(i, "", "", "", "", 0, "", "");
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
