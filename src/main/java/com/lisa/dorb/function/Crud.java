package com.lisa.dorb.function;

import com.lisa.dorb.layout.CrudUI;
import com.lisa.dorb.model.Admin;
import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.model.Klant;
import com.lisa.dorb.repository.AdminRepository;
import com.lisa.dorb.repository.ChauffeurRepository;
import com.lisa.dorb.repository.KlantRepository;
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
    AdminRepository adminRepository;
    @Autowired
    PlannerRepository plannerRepository;
    @Autowired
    CrudUI crudUI;

    //region [Admin]
    /**
     * @return list of all managers
     */
    public List<Admin> adminList() {
        return (List<Admin>) adminRepository.findAll();
    }

    /**
     * @param admin Admin model
     * @param voornaam The new voornaam
     */
    public void updateAdminVoornaam(Admin admin, String voornaam) {
        long id = admin.getID();
        adminRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param admin Admin model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateAdminTussenvoegsel(Admin admin, String tussenvoegsel) {
        long id = admin.getID();
        adminRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param admin Admin model
     * @param achternaam The new achternaam
     */
    public void updateAdminAchternaam(Admin admin, String achternaam) {
        long id = admin.getID();
        adminRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param admin Admin model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateAdminInlognaam(Admin admin, String inlognaam) {
        long id = admin.getID();
        try {
            adminRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param admin Admin model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateAdminWachtwoord(Admin admin, String wachtwoord) {
        long id = admin.getID();
        try {
            adminRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param i id from selection
     */
    public void deleteAdminRow(long i) {
        long id = i;
        adminRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addAdminRow() {
        try {
            adminRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getAdminId() {
        return adminRepository.getId();
    }
    //endregion

    //region [Klant]
    /**
     * @return list of all Klanten
     */
    public List<Klant> klantList() {
        return (List<Klant>) klantRepository.findAll();
    }

    /**
     * @param klant Klant model
     * @param voornaam The new voornaam
     */
    public void updateKlantVoornaam(Klant klant, String voornaam) {
        long id = klant.getID();
        klantRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param klant Klant model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateKlantTussenvoegsel(Klant klant, String tussenvoegsel) {
        long id = klant.getID();
        klantRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param klant Klant model
     * @param achternaam The new achternaam
     */
    public void updateKlantAchternaam(Klant klant, String achternaam) {
        long id = klant.getID();
        klantRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param klant Klant model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateKlantInlognaam(Klant klant, String inlognaam) {
        long id = klant.getID();
        try {
            klantRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param klant Klant model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateKlantWachtwoord(Klant klant, String wachtwoord) {
        long id = klant.getID();
        try {
            klantRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }


    /**
     * @param klant M
     * @param rekeningummer
     */
    public void updateKlantRekeningnummer(Klant klant, String rekeningummer) {
        long id = klant.getID();
        klantRepository.updateRekeningummer(rekeningummer, id);

    }

    /**
     * @param i id from selection
     */
    public void deleteKlantRow(long i) {
        long id = i;
        klantRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addKlantRow() {
        try {
            klantRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getKlantId() {
        return klantRepository.getId();
    }
    //endregion

    //region [Chauffeur]
    /**
     * @return list of all managers
     */
    public List<Chauffeur> chauffeursList() {
        return (List<Chauffeur>) chauffeurRepository.findAll();
    }

    /**
     * @param chauffeur Chauffeur model
     * @param voornaam The new voornaam
     */
    public void updateChauffeurVoornaam(Chauffeur chauffeur, String voornaam) {
        long id = chauffeur.getID();
        chauffeurRepository.updateVoornaam(voornaam, id);
    }

    /**
     * @param chauffeur Admin model
     * @param tussenvoegsel The new tussenvoegsel
     */
    public void updateChauffeurTussenvoegsel(Chauffeur chauffeur, String tussenvoegsel) {
        long id = chauffeur.getID();
        chauffeurRepository.updateTussenvoegsel(tussenvoegsel, id);
    }

    /**
     * @param chauffeur Chauffeur model
     * @param achternaam The new achternaam
     */
    public void updateChauffeurAchternaam(Chauffeur chauffeur, String achternaam) {
        long id = chauffeur.getID();
        chauffeurRepository.updateAchternaam(achternaam, id);
    }

    /**
     * @param chauffeur Chauffeur model
     * @param inlognaam The new inlognaam
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateChauffeurInlognaam(Chauffeur chauffeur, String inlognaam) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateInlognaam(inlognaam, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param chauffeur Chauffeur model
     * @param wachtwoord The new wachtwoord
     * @return null if no errors, a inlognaam with the exception
     */
    public String updateChauffeurWachtwoord(Chauffeur chauffeur, String wachtwoord) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateWachtwoord(wachtwoord, id);
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @param chauffeur Chauffeur model
     * @param rijbewijs The new werkdagen
     */
    public String updateChauffeurRijbewijs(Chauffeur chauffeur, String rijbewijs) {
        long id = chauffeur.getID();
        try {
            chauffeurRepository.updateRijbewijs(rijbewijs, id);
        }catch (Exception e){
            return "Rijbewijs kan alleen C of D zijn!";
        }
        return null;
    }

    /**
     * @param chauffeur Chauffeur model
     * @param werkdagen The new werkdagen
     */
    public void updateChauffeurWerkdagen(Chauffeur chauffeur, long werkdagen) {
        long id = chauffeur.getID();
        chauffeurRepository.updateWerkdagen(werkdagen, id);
    }

    /**
     * @param i id from selection
     */
    public void deleteChauffeurRow(long i) {
        long id = i;
        chauffeurRepository.deleteRow(id);
    }

    /**
     * @return null if no errors, a inlognaam with the exception
     */
    public String addChauffeurRow() {
        try {
            chauffeurRepository.addRow();
        } catch (Exception e) {
            return "Inlognaam en wachtwoord kunnen niet twee keer hetzelde zijn!";
        }
        return null;
    }

    /**
     * @return last added id
     */
    public long getChauffeurId() {
        return chauffeurRepository.getId();
    }
    //endregion
}
