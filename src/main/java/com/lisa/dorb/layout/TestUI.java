package com.lisa.dorb.layout;

import com.lisa.dorb.function.Login;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class TestUI extends VerticalLayout implements View {

    //UI
    Button button = new Button("button");
    Label label = new Label("label");
    TextArea textArea = new TextArea("textarea");

        @PostConstruct
        void init() {
            VerticalLayout layout = new VerticalLayout();

            button.addStyleName(ValoTheme.BUTTON_FRIENDLY);


            layout.addComponent(button);
            layout.addComponent(textArea);
            layout.addComponent(label);
            addComponentsAndExpand(layout);
        }


}
