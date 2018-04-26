package com.lisa.dorb.layout.planner.sideLayout;

import com.lisa.dorb.layout.planner.PlannerUI;
import com.lisa.dorb.model.VrachtwagenPlanner;
import com.lisa.dorb.model.db.Vrachtwagen;
import com.lisa.dorb.model.db.VrachtwagenType;
import com.lisa.dorb.repository.*;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class VrachtwagenLayout extends VerticalLayout {

    @Autowired
    PlannerUI plannerUI;
    @Autowired
    VrachtwagenRepository vrachtwagenRepository;
    @Autowired
    VrachtwagenTypeRepository vrachtwagenTypeRepository;


    private List<VrachtwagenPlanner> list = new ArrayList<>();
    private long rowId;

    //UI
    private HorizontalLayout gridLayout = new HorizontalLayout();
    private Grid<VrachtwagenPlanner> vrachtwagenGrid = new Grid<>();
    private Button apkBtn = new Button("Verander datum");
    private Button statusBtn = new Button("Verander status");
    private Button typBtn = new Button("Verander type");
    private DateField datumTxtField = new DateField();
    private ComboBox statusBox = new ComboBox();
    private ComboBox typBox = new ComboBox();
    public Label send = new Label("");


    public void start(){
        addOnclick();
        laadCombobox();
        addLayout();
        laadVrachtwagens();
    }

    private void laadVrachtwagens() {
        list = new ArrayList<>();
        for(Vrachtwagen vrachtwagen : vrachtwagenRepository.findAll()){
            list.add(new VrachtwagenPlanner(vrachtwagen.getID(),Integer.parseInt(vrachtwagen.getTyp_Id()),vrachtwagen.getKenteken(),Date.valueOf(vrachtwagen.getApk()),vrachtwagen.getStatus(),vrachtwagenTypeRepository.getTypeById(Integer.parseInt(vrachtwagen.getTyp_Id()))));
        }
        vrachtwagenGrid.setItems(list);
    }

    public void setGrid() {
        vrachtwagenGrid.setCaption("VrachtwagenPlanner");
        vrachtwagenGrid.setSizeFull();
        vrachtwagenGrid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<VrachtwagenPlanner> dataProvider =
                DataProvider.ofCollection(list);

        vrachtwagenGrid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();

        vrachtwagenGrid.addColumn(VrachtwagenPlanner::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        vrachtwagenGrid.addColumn(VrachtwagenPlanner::getTyp)
                .setEditorComponent(taskField1, this::setTyp_Id)
                .setCaption("Type")
                .setExpandRatio(2);

        vrachtwagenGrid.addColumn(VrachtwagenPlanner::getKenteken)
                .setEditorComponent(taskField2, this::setKenteken)
                .setCaption("Kenteken")
                .setExpandRatio(2);

        vrachtwagenGrid.addColumn(VrachtwagenPlanner::getApk)
                .setCaption("Apk")
                .setExpandRatio(2);

        vrachtwagenGrid.addColumn(VrachtwagenPlanner::getStatus)
                .setCaption("Status")
                .setExpandRatio(2);

        vrachtwagenGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        vrachtwagenGrid.addItemClickListener(event ->
                setID(event.getItem().getID()));

        vrachtwagenGrid.getEditor().setEnabled(true);
        gridLayout.addComponentsAndExpand(vrachtwagenGrid);
    }

    private void laadCombobox(){
        List<String> statusList = new ArrayList<>();
        statusList.add("beschikbaar");
        statusList.add("onderhoud");
        statusBox.setItems(statusList);
        List<String> typeList = new ArrayList<>();
        for(VrachtwagenType vrachtwagenType: vrachtwagenTypeRepository.findAll()){
            typeList.add(vrachtwagenType.getType());
        }
        typBox.setItems(typeList);
    }

    /**
     * @param rowId = vrachtwagen_Id
     * @param datum = nieuwe datum
     */
    private void setApk(long rowId, LocalDate datum) {
        vrachtwagenRepository.updateApk(Date.valueOf(datum), rowId);
        reload();
    }

    /**
     * @param model = vrachtwagens(model)
     * @param typ_Id = nieuwe typ_Id
     */
    private void setTyp_Id(VrachtwagenPlanner model, String typ_Id) {
        long newLong = Long.parseLong(typ_Id);
        long id = model.getID();
        vrachtwagenRepository.updateTyp_Id(newLong, id);
        reload();
    }

    /**
     * @param model = vrachtwagens(model)
     * @param kenteken = nieuwe kenteken
     */
    private void setKenteken(VrachtwagenPlanner model, String kenteken) {
        long id = model.getID();
        vrachtwagenRepository.updateKenteken(kenteken, id);
        reload();
    }

    private void setID(long id) {
        rowId = id;
    }

    private void reload(){
        list.clear();
        laadVrachtwagens();
    }

    private void addOnclick() {
        try {
            statusBtn.addClickListener(event -> setSatus(String.valueOf(statusBox.getValue()), rowId));
            typBtn.addClickListener(event -> setType(String.valueOf(typBox.getValue()), rowId));
            apkBtn.addClickListener(event -> setApk(rowId, datumTxtField.getValue()));
        }catch (Exception e){
            send.setValue(e+"");
        }
    }

    /**
     * @param type = nieuwe vrachtwagenType
     * @param rowId = vrachtwagen_Id
     */
    private void setType(String type, long rowId) {
        try {
            long typ_Id = vrachtwagenTypeRepository.getIdByType(type);
            vrachtwagenRepository.updateTyp_Id(typ_Id, rowId);
            reload();
        }catch (Exception ignore){}
    }

    private void setSatus(String status, long rowId) {
        vrachtwagenRepository.updateStatus(status, rowId);
        reload();
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void addLayout() {
        gridLayout = plannerUI.gridLayout;
        vrachtwagenGrid = plannerUI.vrachtwagenGrid;
        list = plannerUI.vrachtwagenList;

        HorizontalLayout layout2 = plannerUI.buttons;
        layout2.addComponents(send);

        VerticalLayout layout3 = new VerticalLayout();
        layout2.addComponents(datumTxtField, apkBtn, statusBox, statusBtn, typBox, typBtn);
        gridLayout.addComponent(layout3);

        plannerUI.parent.addComponent(layout2);
    }
}

