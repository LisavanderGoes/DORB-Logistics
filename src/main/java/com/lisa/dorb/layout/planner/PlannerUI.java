package com.lisa.dorb.layout.planner;

import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.layout.planner.sideLayout.OrdersLayout;
import com.lisa.dorb.layout.planner.sideLayout.UsersLayout;
import com.lisa.dorb.layout.planner.sideLayout.VrachtwagenLayout;
import com.lisa.dorb.model.OrderPlanner;
import com.lisa.dorb.model.UserDetails;
import com.lisa.dorb.model.VrachtwagenPlanner;
import com.lisa.dorb.model.db.Pallet;
import com.lisa.dorb.model.db.users.User;
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
    @Autowired
    UsersLayout usersLayout;
    @Autowired
    VrachtwagenLayout vrachtwagenLayout;


    public List<OrderPlanner> orderList = new ArrayList<>();
    public List<Pallet> palletsList = new ArrayList<>();
    public List<User> userList = new ArrayList<>();
    public List<UserDetails> rolList = new ArrayList<>();
    public List<VrachtwagenPlanner> vrachtwagenList = new ArrayList<>();


    //UI
    public VerticalLayout parent;
    public HorizontalLayout gridLayout = new HorizontalLayout();
    public HorizontalLayout buttons = new HorizontalLayout();
    public Grid<OrderPlanner> ordersGrid = new Grid<>();
    public Grid<Pallet> palletsGrid = new Grid<>();
    public Grid<User> usersGrid = new Grid<>();
    public Grid<UserDetails> rolGrid = new Grid<>();
    public Grid<VrachtwagenPlanner> vrachtwagenGrid = new Grid<>();

    private Button terugBtn = new Button("Terug");
    private Button orderBtn = new Button("Orders/Ritten");
    private Button userBtn = new Button("Gebruikers");
    private Button vrachwtagenBtn = new Button("Vrachtwagen");
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
        vrachwtagenBtn.addClickListener(event -> startUI(strings.VRACHTWAGEN));
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
        layout2.addComponents(orderBtn, userBtn, vrachwtagenBtn, terugBtn, send);

        parent.addComponent(layout2);
        parent.addComponentsAndExpand(gridLayout);
    }

    private void terugButtonClick() {
        gridLayout.removeComponent(palletsGrid);
        orderList.clear();
        ordersGrid.removeAllColumns();
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
                ordersLayout.start();
                ordersLayout.setGridOrder();
                break;
            case strings.USERS:
                usersLayout.start();
                usersLayout.setGridUsers();
                break;
            case strings.VRACHTWAGEN:
                vrachtwagenLayout.start();
                vrachtwagenLayout.setGrid();
                break;
        }
    }

    private void removeAll(){
        gridLayout.removeAllComponents();
        buttons.removeAllComponents();
        orderList.clear();
        userList.clear();
        vrachtwagenList.clear();
        ordersGrid.removeAllColumns();
        palletsGrid.removeAllColumns();
        usersGrid.removeAllColumns();
        vrachtwagenGrid.removeAllColumns();
    }
}

