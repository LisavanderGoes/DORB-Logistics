package com.lisa.dorb.layout;


import com.lisa.dorb.function.Route;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
public class OrderUI extends VerticalLayout implements View {

    @Autowired
    LoginUI loginUI;
    @Autowired
    Route route;


    public VerticalLayout parent;

    //UI

    public Button terugBtn = new Button("Annuleren");
    public Label send = new Label("");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addContent();
        addLayout();
    }

    private void addContent() {
        try {
//            route.getDistance("ede", "arnhem");
            send.setValue(route.getDistance("ede", "arnhem"));
        } catch (Exception e) {
            e.printStackTrace();
//            send.setValue("doet het niet");
        }
    }

    private void addHeader() {
        Label header = new Label("ORDER");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        HorizontalLayout buttons = new HorizontalLayout();
        terugBtn.addClickListener(event -> terugButtonClick());
        terugBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        buttons.addComponents(terugBtn, send);
        parent.addComponent(buttons);
        parent.addComponent(send);
    }

    private void terugButtonClick() {
        getUI().setContent(loginUI);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}

