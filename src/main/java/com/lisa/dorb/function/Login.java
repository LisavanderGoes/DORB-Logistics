package com.lisa.dorb.function;

import com.lisa.dorb.saved.UserInfo;
import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.model.DB.Rol;
import com.lisa.dorb.model.DB.User;
import com.lisa.dorb.repository.*;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@SpringComponent
public class Login {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    LoginUI loginUI;

    public List<Rol> login(String naam, String wachtwoord){
        if(!naam.equals("") || !wachtwoord.equals("")){
            return getStatus(naam, wachtwoord);
        }
        return null;
    }

    private List<Rol> getStatus(String naam, String wachtwoord) {
        List<Rol> allRollen;
        try {
            User user = userRepository.findAllByInlognaamAndWachtwoord(naam, wachtwoord);
            allRollen = rolRepository.getAllByUser_Id(user.getID());
            fillUserInfo(user, allRollen);
        }catch (Exception e){
            return null;
        }

        return allRollen;
    }

    private void fillUserInfo(User user, List<Rol> allRollen) {
        UserInfo.voornaam = user.getVoornaam();
        UserInfo.tussenvoegsel = user.getTussenvoegsel();
        UserInfo.achternaam = user.getAchternaam();
        UserInfo.user_Id = user.getID();
        UserInfo.rol = "";
        for(Rol rol: allRollen){
            UserInfo.rol = UserInfo.rol+rol.getRol()+";";
        }
    }

}
