package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Rit;
import com.lisa.dorb.repository.RitRepository;
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
public class RitCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    RitRepository repository;

    private List<Rit> list; //define inside methode otherwise null
    private Button deleteBtn;
    private Button addBtn;
    private Grid<Rit> grid;
    private long rowId;
    private Object rowItem;

    public void addTable() {
        list = repository.findAll();
        grid = crudUI.ritGrid;
        deleteBtn = new Button("Verwijderen"); //Dit moet hier staan want anders zet hij er 2 onclicks op
        addBtn = new Button("Toevoegen");

        grid.setCaption("Orders");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Rit> dataProvider =
                DataProvider.ofCollection(list);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField5 = new TextField();
        TextField taskField6 = new TextField();

        grid.addColumn(Rit::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Rit::getRuimte)
                .setEditorComponent(taskField1, this::setRuimte)
                .setCaption("Ruimte")
                .setExpandRatio(2);

        grid.addColumn(Rit::getDatum)
                .setCaption("Datum")
                .setExpandRatio(2);

        grid.addColumn(Rit::getChauffeur_Id)
                .setEditorComponent(taskField5, this::setChauffeur_Id)
                .setCaption("Chauffeur_Id")
                .setExpandRatio(2);

        grid.addColumn(Rit::getVrachtwagen_Id)
                .setEditorComponent(taskField6, this::setVrachtwagen_Id)
                .setCaption("Vrachtwagen_Id")
                .setExpandRatio(2);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        grid.getEditor().setEnabled(true);

        crudUI.table.addComponentsAndExpand(grid);
        deleteBtn.addClickListener(event -> {
            delete(rowId, rowItem);
        });
        addBtn.addClickListener(event -> {
            //toevoegen();
        });

        crudUI.table.addComponents(deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void toevoegen() {
        String snd;
        try {
            repository.addRow();
            long id = getDBId();
            Rit model = new Rit(id, 0, 0, null, 0);
            list.add(model);
            grid.setItems(list);
            snd = "";
        } catch (Exception e) {
            snd = "Iets is fout!";
        }
        crudUI.send.setValue(snd);
    }

    private void setID(long id, Object item) {
        rowId = id;
        rowItem = item;
    }

    private void setRuimte(Rit model, String ruimte) {
        long newLong = Long.parseLong(ruimte);
        long id = model.getID();
            repository.updateRuimte(newLong, id);
            model.setRuimte(newLong);
            grid.setItems(list);
    }

    private void setDatum(Rit model, String datum) {
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

    public void setChauffeur_Id(Rit model, String chauffeur_Id){
        long newLong = Long.parseLong(chauffeur_Id);
        long id = model.getID();
        try {
            repository.updateChauffeur_Id(newLong, id);
        }catch (Exception e){

        }
        model.setChauffeur_Id(newLong);
        grid.setItems(list);
    }

    public void setVrachtwagen_Id(Rit model, String vrachtwagen_Id){
        long newLong = Long.parseLong(vrachtwagen_Id);
        long id = model.getID();
        try {
            repository.updateVrachtwagen_Id(newLong, id);
        }catch (Exception e){

        }
        model.setVrachtwagen_Id(newLong);
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
