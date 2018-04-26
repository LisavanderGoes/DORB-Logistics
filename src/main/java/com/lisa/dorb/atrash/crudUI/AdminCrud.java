package com.lisa.dorb.atrash.crudUI;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;

@SpringComponent
public class AdminCrud extends VerticalLayout {

//    @Autowired
//    CrudUI crudUI;
//    @Autowired
//    AdminRepository repository;
//
//    private List<Admin> vrachtwagenList; //define inside methode otherwise null
//    private Button deleteBtn;
//    private Button toevoegenBtn;
//    private werkroosterGrid<Admin> werkroosterGrid;
//    private long rowId;
//    private Object rowItem;
//
//    public void addTable() {
//        vrachtwagenList = repository.findAll();
//        werkroosterGrid = crudUI.gridAdmin;
//        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
//        toevoegenBtn = new Button("Toevoegen");
//
//        werkroosterGrid.setCaption("Admins");
//        werkroosterGrid.setSizeFull();
//        werkroosterGrid.setSelectionMode(werkroosterGrid.SelectionMode.NONE);
//        ListDataProvider<Admin> dataProvider =
//                DataProvider.ofCollection(vrachtwagenList);
//
//        werkroosterGrid.setDataProvider(dataProvider);
//
//        TextField taskField1 = new TextField();
//        TextField taskField2 = new TextField();
//        TextField taskField3 = new TextField();
//        TextField taskField4 = new TextField();
//        TextField taskField5 = new TextField();
//
//        werkroosterGrid.addColumn(Admin::getID)
//                .setCaption("Id")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Admin::getVoornaam)
//                .setEditorComponent(taskField1, this::setVoornaam)
//                .setCaption("Voornaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Admin::getTussenvoegsel)
//                .setEditorComponent(taskField2, this::setTussenvoegsel)
//                .setCaption("Tussenvoegsel")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Admin::getAchternaam)
//                .setEditorComponent(taskField3, this::setAchternaam)
//                .setCaption("Achternaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Admin::getUser_Id)
//                .setEditorComponent(taskField4, this::setUser_Id)
//                .setCaption("Inlognaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Admin::getWachtwoord)
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
//    private void setWachtwoord(Admin admin, String wachtwoord) {
//        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
//        long id = admin.getID();
//        String snd;
//        try {
//            repository.updateRol(wachtwoord, id);
//            admin.setWachtwoord(wachtwoord);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setUser_Id(Admin admin, String inlognaam) {
//        String snd;
//        long id = admin.getID();
//        try {
//            repository.updateInlognaam(inlognaam, id);
//            admin.setUser_Id(inlognaam);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setAchternaam(Admin admin, String achternaam) {
//        long id = admin.getID();
//        repository.updateAchternaam(achternaam, id);
//        admin.setAchternaam(achternaam);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void setTussenvoegsel(Admin admin, String tussenvoegsel) {
//        long id = admin.getID();
//        repository.updateTussenvoegsel(tussenvoegsel, id);
//        admin.setTussenvoegsel(tussenvoegsel);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    public void setVoornaam(Admin admin, String voornaam) {
//        long id = admin.getID();
//        repository.updateVoornaam(voornaam, id);
//        admin.setVoornaam(voornaam);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void test(String s){
//        if(s == "a") {
//            crudUI.send.setValue(crudUI.send.getValue() + "L   ");
//        }
//    }
//
//    private void toevoegen() {
//        String snd;
//        try {
//            repository.addRow();
//            long id = getDBId();
//            Admin admin = new Admin(id, "", "", "", "", "");
//            vrachtwagenList.add(admin);
//            werkroosterGrid.setItems(vrachtwagenList);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//        private void delete (long id, Object item){
//            repository.deleteRow(id);
//            vrachtwagenList.remove(item);
//            werkroosterGrid.setItems(vrachtwagenList);
//        }
//
//        private long getDBId() {
//            return repository.getId();
//        }
//

}
