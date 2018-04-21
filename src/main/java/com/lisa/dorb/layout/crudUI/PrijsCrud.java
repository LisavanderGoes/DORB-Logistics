package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.db.Prijs;
import com.lisa.dorb.repository.PrijsRepository;
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
public class PrijsCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    PrijsRepository repository;


    private List<Prijs> list; //define inside methode otherwise null
    private Button deleteBtn;
    private Button addBtn;
    private Grid<Prijs> grid;
    private long rowId;
    private Object rowItem;

    public void addTable() {
        list = repository.findAll();
        grid = crudUI.prijsGrid;
        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
        addBtn = new Button("Toevoegen");


        grid.setCaption("Prijzen");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Prijs> dataProvider =
                DataProvider.ofCollection(list);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();

        grid.addColumn(Prijs::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Prijs::getWat)
                .setEditorComponent(taskField1, this::setWat)
                .setCaption("Wat?")
                .setExpandRatio(2);

        grid.addColumn(Prijs::getPrijs)
                .setEditorComponent(taskField2, this::setPrijs)
                .setCaption("Prijs")
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

    private void setWat(Prijs model, String wat) {
        long id = model.getID();
        repository.updateWat(wat, id);
        model.setWat(wat);
        grid.setItems(list);
    }

    private void setPrijs(Prijs model, String prijs) {
        long id = model.getID();
        repository.updatePrijs(prijs, id);
        model.setPrijs(prijs);
        grid.setItems(list);
    }

    private void toevoegen() {
        String snd;
        try {
            repository.addRow();
            long id = getDBId();
            Prijs model = new Prijs(id, "", "");
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
