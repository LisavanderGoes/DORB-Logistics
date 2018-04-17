package com.lisa.dorb.function;

import com.lisa.dorb.model.*;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.values.strings;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class OrderMaken {

    @Autowired
    RitRepository ritRepository;
    @Autowired
    VrachtwagenRepository vrachtwagenRepository;
    @Autowired
    VrachtwagenTypeRepository vrachtwagenTypeRepository;
    @Autowired
    ChauffeurRepository chauffeurRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    LandRepository landRepository;
    @Autowired
    PrijsRepository prijsRepository;
    @Autowired
    LandenRepository landenRepository;
    @Autowired
    Route route;

    private List<Rit> newRitten = new ArrayList<>(); //Maak een list nooit null!!!!

    String send = "";

    public String makeOrder(Date dat, String adr, String land, int palletAantal){
        //moet nog bestelling in
        //check voor dagen dat chauffeur werkt
        long klant_Id;
        String adres;
        double prijs;
        Date datum;
        long pallet_Id;
        long rit_Id;

        try {
            long land_Id = landenRepository.getLand_IdByLanden(land);
            float km = 0;
            try {
                km = Float.valueOf(route.getDistance(strings.ADRES_FROM, adr));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (checkRit(palletAantal, dat, land_Id) != null) {
                    //get vars from checkrit en vul db
                    return send;
            } else {
                if (findVrachtwagen(dat, palletAantal, 0 , 0) != null) {
                    //get vars from vrachtwagen
                    Vrachtwagen vrachtwagen;
                    Chauffeur chauffeur;
                    long vrachtwagen_Id = findVrachtwagen(dat, palletAantal, 0 , 0).get(0).getID();
                    if (findChauffeur(dat, vrachtwagen_Id, land_Id) != null) {
                        //get vars from chauffeur
                        vrachtwagen = findVrachtwagen(dat, palletAantal, 0, 0).get(0);
                        chauffeur = findChauffeur(dat, vrachtwagen_Id, land_Id).get(0);
                        prijs = findPrijs(km, land, palletAantal);
                        //to db
                        send = "new rit added";
                    }
                }
            }
        }catch (Exception e){
            return ""+e;
        }
        return send;
    }

    public Rit checkRit(int palletAantal, Date datum, long landId){ //maak miss class
        List<Rit> allRitten = ritRepository.getByDatum(datum);
            //get:
            Vrachtwagen vrachtwagen;
            Chauffeur chauffeur;
            if(!allRitten.isEmpty()) {
                    for (Rit rit : allRitten) {
                        long rit_Id = rit.getID();
                        long chauffeur_Id = Integer.parseInt(rit.getChauffeur_Id());
                        long vrachtwagen_IdRit = Integer.parseInt(rit.getVrachtwagen_Id());
                        int ruimteRit = Integer.parseInt(rit.getRuimte());
                        long typ_Id = vrachtwagenRepository.getTyp_IdById(Integer.parseInt(rit.getVrachtwagen_Id()));
                        long ruimteTyp = vrachtwagenTypeRepository.getRuimteById(typ_Id);
                        send = "sad";
                        if (ruimteRit + palletAantal <= ruimteTyp) {
                            rit.setRuimte(ruimteRit - palletAantal);
                            newRitten.add(rit);
                            send = "space";
                        } else {
                            send = "no vrachtwagen";
                            String grootst = vrachtwagenTypeRepository.getGrootstById(typ_Id);
                            if (!grootst.equals("true")) {
                                long volgende = vrachtwagenTypeRepository.getVolgordeById(typ_Id);
                                if (findVrachtwagen(datum, palletAantal, volgende, ruimteRit) != null) {
                                    send = "not grootst";
                                    vrachtwagen = findVrachtwagen(datum, palletAantal, volgende, ruimteRit).get(0);
                                    String rijbewijsTypeVrachtwagen = vrachtwagenTypeRepository.getRijbewijsById(typ_Id);
                                    String rijbewijsChauffeur = chauffeurRepository.getRijbewijsById(chauffeur_Id);
                                    if (rijbewijsChauffeur.equals(rijbewijsTypeVrachtwagen)) {
                                        newRitten.add(rit);
                                        send = "added to list";
                                    } else if(rijbewijsChauffeur.equals("D")){
                                        newRitten.add(rit);
                                        send = "added to list with D";
                                    } else {
                                        List<Chauffeur> allChauffeurs = null;
                                        List<Chauffeur> firstChauffeurs = findChauffeur(datum, vrachtwagen.getID(), landId);
                                        List<Order> adressen = orderRepository.getAdresByRit_Id(rit_Id);
                                        if (firstChauffeurs != null) {
                                            for (Order adres : adressen) {
                                                long land_Id = adres.getLand_Id();
                                                if (findChauffeur(datum, vrachtwagen.getID(), land_Id) != null) {
                                                    List<Chauffeur> secondChauffeurs = findChauffeur(datum, vrachtwagen.getID(), land_Id);
                                                    allChauffeurs = new ArrayList<>();
                                                    for (Chauffeur second : secondChauffeurs) {
                                                        for (Chauffeur first : firstChauffeurs) {
                                                            if (second.getID() == first.getID()) {
                                                                allChauffeurs.add(second);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if(allChauffeurs != null) {
                                            if (!allChauffeurs.isEmpty()) {
                                                chauffeur = allChauffeurs.get(0);
                                                newRitten.add(rit);
                                                send = "added chauffeur";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                if(!newRitten.isEmpty()) {
                    Rit ritToAdd = newRitten.get(0);
                    return ritToAdd;
                }
            } else {
                return null;
            }
        return null;
    }

    private List<Chauffeur> findChauffeur(Date datum, long vrachtwagen_Id, long land_Id) {
        //check op werkdagen
        List<Chauffeur> allChauffeurs = new ArrayList<>();
        String rijbewijs = vrachtwagenTypeRepository.getRijbewijsById(vrachtwagenRepository.getTyp_IdById(vrachtwagen_Id));
        List<Rit> chauffeurList = ritRepository.getByDatum(datum);
        if(!chauffeurList.isEmpty()) {
            for(Rit ritChauffeur: chauffeurList){
                long chauffeur_Id = Integer.parseInt(ritChauffeur.getChauffeur_Id());
                List<Chauffeur> chauffeursUsers = new ArrayList<>();
                List<Chauffeur> user_IdByRijbewijs = chauffeurRepository.getAllByRijbewijs(rijbewijs);
                for(Chauffeur user : user_IdByRijbewijs) {
                    List<Land> landen = landRepository.getAllByChauffeur_Id(user.getID());
                    for(Land land: landen){
                        if(land.getLand_Id() == land_Id){
                            chauffeursUsers.add(user);
                        }
                    }
                }
                for(Chauffeur chauffeur : chauffeursUsers){
                    if(chauffeur_Id != chauffeur.getID()){
                        allChauffeurs.add(chauffeur);
                    }
                }
            }
        } else{
            List<Chauffeur> user_IdByRijbewijs = chauffeurRepository.getAllByRijbewijs(rijbewijs);
            for(Chauffeur user : user_IdByRijbewijs) {
                List<Land> landen = landRepository.getAllByChauffeur_Id(user.getID());
                for(Land land: landen){
                    if(land.getLand_Id() == land_Id){
                        allChauffeurs.add(user);
                    }
                }
            }
        }
        if(!allChauffeurs.isEmpty()){
            return allChauffeurs;
        }
        return null;
    }

    private double findPrijs(float km, String land, long palletAantal){
        double prijsLand = Double.valueOf(prijsRepository.getPrijsByWat(land));
        double prijsPallets = Double.valueOf(prijsRepository.getPrijsByWat(palletAantal+"Pallet"));
        return (prijsPallets * km) + prijsLand;
    }

    private List<Vrachtwagen> findVrachtwagen(Date datum, int palletAantal, long volg, long ruimte) {
        List<Vrachtwagen> allVrachtwagens = new ArrayList<>();
        long volgende = checkVolgorde(volg, palletAantal,ruimte);
        if (volgende != volg) {
            long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
            List<Rit> vrachtwagenRit = ritRepository.getByDatum(datum);
            if(!vrachtwagenRit.isEmpty()) {
                for (Rit rit : vrachtwagenRit) {
                    List<Vrachtwagen> vrachtwagenList = vrachtwagenRepository.getIdByTyp_IdAndBeschikbaarheidAndApk(typ_Id, datum, "beschikbaar");
                    for (Vrachtwagen vrachtwagen : vrachtwagenList) {
                        if (Integer.parseInt(rit.getVrachtwagen_Id()) != vrachtwagen.getID()) {
                            allVrachtwagens.add(vrachtwagen);
                        } else {
                            send = "problem here";
                        }
                    }
                }
            } else{
                allVrachtwagens = vrachtwagenRepository.getIdByTyp_IdAndBeschikbaarheidAndApk(typ_Id, datum, "beschikbaar");
                send = "geen ritten die datum";
            }
        } else{
            send = "here is problem";
        }
        if(!allVrachtwagens.isEmpty()){
            send = "tot hier";
            return allVrachtwagens;
        } else {
            send = "still null";
        }
        return null;
    }

    private long checkVolgorde(long volgende, int palletAantal, long ruimteRit){
        volgende = volgende + 1;
        long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
        long ruimte = vrachtwagenTypeRepository.getRuimteById(typ_Id);
        if(ruimteRit + palletAantal <= ruimte){
            return volgende;
        } else {
            String grootst = vrachtwagenTypeRepository.getGrootstById(typ_Id);
            if (!grootst.equals("true")) {
                volgende = checkVolgorde(volgende, palletAantal, ruimteRit);
            }
        }
        return volgende;
    }

//region [Rittencheck]
    //if rit !exists where datum == datum
            //return false
        //if rit exists where datum == datum
                //rit_Id *
                //ruimte
                //vrachtwagen_Id
                //chauffeur_Id *
        // get typ_Id from vrachtwagen where vrachtwagen_Id == vrachtwagen_Id
        // get ruimte van vrachtwagenType where typ_Id == typ_Id
        // if ruimte + palletAantal !> ruimte vrachtwagenType
            //DB update rit in ruimte where rit_Id == rit_Id
            //return true
        // if ruimte + palletAantal > ruimte vrachtwagenType
            // get grootst van vrachtwagenType
                // if grootst
                    //return false
                //if !grootst
                    //boolean check(typ_Id){
                    //typ_++
                    //get ruimte van vrachtwagenType where typ_Id == typ_Id++
                    // if ruimte + palletAantal > ruimte vrachtwagenType
                        // get grootst van vrachtwagenType
                            // if grootst
                                //return false
                            //if !grootst
                                //check(type_Id)
                        // if ruimte + palletAantal !> ruimte vrachtwagenType
                            //return typ_Id
                        //}
                        //findVrachtwagen(typ_++){
                            //get ruimte van typeVrachtwagens where typ_Id == typ_Id++(????????)
                            //get vrachtwagen_Id van rit where datum == datum
                            //get vrachtwagen_Id van vrachtwagen where status == beschikbaar && typ_Id == typ_Id++
                            //if vrachtwagen_Id != vrachtwagen_Id
                                //add to List(vrachtwagen_Id)
                                    //if List == null
                                        //return false
                                    //if List != null
                                        //get List(0) vrachtwagen_Id


                            //get rijbewijs van typeVrachtwagens where typ_Id == typ_Id
                            //get rijbewijs van chauffeurs where chauffeur_Id == chauffeur_Id*
                                //if rijbewijs_Id != rijbewijs_Id
                                    //get adres van order where rit_Id == rit_Id *

                                        //land = route(adres)
                                            //if findChauffeur(datum, vrachtwagen_Id, land) == null
                                                //return false
                                            //if findChauffeur(datum, vrachtwagen_Id, land) != null
                                                //DB update rit in vrachtwagen_Id && ruimte && chauffeur_Id where rit_Id == rit_Id
                                                //return true

                                //if rijbewijs_Id == rijbewijs_Id
                                    //DB update rit in vrachtwagen_Id && ruimte where rit_Id == rit_Id
                                    //return true

                                        //check ook op het land van nieuwe orde
//endregion

//region [Chauffeurcheck]
    //vrachtwagen_Id, land_Id*, datum
    //get typ_Id van vrachtwagen where vrachtwagen_Id == vrachtwagen_Id
    //get rijbewijs van Typevrachtwagens where typ_Id == typ_Id

    //get chauffeur_Id van rit where datum == datum
    //get user_Id van chauffeurs where rijbewijs == rijbewijs
    //for(user :user_Id)
        //get land_Id van nationaliteit where chauffeur_Id == user_Id
        //for(land: land_Id)
            //if land_Id == land_Id*
                //add use_Id to list
    //if chauffeur_Id == user_Id(list)
        //return false
    //if chauffeur_Id != user_Id
        //add to list
//endregion

//region [Vrachtwagencheck]

    //pallet aantal, datum
    //volgende = (checkvolgorde(0, pallet aantal, 0))
    //get typ_Id van typevrachtwagens where volgorde == volgende
    //get vrachtwagen_Id van rit where datum == datum
    //get vrachtwagen_Id van vrachtwagens where status == beschikbaar && typ_Id == typ_Id && apk != datum
    //if vrachtwagen_Id == vrachtwagen_Id)\
        //return null
    //if vrachtwagen_Id != vrachtwagen_Id
        //add to list

//endregion

//region [Prijsberekenen]
    //km, land, aantal pallets
    //get prijs van prijzen where wat == land
    //get prijs van prijzen where wat == pallet aantal
    //prijs(pallet) * km + prijs(land)
//endregiond
}
