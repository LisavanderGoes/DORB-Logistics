package com.lisa.dorb.layout.planner.sideLayout;

import com.lisa.dorb.layout.planner.PlannerUI;
import com.lisa.dorb.layout.planner.ToevoegenUsersLayout;
import com.lisa.dorb.model.UserDetails;
import com.lisa.dorb.model.db.Natio;
import com.lisa.dorb.model.db.Rol;
import com.lisa.dorb.model.db.users.Chauffeur;
import com.lisa.dorb.model.db.users.User;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.values.strings;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class UsersLayout extends VerticalLayout {

    @Autowired
    PlannerUI plannerUI;
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    ChauffeurRepository chauffeurRepository;
    @Autowired
    ToevoegenUsersLayout toevoegenUsersLayout;
    @Autowired
    NatioRepository natioRepository;
    @Autowired
    LandenRepository landenRepository;


    private List<User> userList = new ArrayList<>();
    private List<UserDetails> rolList = new ArrayList<>();

    private long rowId;

    //UI
    private ComboBox rijbewijsBox = new ComboBox();
    private ComboBox rollenBox = new ComboBox();
    private HorizontalLayout gridLayout = new HorizontalLayout();
    public Grid<User> userGrid = new Grid<>();
    private Grid<UserDetails> rolGrid = new Grid<>();
    private Button rijbewijsBtn = new Button("Verander rijbewijs");
    private Button rollenBtn = new Button("Verander rol");
    private Button deleteBtn = new Button("Verwijder");
    private Button addBtn = new Button("Toevoegen");
    private Button detailsBtn = new Button("Details");
    public Label send = new Label("");


    public void start() {
        addOnclick();
        addLayout();
        laadUsers();
        laadCombobox();
    }

    private void laadCombobox(){
        List<String> rijbewijsList = new ArrayList<>();
        rijbewijsList.add("C");
        rijbewijsList.add("D");
        rijbewijsBox.setItems(rijbewijsList);
        List<String> statusList = new ArrayList<>();
        statusList.add("chauffeur");
        statusList.add("manager");
        statusList.add("klant");
        statusList.add("planner");
        rollenBox.setItems(statusList);
    }

    private void laadUsers() {
        userList = userRepository.findAll();
        userGrid.setItems(userList);
    }

    /**
     * @param rowId = user_Id
     */
    private void laadDetails(long rowId) {
        String rekeningnummer = "";
        long werkdagen = 0;
        String rijbewijs = "";
        String landen = "";

        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        try {

            String status = "";
            for (Rol rol : rolRepository.getAllByUser_Id(rowId)) {
                status = status + rol.getRol();
            }
            switch (status) {
                case strings.KLANT:
                    rekeningnummer = klantRepository.findAllByUser_Id(rowId).getRekeningnummer();
                    rolGrid.addColumn(UserDetails::getRekeningnummer)
                            .setEditorComponent(taskField4, this::setRekeningnummer)
                            .setCaption("Rekeningnummer")
                            .setExpandRatio(2);
                    break;
                case strings.CHAUFFEUR:
                    Chauffeur chauffeur = chauffeurRepository.findAllByUser_Id(rowId);
                    werkdagen = Long.parseLong(chauffeur.getWerkdagen());
                    rijbewijs = chauffeur.getRijbewijs();
                    for(Natio nation : natioRepository.getAllByChauffeur_Id(rowId)){
                        landen = landen + "/" + landenRepository.getLandenByLand_Id(nation.getLand_Id());
                    }
                    rolGrid.addColumn(UserDetails::getRijbewijs)
                            .setCaption("Rijbewijs")
                            .setExpandRatio(2);
                    rolGrid.addColumn(UserDetails::getWerkdagen)
                            .setEditorComponent(taskField3, this::setWerkdagen)
                            .setCaption("Werkdagen")
                            .setExpandRatio(2);
                    rolGrid.addColumn(UserDetails::getLanden)
                            .setCaption("Nationaliteit rijbewijs")
                            .setExpandRatio(2);
                    break;
                default:
                    break;
            }
            for (Rol rol : rolRepository.getAllByUser_Id(rowId)) {
                rolList.add(new UserDetails(rol.getID(), rol.getUser_Id(), rol.getRol(), rijbewijs, werkdagen, rekeningnummer, landen));
            }

            rolGrid.addColumn(UserDetails::getRol)
                    .setCaption("Rol")
                    .setExpandRatio(2);

            rolGrid.setItems(rolList);
        }catch (Exception ignore){}
    }

    /**
     * @param userDetails = userDetails(model)
     * @param werkdagen = nieuwe werkdagen
     */
    private void setWerkdagen(UserDetails userDetails, String werkdagen) {
        long id = userDetails.getUser_Id();
        chauffeurRepository.updateWerkdagen(Integer.parseInt(werkdagen), id);
        rolList.clear();
        rolGrid.removeAllColumns();
        laadDetails(id);
    }

    /**
     * @param userDetails = userDetails(model)
     * @param rekeningnummer = nieuwe rekeningnummer
     */
    private void setRekeningnummer(UserDetails userDetails, String rekeningnummer) {
        long id = userDetails.getUser_Id();
        klantRepository.updateRekeningummer(rekeningnummer, id);
        rolList.clear();
        rolGrid.removeAllColumns();
        laadDetails(id);
    }

    public void setGridUsers() {
        userGrid = plannerUI.usersGrid;

        userGrid.setCaption("Gebruikers");
        userGrid.setSizeFull();
        userGrid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<User> dataProvider =
                DataProvider.ofCollection(userList);

        userGrid.setDataProvider(dataProvider);

        TextField taskField1 = new TextField();
        TextField taskField2 = new TextField();
        TextField taskField3 = new TextField();
        TextField taskField4 = new TextField();
        TextField taskField5 = new TextField();

        userGrid.addColumn(User::getID)
                .setCaption("Id")
                .setExpandRatio(2);

        userGrid.addColumn(User::getVoornaam)
                .setEditorComponent(taskField1, this::setVoornaam)
                .setCaption("Voornaam")
                .setExpandRatio(2);

        userGrid.addColumn(User::getTussenvoegsel)
                .setEditorComponent(taskField2, this::setTussenvoegsel)
                .setCaption("Tussenvoegsel")
                .setExpandRatio(2);

        userGrid.addColumn(User::getAchternaam)
                .setEditorComponent(taskField3, this::setAchternaam)
                .setCaption("Achternaam")
                .setExpandRatio(2);

        userGrid.addColumn(User::getInlognaam)
                .setEditorComponent(taskField4, this::setInlognaam)
                .setCaption("Inlognaam")
                .setExpandRatio(2);

        userGrid.addColumn(User::getWachtwoord)
                .setEditorComponent(taskField5, this::setWachtwoord)
                .setCaption("Wachtwoord")
                .setExpandRatio(2);

        userGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        userGrid.addItemClickListener(event ->
                setID(event.getItem().getID()));

        userGrid.getEditor().setEnabled(true);
        gridLayout.addComponentsAndExpand(userGrid);
    }

    private void toevoegen() {
        try {
            plannerUI.getUI().setContent(toevoegenUsersLayout);
        }catch (Exception ignore){
        }
    }

    /**
     * @param id = user_Id
     */
    private void delete (long id){
        try {
            for (Rol rol : rolRepository.getAllByUser_Id(id)) {
                switch (rol.getRol()) {
                    case strings.KLANT:
                        klantRepository.deleteRow(id);
                        break;
                    case strings.CHAUFFEUR:
                        chauffeurRepository.deleteRow(id);
                        natioRepository.deleteRow(id);
                        break;
                    default:
                        break;
                }
            }
            rolRepository.deleteRowUser_Id(id);
            userRepository.deleteRow(id);
            reload();
        }catch (Exception e){
            sendError("Hij kan niet verwijderd worden!");
        }
    }

    /**
     * @param user = user(model)
     * @param achternaam = nieuwe achternaam
     */
    private void setAchternaam(User user, String achternaam) {
        long id = user.getID();
        userRepository.updateAchternaam(achternaam, id);
        reload();
    }

    /**
     * @param user = user(model)
     * @param tussenvoegsel = nieuwe tussenvoegsel
     */
    private void setTussenvoegsel(User user, String tussenvoegsel) {
        long id = user.getID();
        userRepository.updateTussenvoegsel(tussenvoegsel, id);
        reload();
    }

    /**
     * @param user = user(model)
     * @param voornaam = nieuwe voornaam
     */
    public void setVoornaam(User user, String voornaam) {
        long id = user.getID();
        userRepository.updateVoornaam(voornaam, id);
        reload();
    }

    /**
     * @param user = user(model)
     * @param wachtwoord = nieuwe wachtwoord
     */
    private void setWachtwoord(User user, String wachtwoord) {
        long id = user.getID();
        try {
            userRepository.updateWachtwoord(wachtwoord, id);
            reload();
        } catch (Exception e) {
            sendError("Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!");
        }
    }

    /**
     * @param user = user(model)
     * @param inlognaam = nieuwe inlognaam
     */
    private void setInlognaam(User user, String inlognaam) {
        long id = user.getID();
        try {
            userRepository.updateInlognaam(inlognaam, id);
            reload();
        } catch (Exception e) {
            sendError("Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!");
        }
    }

    private void setGridDetails() {
        Grid<UserDetails> grid = rolGrid;
        grid.setCaption("Rollen");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        ListDataProvider<UserDetails> dataProvider =
                DataProvider.ofCollection(rolList);

        grid.setDataProvider(dataProvider);

        grid.getEditor().setEnabled(true);
        gridLayout.addComponentsAndExpand(grid);
    }

    private void setID(long id) {
        rowId = id;
    }

    private void reload(){
        userList.clear();
        laadUsers();
    }

    private void addOnclick() {
        rollenBtn.addClickListener(event -> setRol(String.valueOf(rollenBox.getValue()), rowId));
        rijbewijsBtn.addClickListener(event -> setRijbewijs(String.valueOf(rijbewijsBox.getValue()), rowId));
        detailsBtn.addClickListener(event -> details());
        addBtn.addClickListener(event -> toevoegen());
        deleteBtn.addClickListener(event -> delete(rowId));
    }

    /**
     * @param rijbewijs = nieuwe rijbewijs
     * @param rowId = user_Id
     */
    private void setRijbewijs(String rijbewijs, long rowId) {
        chauffeurRepository.updateRijbewijs(rijbewijs, rowId);
        rolList.clear();
        rolGrid.removeAllColumns();
        laadDetails(rowId);
    }

    private void setRol(String rollen, long rowId) {
        rolRepository.updateRol(rollen, rowId);
        rolList.clear();
        rolGrid.removeAllColumns();
        laadDetails(rowId);
    }

    private void details() {
        rolList.clear();
        rolGrid.removeAllColumns();
        laadDetails(rowId);
        setGridDetails();
    }

    private void sendError(String send){
        Notification error = new Notification(send);
        error.setStyleName(ValoTheme.NOTIFICATION_ERROR);
        Notification.show(send);
    }

    private void addLayout() {
        gridLayout = plannerUI.gridLayout;
        userGrid = plannerUI.usersGrid;
        rolGrid = plannerUI.rolGrid;
        userList = plannerUI.userList;
        rolList = plannerUI.rolList;

        HorizontalLayout layout2 = plannerUI.buttons;
        layout2.addComponents(deleteBtn, addBtn, detailsBtn, send);

        VerticalLayout layout3 = new VerticalLayout();
        layout2.addComponents(rijbewijsBox, rijbewijsBtn);
        gridLayout.addComponent(layout3);

        plannerUI.parent.addComponent(layout2);
    }


}

