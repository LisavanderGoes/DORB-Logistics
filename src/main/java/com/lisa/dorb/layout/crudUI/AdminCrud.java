package com.lisa.dorb.layout.crudUI;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;

@SpringComponent
public class AdminCrud extends VerticalLayout {

//    @Autowired
//    CrudUI crudUI;
//    @Autowired
//    AdminRepository repository;
//
//    private List<Admin> list; //define inside methode otherwise null
//    private Button deleteBtn;
//    private Button addBtn;
//    private Grid<Admin> Grid;
//    private long rowId;
//    private Object rowItem;
//
//    public void addTable() {
//        list = repository.findAll();
//        Grid = crudUI.gridAdmin;
//        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
//        addBtn = new Button("Toevoegen");
//
//        Grid.setCaption("Admins");
//        Grid.setSizeFull();
//        Grid.setSelectionMode(Grid.SelectionMode.NONE);
//        ListDataProvider<Admin> dataProvider =
//                DataProvider.ofCollection(list);
//
//        Grid.setDataProvider(dataProvider);
//
//        TextField taskField1 = new TextField();
//        TextField taskField2 = new TextField();
//        TextField taskField3 = new TextField();
//        TextField taskField4 = new TextField();
//        TextField taskField5 = new TextField();
//
//        Grid.addColumn(Admin::getID)
//                .setCaption("Id")
//                .setExpandRatio(2);
//
//        Grid.addColumn(Admin::getVoornaam)
//                .setEditorComponent(taskField1, this::setVoornaam)
//                .setCaption("Voornaam")
//                .setExpandRatio(2);
//
//        Grid.addColumn(Admin::getTussenvoegsel)
//                .setEditorComponent(taskField2, this::setTussenvoegsel)
//                .setCaption("Tussenvoegsel")
//                .setExpandRatio(2);
//
//        Grid.addColumn(Admin::getAchternaam)
//                .setEditorComponent(taskField3, this::setAchternaam)
//                .setCaption("Achternaam")
//                .setExpandRatio(2);
//
//        Grid.addColumn(Admin::getInlognaam)
//                .setEditorComponent(taskField4, this::setInlognaam)
//                .setCaption("Inlognaam")
//                .setExpandRatio(2);
//
//        Grid.addColumn(Admin::getWachtwoord)
//                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
//                .setCaption("Wachtwoord")
//                .setExpandRatio(2);
//
//        Grid.setSelectionMode(Grid.SelectionMode.SINGLE);
//
//        Grid.addItemClickListener(event ->
//                setID(event.getItem().getID(), event.getItem()));
//
//        Grid.getEditor().setEnabled(true);
//
//        crudUI.table.addComponentsAndExpand(Grid);
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
//            repository.updateWachtwoord(wachtwoord, id);
//            admin.setWachtwoord(wachtwoord);
//            Grid.setItems(list);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//    private void setInlognaam(Admin admin, String inlognaam) {
//        String snd;
//        long id = admin.getID();
//        try {
//            repository.updateInlognaam(inlognaam, id);
//            admin.setInlognaam(inlognaam);
//            Grid.setItems(list);
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
//        Grid.setItems(list);
//    }
//
//    private void setTussenvoegsel(Admin admin, String tussenvoegsel) {
//        long id = admin.getID();
//        repository.updateTussenvoegsel(tussenvoegsel, id);
//        admin.setTussenvoegsel(tussenvoegsel);
//        Grid.setItems(list);
//    }
//
//    public void setVoornaam(Admin admin, String voornaam) {
//        long id = admin.getID();
//        repository.updateVoornaam(voornaam, id);
//        admin.setVoornaam(voornaam);
//        Grid.setItems(list);
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
//            list.add(admin);
//            Grid.setItems(list);
//            snd = "";
//        } catch (Exception e) {
//            snd = "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
//        }
//        crudUI.send.setValue(snd);
//    }
//
//        private void delete (long id, Object item){
//            repository.deleteRow(id);
//            list.remove(item);
//            Grid.setItems(list);
//        }
//
//        private long getDBId() {
//            return repository.getId();
//        }
//

}
