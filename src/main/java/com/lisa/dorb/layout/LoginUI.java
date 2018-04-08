package com.lisa.dorb.layout;

import com.lisa.dorb.function.Login;
import com.lisa.dorb.values.strings;
import com.vaadin.annotations.Push;
import com.vaadin.navigator.View;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class LoginUI extends VerticalLayout implements View {

    private VerticalLayout parent;

    @Autowired
    Login login;

    @Autowired
    TestUI testUI;

    @Autowired
    CrudManagerUI crudManagerUI;


    //UI
    ComboBox status = new ComboBox("Inloggen als...");
    Button button = new Button("Inloggen");
    Label label = new Label("label");
    TextField naam = new TextField("Loginnaam");
    PasswordField wachtwoord = new PasswordField("Wachtwoord");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
        setStatus();
    }

    private void buttonClick(){
            login();
    }

    private void login(){
        String send;
        send = "";
        try {
            if(login.login(naam.getValue(), wachtwoord.getValue(), status.getValue().toString())) {
                switch (status.getValue().toString()) {
                    case strings.MANAGER:
                        getUI().setContent(crudManagerUI);
                        break;
                    case strings.KLANT:
                        getUI().setContent(testUI);
                        break;
                    case strings.CHAUFFEUR:
                        break;
                    case strings.PLANNER:
                        break;
                    default:
                        send = "Iets is niet goed!";
                        break;
                }
            } else {
                send = "Iets is niet goed!";
            }
        }catch (NullPointerException e){
            send = "Null pointer exception:  Inloggen als... is leeg! of Repos is nog niet @Autowired";
        }

        label.setValue(send);
    }

    private void setStatus(){
        List<String> states = new ArrayList<>();
        states.add(strings.KLANT);
        states.add(strings.CHAUFFEUR);
        states.add(strings.MANAGER);
        states.add(strings.PLANNER);
        status.setItems(states);
    }

    private void addHeader() {
        Label header = new Label("LOGIN");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        button.addClickListener(event -> buttonClick());
        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        parent.addComponents(naam, wachtwoord, status, button, label);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
