package com.lisa.dorb;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

//@SpringUI
public class test extends UI {
    Button button = new Button("button");
    Label label = new Label("label");
    TextArea textArea = new TextArea("textarea");

    @Override
    protected void init(VaadinRequest request){
        VerticalLayout layout = new VerticalLayout();

        button.addClickListener(event -> buttonClick());
        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);


        layout.addComponent(button);
        layout.addComponent(textArea);
        layout.addComponent(label);
        setContent(layout);
    }




    public void buttonClick(){
        String data = textArea.getValue();
        label.setValue(data);
    }

}
