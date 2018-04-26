package com.lisa.dorb.atrash.crudUI;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;

@SpringComponent
public class PlannerCrud extends VerticalLayout {
//
//    @Autowired
//    CrudUI crudUI;
//    @Autowired
//    PlannerRepository repository;
//
//
//    private List<Planner> vrachtwagenList; //define inside methode otherwise null
//    private Button deleteBtn;
//    private Button toevoegenBtn;
//    private werkroosterGrid<Planner> werkroosterGrid;
//    private long rowId;
//    private Object rowItem;
//
//    public void addTable() {
//        vrachtwagenList = repository.findAll();
//        werkroosterGrid = crudUI.plannerGrid;
//        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
//        toevoegenBtn = new Button("Toevoegen");
//
//
//        werkroosterGrid.setCaption("Planners");
//        werkroosterGrid.setSizeFull();
//        werkroosterGrid.setSelectionMode(werkroosterGrid.SelectionMode.NONE);
//        ListDataProvider<Planner> dataProvider =
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
//        werkroosterGrid.addColumn(Planner::getID)
//                .setCaption("Id")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Planner::getVoornaam)
//                .setEditorComponent(taskField1, this::setVoornaam)
//                .setCaption("Voornaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Planner::getTussenvoegsel)
//                .setEditorComponent(taskField2, this::setTussenvoegsel)
//                .setCaption("Tussenvoegsel")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Planner::getAchternaam)
//                .setEditorComponent(taskField3, this::setAchternaam)
//                .setCaption("Achternaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Planner::getUser_Id)
//                .setEditorComponent(taskField4, this::setUser_Id)
//                .setCaption("Inlognaam")
//                .setExpandRatio(2);
//
//        werkroosterGrid.addColumn(Planner::getWachtwoord)
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
//    private void setWachtwoord(Planner model, String wachtwoord) {
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
//    private void setUser_Id(Planner model, String inlognaam) {
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
//    private void setAchternaam(Planner model, String achternaam) {
//        long id = model.getID();
//        repository.updateAchternaam(achternaam, id);
//        model.setAchternaam(achternaam);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void setTussenvoegsel(Planner model, String tussenvoegsel) {
//        long id = model.getID();
//        repository.updateTussenvoegsel(tussenvoegsel, id);
//        model.setTussenvoegsel(tussenvoegsel);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    public void setVoornaam(Planner model, String voornaam){
//        long id = model.getID();
//        repository.updateVoornaam(voornaam, id);
//        model.setVoornaam(voornaam);
//        werkroosterGrid.setItems(vrachtwagenList);
//    }
//
//    private void toevoegen() {
//        String snd;
//        try {
//            repository.addRow();
//            long id = getDBId();
//            Planner model = new Planner(id, "", "", "", "", "");
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
//

}
