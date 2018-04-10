package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Admin;
import com.lisa.dorb.repository.AdminRepository;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class AdminCrud extends VerticalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    AdminRepository adminRepository;

    public List<Admin> adminList; //define inside methode otherwise null
    public Button deleteBtn= new Button("Verwijderen");
    public Button addBtn = new Button("Toevoegen");

    public void addTable() {
        adminList = adminList();
        crudUI.gridAdmin.setCaption("Admins");
        crudUI.gridAdmin.setSizeFull();
        crudUI.gridAdmin.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<Admin> dataProvider =
                DataProvider.ofCollection(adminList);

        crudUI.gridAdmin.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        crudUI.gridAdmin.addColumn(Admin::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        crudUI.gridAdmin.addColumn(Admin::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        crudUI.gridAdmin.addColumn(Admin::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        crudUI.gridAdmin.addColumn(Admin::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        crudUI.gridAdmin.addColumn(Admin::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        crudUI.gridAdmin.addColumn(Admin::getWachtwoord)
                .setEditorComponent(taskField5, this::setWachtwoord) //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
                .setCaption("Wachtwoord")
                .setExpandRatio(2);

        crudUI.gridAdmin.setSelectionMode(Grid.SelectionMode.SINGLE);

        crudUI.gridAdmin.addItemClickListener(event ->
                setID(event.getItem().getID(), event.getItem()));

        crudUI.gridAdmin.getEditor().setEnabled(true);

        crudUI.table.addComponentsAndExpand(crudUI.gridAdmin);
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

    private void setWachtwoord(Admin admin, String wachtwoord) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = updateWachtwoord(admin, wachtwoord);
        if(snd == null) {
            admin.setWachtwoord(wachtwoord);
            crudUI.gridAdmin.setItems(adminList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Admin admin, String inlognaam) {
        String snd = updateInlognaam(admin, inlognaam);
        if(snd == null) {
            admin.setInlognaam(inlognaam);
            crudUI.gridAdmin.setItems(adminList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Admin admin, String achternaam) {
        updateAchternaam(admin, achternaam);
        admin.setAchternaam(achternaam);
        crudUI.gridAdmin.setItems(adminList);
    }

    private void setTussenvoegsel(Admin admin, String tussenvoegsel) {
        updateTussenvoegsel(admin, tussenvoegsel);
        admin.setTussenvoegsel(tussenvoegsel);
        crudUI.gridAdmin.setItems(adminList);
    }

    public void setVoornaam(Admin admin, String voornaam){
        updateVoornaam(admin, voornaam);
        admin.setVoornaam(voornaam);
        crudUI.gridAdmin.setItems(adminList);
    }

    private void toevoegen() {
        String snd = addAdminRow();
        if(snd == null) {
            long id = getAdminId();
            Admin admin = new Admin(id, "", "", "", "", "");
            adminList.add(admin);
            crudUI.gridAdmin.setItems(adminList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        deleteAdminRow(id);
        adminList.remove(item);
        crudUI.gridAdmin.setItems(adminList);
    }

    //region [Admin]
    /**
     * @return list of all admins
     */
    public List<Admin> adminList() {
        return (List<Admin>) adminRepository.findAll();
    }

    /**
     * @param admin Admin model
     * @param voornaam The new voornaam
     */
    public void updateVoornaam(Admin admin, String voornaam) {
        long id = admin.getID();
        adminRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param admin Admin model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateTussenvoegsel(Admin admin, String tussenvoegsel) {
        long id = admin.getID();
        adminRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param admin Admin model
     * @param achternaam The new achternaam
     */
    public void updateAchternaam(Admin admin, String achternaam) {
        long id = admin.getID();
        adminRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param admin Admin model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateInlognaam(Admin admin, String inlognaam) {
        long id = admin.getID();
        try {
            adminRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param admin Admin model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateWachtwoord(Admin admin, String wachtwoord) {
        long id = admin.getID();
        try {
            adminRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param i id from selection
     */
    public void deleteAdminRow(long i) {
        long id = i;
        adminRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addAdminRow() {
        try {
            adminRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getAdminId() {
        return adminRepository.getId();
    }
    //endregion
}
