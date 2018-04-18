package com.lisa.dorb.function;

import com.lisa.dorb.layout.LoginUI;
import com.lisa.dorb.model.Rol;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.values.strings;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
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
            long user_Id = userRepository.findIdByInlognaamAndWachtwoord(naam, wachtwoord);
            allRollen = rolRepository.getAllByUser_Id(user_Id);

        }catch (Exception e){
            return null;
        }

        return allRollen;
    }

}
