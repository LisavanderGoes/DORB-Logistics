package com.lisa.dorb.layout;

import com.lisa.dorb.layout.chauffeur.WerkroosterUI;
import com.lisa.dorb.saved.UserInfo;
import com.lisa.dorb.function.Login;
import com.lisa.dorb.layout.Order.OrderUI;
import com.lisa.dorb.model.DB.Rol;
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
    @Autowired
    WerkroosterUI werkroosterUI;

    //UI
    private HorizontalLayout buttons = new HorizontalLayout();
    private Button loginBtn = new Button("Inloggen");
    public Button uitlogButton = new Button("Uitloggen");
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
        if(login.login(naam.getValue(), wachtwoord.getValue()) != null && !login.login(naam.getValue(), wachtwoord.getValue()).isEmpty()) {
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
            parent.addComponent(uitlogButton);
            loginBtn.setEnabled(false);
        } else {
            uitloggen();
        }
    }

    private void login(String status){
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
                        werkroosterUI.laadWerkrooster();
                        werkroosterUI.setGrid();
                        getUI().setContent(werkroosterUI);
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
        send = UserInfo.voornaam+" "+UserInfo.tussenvoegsel+" "+UserInfo.achternaam+" "+UserInfo.rol+" "+UserInfo.user_Id;
        label.setValue(send);
    }

    private void removeFromLogin(){
        buttons.removeAllComponents();
        parent.removeComponent(buttons);
    }

    private void uitloggen() {
        loginBtn.setEnabled(true);
        parent.removeComponent(uitlogButton);
        naam.setValue("");
        wachtwoord.setValue("");
        label.setValue("");
        removeFromLogin();
        removeFromUserInfo();
    }

    private void removeFromUserInfo() {
        UserInfo.voornaam = "";
        UserInfo.tussenvoegsel = "";
        UserInfo.achternaam = "";
        UserInfo.user_Id = 0;
        UserInfo.rol = "";

    }

    private void addHeader() {
        Label header = new Label("LOGIN");
        header.addStyleName(ValoTheme.LABEL_H1);
        parent.addComponent(header);
    }

    private void addLayout() {
        loginBtn.addClickListener(event -> inloggen());
        uitlogButton.addClickListener(event -> uitloggen());
        loginBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        uitlogButton.addStyleName(ValoTheme.BUTTON_DANGER);
        parent.addComponents(naam, wachtwoord, loginBtn, label);
    }

    private void setupLayout() {
        parent = new VerticalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }
}
