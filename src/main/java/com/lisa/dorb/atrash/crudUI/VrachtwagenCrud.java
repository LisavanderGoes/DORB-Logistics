package com.lisa.dorb.atrash.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.db.Vrachtwagen;
import com.lisa.dorb.repository.VrachtwagenRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringComponent
public class VrachtwagenCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    VrachtwagenRepository repository;


    private List<Vrachtwagen> list; //define inside methode otherwise null
    private Button deleteBtn;
    private Button addBtn;
    private Grid<Vrachtwagen> grid;
    private long rowId;
    private Object rowItem;

    public void addTable() {
        list = repository.findAll();
        grid = crudUI.vrachtwagenGrid;
        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
        addBtn = new Button("Toevoegen");


        grid.setCaption("VrachtwagenPlanner");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Vrachtwagen> dataProvider =
                DataProvider.ofCollection(list);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        DateField taskField3 = new DateField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        grid.addColumn(Vrachtwagen::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Vrachtwagen::getTyp_Id)
                .setEditorComponent(taskField1, this::setTyp_Id)
                .setCaption("Typ_Id")
                .setExpandRatio(2);

        grid.addColumn(Vrachtwagen::getKenteken)
                .setEditorComponent(taskField2, this::setKenteken)
                .setCaption("Kenteken")
                .setExpandRatio(2);

        grid.addColumn(Vrachtwagen::getApk)
                //.setEditorComponent(taskField3, this::setRijbewijs)
                .setCaption("Apk")
                .setExpandRatio(2);

        grid.addColumn(Vrachtwagen::getStatus)
                .setEditorComponent(taskField4, this::setStatus)
                .setCaption("Status")
                .setExpandRatio(2);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        grid.getEditor().setEnabled(true);

        crudUI.table.addComponentsAndExpand(grid);
        addBtn.addClickListener(event -> {
         //   toevoegen();
        });
        deleteBtn.addClickListener(event -> {
            delete(rowId, rowItem);
        });

        crudUI.table.addComponents(addBtn, deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setApk(Vrachtwagen model, String datum) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatter.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long id = model.getID();
        repository.updateApk(date, id);
        model.setApk(date);
        grid.setItems(list);
    }

    private void setID(long id, Object item) {
        rowId = id;
        rowItem = item;
    }

    private void setTyp_Id(Vrachtwagen model, String typ_Id) {
        long newLong = Long.parseLong(typ_Id);
        long id = model.getID();
        repository.updateTyp_Id(newLong, id);
        model.setTyp_Id(newLong);
        grid.setItems(list);
    }

    private void setKenteken(Vrachtwagen model, String kenteken) {
        long id = model.getID();
        repository.updateKenteken(kenteken, id);
        model.setKenteken(kenteken);
        grid.setItems(list);
    }

    private void setStatus(Vrachtwagen model, String status) {
        long id = model.getID();
        try {
            repository.updateStatus(status, id);
            model.setStatus(status);
            grid.setItems(list);
            crudUI.send.setValue("");
        } catch (Exception e){
            crudUI.send.setValue("typo");
        }
    }

    private void toevoegen() {
        String snd;
        try {
            repository.addRow();
            long id = getDBId();
            Vrachtwagen model = new Vrachtwagen(id, 0, "", null, "");
            list.add(model);
            grid.setItems(list);
            snd = "";
        } catch (Exception e) {
            snd = "Iets is fout!";
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
