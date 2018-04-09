package com.lisa.dorb.layout.crudUI;

import com.lisa.dorb.function.Crud;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Admin;
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
public class AdminCrud extends HorizontalLayout {

    @Autowired
    CrudUI crudUI;
    @Autowired
    Crud crud;

    public List<Admin> adminList = crud.adminList();

    public void addTable() {
        crudUI.crudAdminBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
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
        HorizontalLayout buttons = new HorizontalLayout();
        crudUI.addBtn.addClickListener(event -> {
            toevoegen();
        });
        crudUI.deleteBtn.addClickListener(event -> {
            delete(crudUI.rowId, crudUI.rowItem);
        });
        buttons.addComponents(crudUI.addBtn, crudUI.deleteBtn);
        crudUI.parent.addComponent(buttons);
        crudUI.parent.addComponentsAndExpand(crudUI.table);
    }

    private void setID(long id, Object item) {
        crudUI.rowId = id;
        crudUI.rowItem = item;
    }

    private void setWachtwoord(Admin admin, String s) {
        //hier moet het encrypted erin staan dan weer decrypten naar db en weer encrypted erin
        String snd = crud.updateAdminWachtwoord(admin, s);
        if(snd == null) {
            admin.setWachtwoord(s);
            crudUI.gridAdmin.setItems(adminList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setInlognaam(Admin admin, String s) {
        String snd = crud.updateAdminInlognaam(admin, s);
        if(snd == null) {
            admin.setInlognaam(s);
            crudUI.gridAdmin.setItems(adminList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void setAchternaam(Admin admin, String s) {
        crud.updateAdminAchternaam(admin, s);
        admin.setAchternaam(s);
        crudUI.gridAdmin.setItems(adminList);
    }

    private void setTussenvoegsel(Admin admin, String s) {
        crud.updateAdminTussenvoegsel(admin, s);
        admin.setTussenvoegsel(s);
        crudUI.gridAdmin.setItems(adminList);
    }

    public void setVoornaam(Admin admin, String s){
        crud.updateAdminVoornaam(admin, s);
        admin.setVoornaam(s);
        crudUI.gridAdmin.setItems(adminList);
    }

    private void toevoegen() {
        String snd = crud.addAdminRow();
        if(snd == null) {
            long id = crud.getAdminId();
            Admin admin = new Admin(id, "", "", "", "", "");
            adminList.add(admin);
            crudUI.gridAdmin.setItems(adminList);
            crudUI.send.setValue("");
        }else {
            crudUI.send.setValue(snd);
        }
    }

    private void delete(long id, Object item) {
        crud.deleteAdminRow(id);
        adminList.remove(item);
        crudUI.gridAdmin.setItems(adminList);
    }
}
