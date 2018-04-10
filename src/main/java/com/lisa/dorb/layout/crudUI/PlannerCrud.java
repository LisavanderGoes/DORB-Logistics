package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Planner;
import com.lisa.dorb.repository.PlannerRepository;
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
public class PlannerCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    PlannerRepository plannerRepository;

    public List<Planner> plannerList; //define inside methode otherwise null
    private Grid<Planner> grid;
    public Button deleteBtn= new Button("Verwijderen");
    public Button addBtn = new Button("Toevoegen");

    public void addTable() {
        plannerList = plannerList();
        grid = crudUI.plannerGrid;
        grid.setCaption("Planners");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Planner> dataProvider =
                DataProvider.ofCollection(plannerList);

        grid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        grid.addColumn(Planner::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        grid.addColumn(Planner::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        grid.addColumn(Planner::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        grid.addColumn(Planner::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        grid.addColumn(Planner::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        grid.addColumn(Planner::getWachtwoord)
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

    private void setWachtwoord(Planner model, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = updateWachtwoord(model, wachtwoord);
        if(snd == null) {
            model.setWachtwoord(wachtwoord);
            grid.setItems(plannerList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Planner model, String inlognaam) {
        String snd = updateInlognaam(model, inlognaam);
        if(snd == null) {
            model.setInlognaam(inlognaam);
            grid.setItems(plannerList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Planner model, String achternaam) {
        updateAchternaam(model, achternaam);
        model.setAchternaam(achternaam);
        grid.setItems(plannerList);
    }

    private void setTussenvoegsel(Planner model, String tussenvoegsel) {
        updatePlannerTussenvoegsel(model, tussenvoegsel);
        model.setTussenvoegsel(tussenvoegsel);
        grid.setItems(plannerList);
    }

    public void setVoornaam(Planner model, String voornaam){
        updateVoornaam(model, voornaam);
        model.setVoornaam(voornaam);
        grid.setItems(plannerList);
    }

    private void toevoegen() {
        String snd = addPlannerRow();
        if(snd == null) {
            long id = getPlannerId();
            Planner model = new Planner(id, "", "", "", "", "");
            plannerList.add(model);
            grid.setItems(plannerList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        deletePlannerRow(id);
        plannerList.remove(item);
        grid.setItems(plannerList);
    }

    //region [Planner]
    /**
     * @return list of all planners
     */
    public List<Planner> plannerList() {
        return (List<Planner>) plannerRepository.findAll();
    }

    /**
     * @param model Planner model
     * @param voornaam The new voornaam
     */
    public void updateVoornaam(Planner model, String voornaam) {
        long id = model.getID();
        plannerRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param model Planner model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updatePlannerTussenvoegsel(Planner model, String tussenvoegsel) {
        long id = model.getID();
        plannerRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param model Planner model
     * @param achternaam The new achternaam
     */
    public void updateAchternaam(Planner model, String achternaam) {
        long id = model.getID();
        plannerRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param model Planner model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateInlognaam(Planner model, String inlognaam) {
        long id = model.getID();
        try {
            plannerRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param model Planner model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateWachtwoord(Planner model, String wachtwoord) {
        long id = model.getID();
        try {
            plannerRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param i id from selection
     */
    public void deletePlannerRow(long i) {
        long id = i;
        plannerRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addPlannerRow() {
        try {
            plannerRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getPlannerId() {
        return plannerRepository.getId();
    }
    //endregion
}
