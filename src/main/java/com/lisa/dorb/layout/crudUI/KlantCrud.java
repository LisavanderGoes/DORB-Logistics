package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Admin;
import com.lisa.dorb.model.Klant;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class KlantCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    Crud crud;

    public List<Klant> klantList; //define inside methode otherwise null

    public void addTable() {
        klantList = crud.klantList();
        crudUI.gridKlant.setCaption("Klanten");
        crudUI.gridKlant.setSizeFull();
        crudUI.gridKlant.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Klant> dataProvider =
                DataProvider.ofCollection(klantList);

        crudUI.gridKlant.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField6 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        crudUI.gridKlant.addColumn(Klant::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        crudUI.gridKlant.addColumn(Klant::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        crudUI.gridKlant.addColumn(Klant::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        crudUI.gridKlant.addColumn(Klant::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        crudUI.gridKlant.addColumn(Klant::getRekeningnummer)
                .setEditorComponent(taskField6, this::setRekeningnummer)
                .setCaption("Rekeningnummer")
                .setExpandRatio(2);

        crudUI.gridKlant.addColumn(Klant::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        crudUI.gridKlant.addColumn(Klant::getWachtwoord)
                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
                .setCaption("Wachtwoord")
                .setExpandRatio(2);

        crudUI.gridKlant.setSelectionMode(Grid.SelectionMode.SINGLE);

        crudUI.gridKlant.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        crudUI.gridKlant.getEditor().setEnabled(true);

        crudUI.table.addComponentsAndExpand(crudUI.gridKlant);
        crudUI.addBtn.addClickListener(event -> {
            toevoegen();
        });
        crudUI.deleteBtn.addClickListener(event -> {
            delete(crudUI.rowId, crudUI.rowItem);
        });
        crudUI.parent.addComponents(crudUI.addBtn, crudUI.deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setRekeningnummer(Klant klant, String rekeningnummer) {
        crud.updateKlantRekeningnummer(klant, rekeningnummer);
        klant.setRekeningnummer(rekeningnummer);
        crudUI.gridKlant.setItems(klantList);
    }

    private void setID(long id, Object item) {
        crudUI.rowId = id;
        crudUI.rowItem = item;
    }

    private void setWachtwoord(Klant klant, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = crud.updateKlantWachtwoord(klant, wachtwoord);
        if(snd == null) {
            klant.setWachtwoord(wachtwoord);
            crudUI.gridKlant.setItems(klantList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Klant klant, String inlognaam) {
        String snd = crud.updateKlantInlognaam(klant, inlognaam);
        if(snd == null) {
            klant.setInlognaam(inlognaam);
            crudUI.gridKlant.setItems(klant);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Klant klant, String achternaam) {
        crud.updateKlantAchternaam(klant, achternaam);
        klant.setAchternaam(achternaam);
        crudUI.gridKlant.setItems(klantList);
    }

    private void setTussenvoegsel(Klant klant, String tussenvoegsel) {
        crud.updateKlantTussenvoegsel(klant, tussenvoegsel);
        klant.setTussenvoegsel(tussenvoegsel);
        crudUI.gridKlant.setItems(klantList);
    }

    public void setVoornaam(Klant klant, String s){
        crud.updateKlantVoornaam(klant, s);
        klant.setVoornaam(s);
        crudUI.gridKlant.setItems(klantList);
    }

    private void toevoegen() {
        String snd = crud.addKlantRow();
        if(snd == null) {
            long id = crud.getKlantId();
            Klant klant = new Klant(id, "", "", "", "", "", "");
            klantList.add(klant);
            crudUI.gridKlant.setItems(klantList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        crud.deleteKlantRow(id);
        klantList.remove(item);
        crudUI.gridKlant.setItems(klantList);
    }
}
