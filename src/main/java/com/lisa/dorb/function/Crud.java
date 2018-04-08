package com.lisa.dorb.function;

import com.lisa.dorb.layout.CrudManagerUI;
import com.lisa.dorb.model.Manager;
import com.lisa.dorb.repository.ChauffeurRepository;
import com.lisa.dorb.repository.KlantRepository;
import com.lisa.dorb.repository.ManagerRepository;
import com.lisa.dorb.repository.PlannerRepository;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent //werkt alleen als ie opgeroepen wordt door @autowire
public class Crud {
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    ChauffeurRepository chauffeurRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    PlannerRepository plannerRepository;
    @Autowired
    CrudManagerUI crudManagerUI;

    public List<Manager> managerList() {
    return (List<Manager>) managerRepository.findAll();
    }

    public void updateManagerVoornaam(Manager manager, String s) {
        long id = manager.getID();
        managerRepository.updateVoornaam(s, id);
    }

    public void updateManagerTussenvoegsel(Manager manager, String s) {
        long id = manager.getID();
        managerRepository.updateTussenvoegsel(s, id);
    }

    public void updateManagerAchternaam(Manager manager, String s) {
        long id = manager.getID();
        managerRepository.updateAchternaam(s, id);
    }

    public String updateManagerInlognaam(Manager manager, String s) {
        long id = manager.getID();
        try {
            managerRepository.updateInlognaam(s, id);
        } catch (Exception e){
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    public String updateManagerWachtwoord(Manager manager, String s) {
        long id = manager.getID();
        try {
            managerRepository.updateWachtwoord(s, id);
        } catch (Exception e){
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    public void deleteManagerRow(long i) {
        long id = i;
        managerRepository.deleteRow(id);
    }

    public String addManagerRow(){
        try {
            managerRepository.addRow();
        }catch (Exception e){
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    public long getManagerId(){
        return managerRepository.getId();
    }
}
