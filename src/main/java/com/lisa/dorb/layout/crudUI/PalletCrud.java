package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.db.Pallet;
import com.lisa.dorb.repository.PalletRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class PalletCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    PalletRepository repository;


    private List<Pallet> list; //define inside methode otherwise null
    private Button deleteBtn;
    private Button addBtn;
    private Grid<Pallet> grid;
    private long rowId;
    private Object rowItem;

    public void addTable() {
        list = repository.findAll();
        grid = crudUI.palletGrid;
        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
        addBtn = new Button("Toevoegen");


        grid.setCaption("Pallets");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Pallet> dataProvider =
                DataProvider.ofCollection(list);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();

        grid.addColumn(Pallet::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Pallet::getWat)
                .setEditorComponent(taskField1, this::setWat)
                .setCaption("Wat?")
                .setExpandRatio(2);

        grid.addColumn(Pallet::getOrder_Id)
                .setEditorComponent(taskField2, this::setPrijs)
                .setCaption("Order_Id")
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

    private void setWat(Pallet model, String wat) {
        long id = model.getID();
        repository.updateWat(wat, id);
        model.setWat(wat);
        grid.setItems(list);
    }

    private void setPrijs(Pallet model, String order_Id) {
        long id = model.getID();
        long newLong = Long.parseLong(order_Id);
        repository.updateOrder_Id(newLong, id);
//        model.setPrijs(newLong);
        grid.setItems(list);
    }

    private void toevoegen() {
        String snd;
       try {
//            repository.addRow();
            long id = getDBId();
//            Pallet model = new Pallet(id, 0, "");
//            list.add(model);
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
