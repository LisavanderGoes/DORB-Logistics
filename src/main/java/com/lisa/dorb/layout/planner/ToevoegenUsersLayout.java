package com.lisa.dorb.layout.planner;

import com.lisa.dorb.model.StringLongModel;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.values.strings;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.ui.NumberField;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class ToevoegenUsersLayout extends VerticalLayout implements View {

    @Autowired
    KlantRepository klantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    ChauffeurRepository chauffeurRepository;
    @Autowired
    NatioRepository natioRepository;
    @Autowired
    LandenRepository landenRepository;
    @Autowired
    PlannerUI plannerUI;

    private List<StringLongModel> landen = new ArrayList<>();

    //UI
    private HorizontalLayout parent;
    public Grid<StringLongModel> landenGrid = new Grid<>();
    private VerticalLayout sideLayout = new VerticalLayout();
    private Button terugBtn = new Button("Terug");
    private Button toevoegenBtn = new Button("Voeg nieuwe gebruiker toe");
    public Label send = new Label("");
    private TextField voornaamTxtField = new TextField("Voornaam");
    private TextField tussenvoegselTxtField = new TextField("Tussenvoegsel");
    private TextField achternaamTxtField = new TextField("Achternaam");
    private TextField inlognaamTxtField = new TextField("Inlognaam");
    private TextField wachtwoordTxtField = new TextField("Wachtwoord");
    private TextField rekeningnummerTxtField = new TextField("Rekeningnummer");
    private NumberField werkdagenTxtField = new NumberField("Werkdagen");
    private ComboBox rijbewijsBox = new ComboBox("Rijbewijs");
    private ComboBox rollenBox = new ComboBox("Rol");
    private ComboBox natioBox = new ComboBox("Nationaliteit rijbewijs");

    @PostConstruct
    void init() {
        addOnclick();
        setupLayout();
        addLayout();
        laadCombobox();
        setGrid();
    }

    private void laadCombobox(){
        List<String> rijbewijsList = new ArrayList<>();
        rijbewijsList.add("C");
        rijbewijsList.add("D");
        rijbewijsBox.setItems(rijbewijsList);
        List<String> statusList = new ArrayList<>();
        statusList.add(strings.CHAUFFEUR);
        statusList.add(strings.MANAGER);
        statusList.add(strings.KLANT);
        statusList.add(strings.PLANNER);
        rollenBox.setItems(statusList);
        List<String> landenList = new ArrayList<>();
        landenList.add(strings.NEDERLAND);
        landenList.add(strings.BELGIE);
        landenList.add(strings.LUXEMBURG);
        landenList.add(strings.DUISTLAND);
        natioBox.setItems(landenList);

    }

    /**
     * @param voornaam = voornaam
     * @param tussenvoegsel = tussenvoegsel
     * @param achternaam = achternaam
     * @param inlognaam = inlognaam
     * @param wachtwoord = wachtwoord
     * @param rollen = rol van user
     * @param rekeningnummer = rekeningnummer
     * @param werkdagen = werkdagen(aantal)
     * @param rijbewijs = rijbewijs
     * @param landen = rijbewijs nationaliteit
     */
    private void toevoegen(String voornaam, String tussenvoegsel, String achternaam, String inlognaam, String wachtwoord, String rollen, String rekeningnummer, long werkdagen, String rijbewijs, List<StringLongModel> landen) {
        if(!rollen.equals("null")) {
            try {
                userRepository.addRow(voornaam, tussenvoegsel, achternaam, inlognaam, wachtwoord);
                long user_Id = userRepository.getId();
                switch (rollen) {
                    case strings.KLANT:
                        klantRepository.addRow(rekeningnummer, user_Id);
                        break;
                    case strings.CHAUFFEUR:
                        if(!rijbewijs.equals("null") && !landen.isEmpty()) {
                            chauffeurRepository.addRow(user_Id, rijbewijs, werkdagen);
                            for (StringLongModel land : landen) {
                                long land_Id = landenRepository.getLand_IdByLanden(land.getString());
                                natioRepository.addRow(user_Id, land_Id);
                            }
                        }
                        break;
                    default:
                        break;
                }
                rolRepository.addRow(user_Id, rollen);
            } catch (Exception e) {
                sendError("Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!");
            }
            terugButtonClick();
        }
    }

    private void addOnclick() {
        terugBtn.addClickListener(event -> terugButtonClick());
        rollenBox.addValueChangeListener(new HasValue.ValueChangeListener() {
                    @Override
                    public void valueChange(final HasValue.ValueChangeEvent event) {
                        setRol(String.valueOf(rollenBox.getValue()));
                    }
                });
        natioBox.addValueChangeListener(new HasValue.ValueChangeListener() {
            @Override
            public void valueChange(final HasValue.ValueChangeEvent event) {
                toevoegen(landen, String.valueOf(natioBox.getValue()));
            }
        });
        toevoegenBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        try {
            if(werkdagenTxtField.getValue().equals("")){
                werkdagenTxtField.setValue("0");
            }
            toevoegenBtn.addClickListener(event -> toevoegen(voornaamTxtField.getValue(), tussenvoegselTxtField.getValue(), achternaamTxtField.getValue(), inlognaamTxtField.getValue(), wachtwoordTxtField.getValue(), String.valueOf(rollenBox.getValue()), rekeningnummerTxtField.getValue(), Integer.parseInt(werkdagenTxtField.getValue()), String.valueOf(rijbewijsBox.getValue()), landen));
        } catch (Exception ignore){}
    }

    private void terugButtonClick() {
        remove();
        plannerUI.startUI(strings.USERS);
        getUI().setContent(plannerUI);
    }

    /**
     * @param rollen = rol van user (klant, chauffeur of default)
     */
    private void setRol(String rollen) {
        if(!rollen.equals("null")) {
            parent.removeComponent(sideLayout);
            parent.removeComponent(landenGrid);
            landen.clear();
            landenGrid.setItems(landen);
            sideLayout.removeAllComponents();
            switch (rollen) {
                case strings.KLANT:
                    sideLayout.addComponents(rekeningnummerTxtField, toevoegenBtn);
                    break;
                case strings.CHAUFFEUR:
                    sideLayout.addComponents(rijbewijsBox, werkdagenTxtField, natioBox, toevoegenBtn);
                    parent.addComponent(landenGrid);
                    break;
                default:
                    sideLayout.addComponents(toevoegenBtn);
                    break;
            }
            parent.addComponent(sideLayout);
        }
    }

    private void setGrid() {
        landenGrid.setCaption("Landen");
        landenGrid.setSizeFull();
        landenGrid.setSelectionMode(com.vaadin.ui.Grid.SelectionMode.NONE);
        ListDataProvider<StringLongModel> dataProvider =
                DataProvider.ofCollection(landen);

        landenGrid.setDataProvider(dataProvider);

        landenGrid.addColumn(StringLongModel::getString)
                .setCaption("Wat?")
                .setExpandRatio(2);

        landenGrid.addColumn(person -> "Delete",
                new ButtonRenderer(clickEvent -> {
                    landen.remove(clickEvent.getItem());
                    landenGrid.setItems(landen);
                }))
                .setExpandRatio(2);

        landenGrid.getEditor().setEnabled(false);
    }

    /**
     * @param landen = land(model)
     * @param value = land
     */
    private void toevoegen(List<StringLongModel> landen, String value){
        landen.add(new StringLongModel(value, 0));
        landenGrid.setItems(landen);
    }

    private void remove() {
        voornaamTxtField.clear();
        tussenvoegselTxtField.clear();
        achternaamTxtField.clear();
        inlognaamTxtField.clear();
        wachtwoordTxtField.clear();
        rekeningnummerTxtField.clear();
        werkdagenTxtField.setValue("0");
        rijbewijsBox.clear();
        rollenBox.clear();
        parent.removeComponent(sideLayout);
        parent.removeComponent(landenGrid);
        sideLayout.removeAllComponents();
        landenGrid.removeAllColumns();
        setGrid();
        landen.clear();
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void setupLayout() {
        parent = new HorizontalLayout();
        parent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponentsAndExpand(parent);
    }

    private void addLayout() {
        VerticalLayout layout2 = new VerticalLayout();
        layout2.addComponents(voornaamTxtField, tussenvoegselTxtField, achternaamTxtField, inlognaamTxtField, wachtwoordTxtField, rollenBox, terugBtn, send);

        parent.addComponents(layout2);
    }

}

