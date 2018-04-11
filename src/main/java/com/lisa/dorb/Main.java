package com.lisa.dorb;

import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.layout.OrderUI;
import com.lisa.dorb.model.Order;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class Main extends UI {

    @Autowired
    LoginUI loginUI;
    @Autowired
    CrudUI crudUI;
    @Autowired
    OrderUI orderUI;
    @Autowired
    SpringViewProvider viewProvider;


    @Override
    protected void init(VaadinRequest request){
        in();
    }

    private void in() {
        setContent(orderUI);
    }

}
