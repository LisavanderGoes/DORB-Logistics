package com.lisa.dorb.layout;

import com.lisa.dorb.function.Login;
import com.lisa.dorb.model.Rol;
import com.lisa.dorb.values.strings;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringComponent
public class LoginUI extends VerticalLayout implements View {

    public VerticalLayout parent;

    @Autowired
    Login login;
    @Autowired
    TestUI testUI;
    @Autowired
    CrudUI crudUI;
    @Autowired
    OrderUI orderUI;

    //UI
    private HorizontalLayout buttons = new HorizontalLayout();
    private Button button = new Button("Inloggen");
    private Button uitlogButton = new Button("Uitloggen");
    Label label = new Label("");
    TextField naam = new TextField("Loginnaam");
    PasswordField wachtwoord = new PasswordField("Wachtwoord");

    @PostConstruct
    void init() {
        setupLayout();
        addHeader();
        addLayout();
    }

    private void inloggen(){
        removeFromLogin();
        if(login.login(naam.getValue(), wachtwoord.getValue()) != null || !login.login(naam.getValue(), wachtwoord.getValue()).isEmpty()) {
            List<Rol> allRollen = login.login(naam.getValue(), wachtwoord.getValue());
            if (allRollen.size() > 1) {
                for (Rol rol : allRollen) {
                    Button button = new Button(rol.getRol());
                    button.addClickListener(new Button.ClickListener() {
                        public void buttonClick(Button.ClickEvent event) {
                            login(rol.getRol());
                        }
                    });
                    buttons.addComponent(button);
                }
                parent.addComponents(buttons);
            } else {
                for (Rol rol : allRollen) {
                    login(rol.getRol());
                }
            }
        } else {
            uitloggen();
        }
        parent.addComponent(uitlogButton);
    }

    public void login(String status){
        String send = "";
        try {
                switch (status) {
                    case strings.ADMIN:
                        getUI().setContent(crudUI);
                        break;
                    case strings.KLANT:
                        getUI().setContent(orderUI);
                        break;
                    case strings.CHAUFFEUR:
                        send = "gelukt";
                        break;
                    case strings.PLANNER:
                        break;
                    default:
                        break;
                }
        }catch (Exception e){
            send = ""+e;
            uitloggen();
        }
        label.setValue(send);
    }

    private void removeFromLogin(){
        buttons.removeAllComponents();
        parent.removeComponent(buttons);
    }

    private void uitloggen() {
        parent.removeComponent(uitlogButton);
        naam.setValue("");
        wachtwoord.setValue("");
        label.setValue("");
        removeFromLogin();
    }

    private void addHeader() {
        Label header = new Label("LOGIN");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        button.addClickListener(event -> inloggen());
        uitlogButton.addClickListener(event -> uitloggen());
        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        uitlogButton.addStyleName(ValoTheme.BUTTON_DANGER);
        parent.addComponents(naam, wachtwoord, button, label);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
