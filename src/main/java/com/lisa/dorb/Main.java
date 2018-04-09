package com.lisa.dorb;

import com.lisa.dorb.function.Crypt;
import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.layout.CrudManagerUI;
import com.vaadin.annotations.Push;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class Main extends UI {

    @Autowired
    LoginUI loginUI;

    @Autowired
    CrudManagerUI crudManagerUI;


    @Autowired
    SpringViewProvider viewProvider;


    @Override
    protected void init(VaadinRequest request){
        in();
    }

    private void in() {
        setContent(crudManagerUI);
    }

}
