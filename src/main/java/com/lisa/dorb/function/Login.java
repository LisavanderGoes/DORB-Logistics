package com.lisa.dorb.function;

import com.lisa.dorb.model.db.Rol;
import com.lisa.dorb.model.db.users.User;
import com.lisa.dorb.repository.RolRepository;
import com.lisa.dorb.repository.UserRepository;
import com.lisa.dorb.saved.UserInfo;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class Login {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RolRepository rolRepository;

    /**
     * @param naam = string met inlognaam
     * @param wachtwoord = string met wachtwoord
     * @return List met rollen
     */
    public List<Rol> login(String naam, String wachtwoord){
        if(!naam.equals("") || !wachtwoord.equals("")){
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
        return null;
    }

    /**
     * Geef value aan de variabelen in UserInfo
     * @param user = user(model)
     * @param allRollen = list met rollen
     */
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
