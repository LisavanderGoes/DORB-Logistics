package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Manager;
import com.lisa.dorb.model.Planner;
import com.lisa.dorb.repository.ManagerRepository;
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
public class ManagerCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    ManagerRepository managerRepository;

    public List<Manager> managerList; //define inside methode otherwise null
    private Grid<Manager> grid;
    public Button deleteBtn= new Button("Verwijderen");
    public Button addBtn = new Button("Toevoegen");

    public void addTable() {
        managerList = managerList();
        grid = crudUI.managerGrid;
        grid.setCaption("Managers");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Manager> dataProvider =
                DataProvider.ofCollection(managerList);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        grid.addColumn(Manager::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Manager::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        grid.addColumn(Manager::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        grid.addColumn(Manager::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        grid.addColumn(Manager::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        grid.addColumn(Manager::getWachtwoord)
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

    private void setWachtwoord(Manager model, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = updateWachtwoord(model, wachtwoord);
        if(snd == null) {
            model.setWachtwoord(wachtwoord);
            grid.setItems(managerList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Manager model, String inlognaam) {
        String snd = updateInlognaam(model, inlognaam);
        if(snd == null) {
            model.setInlognaam(inlognaam);
            grid.setItems(managerList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Manager model, String achternaam) {
        updateAchternaam(model, achternaam);
        model.setAchternaam(achternaam);
        grid.setItems(managerList);
    }

    private void setTussenvoegsel(Manager model, String tussenvoegsel) {
        updateTussenvoegsel(model, tussenvoegsel);
        model.setTussenvoegsel(tussenvoegsel);
        grid.setItems(managerList);
    }

    public void setVoornaam(Manager model, String voornaam){
        updateVoornaam(model, voornaam);
        model.setVoornaam(voornaam);
        grid.setItems(managerList);
    }

    private void toevoegen() {
        String snd = addManagerRow();
        if(snd == null) {
            long id = getManagerId();
            Manager model = new Manager(id, "", "", "", "", "");
            managerList.add(model);
            grid.setItems(managerList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        deleteManagerRow(id);
        managerList.remove(item);
        grid.setItems(managerList);
    }

    //region [Manager]
    /**
     * @return list of all managers
     */
    public List<Manager> managerList() {
        return (List<Manager>) managerRepository.findAll();
    }

    /**
     * @param model Manager model
     * @param voornaam The new voornaam
     */
    public void updateVoornaam(Manager model, String voornaam) {
        long id = model.getID();
        managerRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param model Manager model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateTussenvoegsel(Manager model, String tussenvoegsel) {
        long id = model.getID();
        managerRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param model Manager model
     * @param achternaam The new achternaam
     */
    public void updateAchternaam(Manager model, String achternaam) {
        long id = model.getID();
        managerRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param model Manager model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateInlognaam(Manager model, String inlognaam) {
        long id = model.getID();
        try {
            managerRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param model Manager model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateWachtwoord(Manager model, String wachtwoord) {
        long id = model.getID();
        try {
            managerRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param i id from selection
     */
    public void deleteManagerRow(long i) {
        long id = i;
        managerRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addManagerRow() {
        try {
            managerRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getManagerId() {
        return managerRepository.getId();
    }
    //endregion
}
