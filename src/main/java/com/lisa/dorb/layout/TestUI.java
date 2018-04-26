package com.lisa.dorb.layout;

import com.lisa.dorb.function.OrderMaken;
import com.lisa.dorb.layout.order.FactuurUI;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringComponent
public class TestUI extends VerticalLayout implements View {

    //UI
    Button button = new Button("button");
    Label label = new Label("label");
    TextArea textArea = new TextArea("textarea");
    private DateField datum = new DateField("Datum van levering");

    @Autowired
    OrderMaken orderMaken;
    @Autowired
    FactuurUI factuurUI;

    @PostConstruct
        void init() {
            VerticalLayout layout = new VerticalLayout();

            button.addStyleName(ValoTheme.BUTTON_FRIENDLY);


            layout.addComponent(button);
            layout.addComponent(datum);
            layout.addComponent(label);
            addComponentsAndExpand(layout);
        }


}
