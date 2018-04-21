package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.db.Order;
import com.lisa.dorb.repository.OrderRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringComponent
public class OrderCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    OrderRepository repository;

    private List<Order> list; //define inside methode otherwise null
    private Button deleteBtn;
    private Grid<Order> grid;
    private long rowId;
    private Object rowItem;

    public void addTable() {
        list = repository.findAll();
        grid = crudUI.gridOrder;
        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op

        grid.setCaption("Orders");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Order> dataProvider =
                DataProvider.ofCollection(list);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();
        TextField taskField6 = new TextField();

        grid.addColumn(Order::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Order::getKlant_Id)
                .setEditorComponent(taskField1, this::setKlant_Id)
                .setCaption("Klant_Id")
                .setExpandRatio(2);

        grid.addColumn(Order::getAdres)
                .setEditorComponent(taskField2, this::setAdres)
                .setCaption("Adres")
                .setExpandRatio(2);

        grid.addColumn(Order::getPrijs)
                .setEditorComponent(taskField3, this::setPrijs)
                .setCaption("Prijs")
                .setExpandRatio(2);

        grid.addColumn(Order::getDatum)
                .setCaption("Datum")
                .setExpandRatio(2);

        grid.addColumn(Order::getRit_Id)
                .setEditorComponent(taskField6, this::setRit_Id) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
                .setCaption("Rit_Id")
                .setExpandRatio(2);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        grid.getEditor().setEnabled(true);

        crudUI.table.addComponentsAndExpand(grid);
        deleteBtn.addClickListener(event -> {
            delete(rowId, rowItem);
        });

        crudUI.table.addComponents(deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setID(long id, Object item) {
        rowId = id;
        rowItem = item;
    }

    private void setKlant_Id(Order model, String klant_Id) {
        long newLong = Long.parseLong(klant_Id);
        long id = model.getID();
            repository.updateKlant_Id(newLong, id);
            model.setKlant_Id(newLong);
            grid.setItems(list);
    }

    private void setAdres(Order model, String adres) {
        long id = model.getID();
            repository.updateAdres(adres, id);
            model.setAdres(adres);
            grid.setItems(list);
    }

    private void setPrijs(Order model, String prijs) {
        long id = model.getID();
        repository.updatePrijs(prijs, id);
        model.setPrijs(prijs);
        grid.setItems(list);
    }

    private void setDatum(Order model, String datum) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = (Date) formatter.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long id = model.getID();
        repository.updateDatum(date, id);
        model.setDatum(date);
        grid.setItems(list);
    }

    public void setRit_Id(Order model, String rit_Id){
        long newLong = Long.parseLong(rit_Id);
        long id = model.getID();
        repository.updateRit_Id(newLong, id);
        model.setRit_Id(newLong);
        grid.setItems(list);
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
