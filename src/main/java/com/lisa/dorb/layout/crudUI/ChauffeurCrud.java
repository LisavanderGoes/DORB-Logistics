package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.repository.ChauffeurRepository;
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
public class ChauffeurCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    ChauffeurRepository chauffeurRepository;

    public List<Chauffeur> chauffeurList; //define inside methode otherwise null
    private Grid<Chauffeur> grid;
    public Button deleteBtn= new Button("Verwijderen");
    public Button addBtn = new Button("Toevoegen");

    public void addTable() {
        chauffeurList = chauffeursList();
        grid = crudUI.gridChauffeur;
        grid.setCaption("Chauffeurs");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Chauffeur> dataProvider =
                DataProvider.ofCollection(chauffeurList);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();
        TextField taskField6 = new TextField();
        TextField taskField7 = new TextField();

        grid.addColumn(Chauffeur::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getRijbewijs)
                .setEditorComponent(taskField6, this::setRijbewijs)
                .setCaption("Rijbewijs")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getWerkdagen)
                .setEditorComponent(taskField7, this::setWerkdagen)
                .setCaption("Werkdagen")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        grid.addColumn(Chauffeur::getWachtwoord)
                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
                .setCaption("Wachtwoord")
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
            delete(crudUI.rowId, crudUI.rowItem);
        });

        crudUI.table.addComponents(addBtn, deleteBtn);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setID(long id, Object item) {
        crudUI.rowId = id;
        crudUI.rowItem = item;
    }

    private void setWachtwoord(Chauffeur model, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = updateWachtwoord(model, wachtwoord);
        if(snd == null) {
            model.setWachtwoord(wachtwoord);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Chauffeur model, String inlognaam) {
        String snd = updateInlognaam(model, inlognaam);
        if(snd == null) {
            model.setInlognaam(inlognaam);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Chauffeur model, String achternaam) {
        updateAchternaam(model, achternaam);
        model.setAchternaam(achternaam);
        grid.setItems(chauffeurList);
    }

    private void setTussenvoegsel(Chauffeur model, String tussenvoegsel) {
        updateTussenvoegsel(model, tussenvoegsel);
        model.setTussenvoegsel(tussenvoegsel);
        grid.setItems(chauffeurList);
    }

    public void setVoornaam(Chauffeur model, String voornaam){
        updateVoornaam(model, voornaam);
        model.setVoornaam(voornaam);
        grid.setItems(chauffeurList);
    }

    public void setRijbewijs(Chauffeur model, String rijbewijs){
        String snd = updateRijbewijs(model, rijbewijs);
        if(snd == null) {
            model.setRijbewijs(rijbewijs);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    public void setWerkdagen(Chauffeur model, String werkdagen){
        Long newWerkdagen = Long.parseLong(werkdagen);
        updateWerkdagen(model, newWerkdagen);
        model.setWerkdagen(newWerkdagen);
        grid.setItems(chauffeurList);
    }

    private void toevoegen() {
        String snd = addChauffeurRow();
        if(snd == null) {
            long id = getChauffeurId();
            Chauffeur model = new Chauffeur(id, "", "", "", "", 0, "", "");
            chauffeurList.add(model);
            grid.setItems(chauffeurList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        deleteChauffeurRow(id);
        chauffeurList.remove(item);
        grid.setItems(chauffeurList);
    }

    //region [Chauffeur]
    /**
     * @return list of all chauffeurs
     */
    public List<Chauffeur> chauffeursList() {
        return (List<Chauffeur>) chauffeurRepository.findAll();
    }

    /**
     * @param chauffeur Chauffeur model
     * @param voornaam The new voornaam
     */
    public void updateVoornaam(Chauffeur chauffeur, String voornaam) {
        long id = chauffeur.getID();
        chauffeurRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param chauffeur Chauffeur model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateTussenvoegsel(Chauffeur chauffeur, String tussenvoegsel) {
        long id = chauffeur.getID();
        chauffeurRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param chauffeur Chauffeur model
     * @param achternaam The new achternaam
     */
    public void updateAchternaam(Chauffeur chauffeur, String achternaam) {
        long id = chauffeur.getID();
        chauffeurRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param chauffeur Chauffeur model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateInlognaam(Chauffeur chauffeur, String inlognaam) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param chauffeur Chauffeur model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateWachtwoord(Chauffeur chauffeur, String wachtwoord) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param chauffeur Chauffeur model
     * @param rijbewijs The new werkdagen
     */
    public String updateRijbewijs(Chauffeur chauffeur, String rijbewijs) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateRijbewijs(rijbewijs, id);
        }catch (Exception e){
            return "Rijbewijs kan alleen C of D zijn!";
        }
        return null;
    }

    /**
     * @param chauffeur Chauffeur model
     * @param werkdagen The new werkdagen
     */
    public void updateWerkdagen(Chauffeur chauffeur, long werkdagen) {
        long id = chauffeur.getID();
        chauffeurRepository.updateWerkdagen(werkdagen, id);
    }

    /**
     * @param i id from selection
     */
    public void deleteChauffeurRow(long i) {
        long id = i;
        chauffeurRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addChauffeurRow() {
        try {
            chauffeurRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getChauffeurId() {
        return chauffeurRepository.getId();
    }
    //endregion
}
