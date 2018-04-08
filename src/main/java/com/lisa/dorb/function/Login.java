package com.lisa.dorb.function;

import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.model.Klant;
import com.lisa.dorb.model.Manager;
import com.lisa.dorb.model.Planner;
import com.lisa.dorb.repository.ChauffeurRepository;
import com.lisa.dorb.repository.KlantRepository;
import com.lisa.dorb.repository.ManagerRepository;
import com.lisa.dorb.repository.PlannerRepository;
import com.lisa.dorb.values.strings;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringComponent //werkt alleen als ie opgeroepen wordt door @autowire
public class Login {
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    ChauffeurRepository chauffeurRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    PlannerRepository plannerRepository;

    public Boolean login(String naam, String wachtwoord, String status){

        if(naam.equals("") || wachtwoord.equals("")){
        } else{
            switch (status){
                case strings.MANAGER:
                    return checkManager(naam, wachtwoord);
                case strings.KLANT:
                    return checkKlant(naam, wachtwoord);
                case strings.CHAUFFEUR:
                    return checkChauffeur(naam, wachtwoord);
                case strings.PLANNER:
                    return checkPlanner(naam, wachtwoord);
            }
        }

        return null;
    }

    public Boolean test(String naam, String wachtwoord){
        String dbwachtwoord = managerRepository.findWachtwoordByInlognaam(naam);

            if(wachtwoord.equals(dbwachtwoord)){
                return true;
            } else {
                return false;
            }
    }

    public Boolean checkChauffeur(String naam, String wachtwoord){
        String dbwachtwoord = chauffeurRepository.findWachtwoordByInlognaam(naam);

        if(wachtwoord.equals(dbwachtwoord)){
            return true;
        } else {
            return false;
        }
    }


    public Boolean checkPlanner(String naam, String wachtwoord){
        String dbwachtwoord = plannerRepository.findWachtwoordByInlognaam(naam);

        if(wachtwoord.equals(dbwachtwoord)){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkKlant(String naam, String wachtwoord){
        String dbwachtwoord = klantRepository.findWachtwoordByInlognaam(naam);

        if(wachtwoord.equals(dbwachtwoord)){
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkManager(String naam, String wachtwoord){
        String dbwachtwoord = managerRepository.findWachtwoordByInlognaam(naam);

        if(wachtwoord.equals(dbwachtwoord)){
            return true;
        } else {
            return false;
        }
    }
}
