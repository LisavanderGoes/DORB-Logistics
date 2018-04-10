package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Klant;
import com.lisa.dorb.repository.KlantRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class KlantCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    KlantRepository klantRepository;

    public List<Klant> klantList; //define inside methode otherwise null
    public Button deleteBtn= new Button("Verwijderen");
    public Button addBtn = new Button("Toevoegen");

    public void addTable() {
        klantList = klantList();
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
        addBtn.addClickListener(event -> {
            toevoegen();
        });
        deleteBtn.addClickListener(event -> {
            delete(crudUI.rowId, crudUI.rowItem);
        });

        crudUI.table.addComponents(addBtn, deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setRekeningnummer(Klant klant, String rekeningnummer) {
        updateRekeningnummer(klant, rekeningnummer);
        klant.setRekeningnummer(rekeningnummer);
        crudUI.gridKlant.setItems(klantList);
    }

    private void setID(long id, Object item) {
        crudUI.rowId = id;
        crudUI.rowItem = item;
    }

    private void setWachtwoord(Klant klant, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = updateWachtwoord(klant, wachtwoord);
        if(snd == null) {
            klant.setWachtwoord(wachtwoord);
            crudUI.gridKlant.setItems(klantList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Klant klant, String inlognaam) {
        String snd = updateInlognaam(klant, inlognaam);
        if(snd == null) {
            klant.setInlognaam(inlognaam);
            crudUI.gridKlant.setItems(klant);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Klant klant, String achternaam) {
        updateAchternaam(klant, achternaam);
        klant.setAchternaam(achternaam);
        crudUI.gridKlant.setItems(klantList);
    }

    private void setTussenvoegsel(Klant klant, String tussenvoegsel) {
        updateTussenvoegsel(klant, tussenvoegsel);
        klant.setTussenvoegsel(tussenvoegsel);
        crudUI.gridKlant.setItems(klantList);
    }

    public void setVoornaam(Klant klant, String s){
        updateVoornaam(klant, s);
        klant.setVoornaam(s);
        crudUI.gridKlant.setItems(klantList);
    }

    private void toevoegen() {
        String snd = addKlantRow();
        if(snd == null) {
            long id = getKlantId();
            Klant klant = new Klant(id, "", "", "", "", "", "");
            klantList.add(klant);
            crudUI.gridKlant.setItems(klantList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        deleteKlantRow(id);
        klantList.remove(item);
        crudUI.gridKlant.setItems(klantList);
    }

    //region [Klant]
    /**
     * @return list of all klanten
     */
    public List<Klant> klantList() {
        return (List<Klant>) klantRepository.findAll();
    }

    /**
     * @param klant Klant model
     * @param voornaam The new voornaam
     */
    public void updateVoornaam(Klant klant, String voornaam) {
        long id = klant.getID();
        klantRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param klant Klant model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateTussenvoegsel(Klant klant, String tussenvoegsel) {
        long id = klant.getID();
        klantRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param klant Klant model
     * @param achternaam The new achternaam
     */
    public void updateAchternaam(Klant klant, String achternaam) {
        long id = klant.getID();
        klantRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param klant Klant model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateInlognaam(Klant klant, String inlognaam) {
        long id = klant.getID();
        try {
            klantRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param klant Klant model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateWachtwoord(Klant klant, String wachtwoord) {
        long id = klant.getID();
        try {
            klantRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }


    /**
     * @param klant M
     * @param rekeningummer
     */
    public void updateRekeningnummer(Klant klant, String rekeningummer) {
        long id = klant.getID();
        klantRepository.updateRekeningummer(rekeningummer, id);

    }

    /**
     * @param i id from selection
     */
    public void deleteKlantRow(long i) {
        long id = i;
        klantRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addKlantRow() {
        try {
            klantRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getKlantId() {
        return klantRepository.getId();
    }
    //endregion
}
