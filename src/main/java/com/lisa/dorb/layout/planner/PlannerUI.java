package com.lisa.dorb.layout.planner;

import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.layout.planner.sideLayout.OrdersLayout;
import com.lisa.dorb.model.OrderPlanner;
import com.lisa.dorb.model.db.Pallet;
import com.lisa.dorb.values.strings;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class PlannerUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    OrdersLayout ordersLayout;


    public VerticalLayout parent;
    public List<OrderPlanner> orderList = new ArrayList<>();
    public List<Pallet> palletsList = new ArrayList<>();


    //UI
    public HorizontalLayout gridLayout = new HorizontalLayout();
    public HorizontalLayout buttons = new HorizontalLayout();
    public Grid<OrderPlanner> gridOrders = new Grid<>();
    public Grid<Pallet> gridDetail = new Grid<>();

    private Button terugBtn = new Button("Terug");
    private Button orderBtn = new Button("Orders");
    private Button userBtn = new Button("Gebruikers");
    public Label send = new Label("");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        addOnclick();
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        orderBtn.addClickListener(event -> startUI(strings.ORDER));
        userBtn.addClickListener(event -> startUI(strings.USERS));
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void addHeader() {
        Label header = new Label("PLANNERS");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        HorizontalLayout layout2 = new HorizontalLayout();
        layout2.addComponents(orderBtn, userBtn, terugBtn, send);

        parent.addComponent(layout2);
        parent.addComponentsAndExpand(gridLayout);
    }

    private void terugButtonClick() {
        gridLayout.removeComponent(gridDetail);
        orderList.clear();
        gridOrders.removeAllColumns();
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }

    public void startUI(String ui){
        removeAll();
        switch (ui) {
            case strings.ORDER:
                ordersLayout.laadOrderPlanner();
                ordersLayout.setGridOrder();
                break;
            case strings.USERS:
                break;
//            case "Prijs":
//                prijsCrud.addTable();
//                break;
//            case "Pallet":
//                palletCrud.addTable();
//                break;
//            case "Rit":
//                ritCrud.addTable();
//                break;
//            default:
//                break;
        }
    }

    private void removeAll(){
        gridLayout.removeAllComponents();
        buttons.removeAllComponents();
        orderList.clear();
        gridOrders.removeAllColumns();
        gridDetail.removeAllColumns();
    }
}

