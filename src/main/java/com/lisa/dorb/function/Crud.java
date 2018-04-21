package com.lisa.dorb.function;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.*;
import com.lisa.dorb.repository.*;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent //werkt alleen als ie opgeroepen wordt door @autowire
public class Crud {
    @Autowired
    CrudUI crudUI;


    /*public List<Admin> adminList(Repository repos) {
        return (List<Admin>) adminRepository.findAll();
    }

    public void updateVoornaam(Admin admin, String voornaam) {
        long id = admin.getID();
        adminRepository.updateVoornaam(voornaam, id);
    }
    public void updateTussenvoegsel(Admin admin, String tussenvoegsel) {
        long id = admin.getID();
        adminRepository.updateTussenvoegsel(tussenvoegsel, id);
    }
    public void updateAchternaam(Admin admin, String achternaam) {
        long id = admin.getID();
        adminRepository.updateAchternaam(achternaam, id);
    }
    public String updateInlognaam(Admin admin, String inlognaam) {
        long id = admin.getID();
        try {
            adminRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public String updateWachtwoord(Admin admin, String wachtwoord) {
        long id = admin.getID();
        try {
            adminRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public void deleteAdminRow(long i) {
        long id = i;
        adminRepository.deleteRow(id);
    }
    public String addAdminRow() {
        try {
            adminRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public long getDBId() {
        return adminRepository.getId();
    }



    //region [chauffeur]
    public List<chauffeur> chauffeursList() {
        return (List<chauffeur>) chauffeurRepository.findAll();
    }

    public void updateVoornaam(chauffeur chauffeur, String voornaam) {
        long id = chauffeur.getID();
        chauffeurRepository.updateVoornaam(voornaam, id);
    }
    public void updateTussenvoegsel(chauffeur chauffeur, String tussenvoegsel) {
        long id = chauffeur.getID();
        chauffeurRepository.updateTussenvoegsel(tussenvoegsel, id);
    }
    public void updateAchternaam(chauffeur chauffeur, String achternaam) {
        long id = chauffeur.getID();
        chauffeurRepository.updateAchternaam(achternaam, id);
    }
    public String updateInlognaam(chauffeur chauffeur, String inlognaam) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public String updateWachtwoord(chauffeur chauffeur, String wachtwoord) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public String updateRijbewijs(chauffeur chauffeur, String rijbewijs) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateRijbewijs(rijbewijs, id);
        }catch (Exception e){
            return "Rijbewijs kan alleen C of D zijn!";
        }
        return null;
    }
    public void updateWerkdagen(chauffeur chauffeur, long werkdagen) {
        long id = chauffeur.getID();
        chauffeurRepository.updateWerkdagen(werkdagen, id);
    }
    public void deleteChauffeurRow(long i) {
        long id = i;
        chauffeurRepository.deleteRow(id);
    }
    public String addChauffeurRow() {
        try {
            chauffeurRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public long getChauffeurId() {
        return chauffeurRepository.getId();
    }
    //endregion

    //region [Klant]
    public List<Klant> list() {
        return (List<Klant>) klantRepository.findAll();
    }

    public void updateVoornaam(Klant klant, String voornaam) {
        long id = klant.getID();
        klantRepository.updateVoornaam(voornaam, id);
    }
    public void updateTussenvoegsel(Klant klant, String tussenvoegsel) {
        long id = klant.getID();
        klantRepository.updateTussenvoegsel(tussenvoegsel, id);
    }
    public void updateAchternaam(Klant klant, String achternaam) {
        long id = klant.getID();
        klantRepository.updateAchternaam(achternaam, id);
    }
    public String updateInlognaam(Klant klant, String inlognaam) {
        long id = klant.getID();
        try {
            klantRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public String updateWachtwoord(Klant klant, String wachtwoord) {
        long id = klant.getID();
        try {
            klantRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public void updateRekeningnummer(Klant klant, String rekeningummer) {
        long id = klant.getID();
        klantRepository.updateRekeningummer(rekeningummer, id);

    }
    public void deleteKlantRow(long i) {
        long id = i;
        klantRepository.deleteRow(id);
    }
    public String addKlantRow() {
        try {
            klantRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public long getKlantId() {
        return klantRepository.getId();
    }
    //endregion



    //region [Manager]
    public List<Manager> managerList() {
        return (List<Manager>) managerRepository.findAll();
    }

    public void updateVoornaam(Manager model, String voornaam) {
        long id = model.getID();
        managerRepository.updateVoornaam(voornaam, id);
    }
    public void updateTussenvoegsel(Manager model, String tussenvoegsel) {
        long id = model.getID();
        managerRepository.updateTussenvoegsel(tussenvoegsel, id);
    }
    public void updateAchternaam(Manager model, String achternaam) {
        long id = model.getID();
        managerRepository.updateAchternaam(achternaam, id);
    }
    public String updateInlognaam(Manager model, String inlognaam) {
        long id = model.getID();
        try {
            managerRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public String updateWachtwoord(Manager model, String wachtwoord) {
        long id = model.getID();
        try {
            managerRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public void deleteManagerRow(long i) {
        long id = i;
        managerRepository.deleteRow(id);
    }
    public String addManagerRow() {
        try {
            managerRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public long getManagerId() {
        return managerRepository.getId();
    }
    //endregion
*/

    /*
    //region [Planner]
    public List<Planner> list() {
        return (List<Planner>) plannerRepository.findAll();
    }

    public void updateVoornaam(Planner model, String voornaam) {
        long id = model.getID();
        plannerRepository.updateVoornaam(voornaam, id);
    }
    public void updatePlannerTussenvoegsel(Planner model, String tussenvoegsel) {
        long id = model.getID();
        plannerRepository.updateTussenvoegsel(tussenvoegsel, id);
    }
    public void updateAchternaam(Planner model, String achternaam) {
        long id = model.getID();
        plannerRepository.updateAchternaam(achternaam, id);
    }
    public String updateInlognaam(Planner model, String inlognaam) {
        long id = model.getID();
        try {
            plannerRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public String updateWachtwoord(Planner model, String wachtwoord) {
        long id = model.getID();
        try {
            plannerRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public void deletePlannerRow(long i) {
        long id = i;
        plannerRepository.deleteRow(id);
    }
    public String addPlannerRow() {
        try {
            plannerRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }
    public long getPlannerId() {
        return plannerRepository.getId();
    }
    //endregion
    */


}
