package com.lisa.dorb.function;

import com.lisa.dorb.model.*;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.values.strings;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
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
        long klant_Id;
        String adres;
        long prijs;
        Date datum;
        long pallet_Id;
        long rit_Id;

//        try {
            long land_Id = landenRepository.getLand_IdByLanden(land);
        float km = 0;
        try {
            km = Float.valueOf(route.getDistance(strings.ADRES_FROM, adr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (checkRit(palletAantal, dat, land_Id, adr) != null) {
                //get vars from checkrit en vul db
                return send;
            } else {
                if (findVrachtwagen(dat, palletAantal, 0 , 0) != null) {
                    //get vars from vrachtwagen
                    long vrachtwagen_Id = findVrachtwagen(dat, palletAantal, 0 ,0).getID();
                    if (findChauffeur(dat, vrachtwagen_Id, land_Id) != null) {
                        //get vars from chauffeur
                        findPrijs(km, land, palletAantal);
                        //to db
                        return "new rit added";
                    }
                }
            }
//        }catch (Exception e){
//            return ""+e;
//        }
        return send;
    }

    public Rit checkRit(int palletAantal, Date datum, long landId, String adrs){ //maak miss class
        List<Rit> allRitten;
            allRitten = ritRepository.getByDatum(datum);
            Vrachtwagen vrachtwagen;
            Chauffeur chauffeur;
            if(!allRitten.isEmpty()) {
                //try {
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

//                                volgende = checkVolgorde(volgende, palletAantal, ruimteRit);
//                                if (volgende != vrachtwagenTypeRepository.getVolgordeById(typ_Id)) {
//                                    typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
                                if (findVrachtwagen(datum, palletAantal, volgende, ruimteRit) != null) {
                                    send = "not grootst";
                                    vrachtwagen = findVrachtwagen(datum, palletAantal, volgende, ruimteRit);

                                    String rijbewijsTypeVrachtwagen = vrachtwagenTypeRepository.getRijbewijsById(typ_Id);
                                    String rijbewijsChauffeur = chauffeurRepository.getRijbewijsById(chauffeur_Id);
                                    if (rijbewijsChauffeur.equals(rijbewijsTypeVrachtwagen)) {
                                        newRitten.add(rit);
                                        send = "added to list";
//                                    } else if(rijbewijsChauffeur.equals("D")){
//                                        newRitten.add(rit);
//                                        send = "added to list with D";
                                    } else {
                                        List<Order> adressen = orderRepository.getAdresByRit_Id(rit_Id);
                                        adressen.add(new Order(0,0, adrs, 0, null, 0, 0, landId));
                                        List<String> newAdressen = new ArrayList<>();
                                        //eerst nog naar adres van order
                                        for (Order adres : adressen) {
                                            long land_Id = adres.getLand_Id();
                                            if (findChauffeur(datum, vrachtwagen.getID(), land_Id) != null) {
                                                chauffeur = findChauffeur(datum, vrachtwagen.getID(), land_Id); //dit klopt niet want er wordt van 1 adres gezocht terwijl er meer zijn maak lijst en vergelijk (maar met wat)laat de list die je krijgt van findchauffeurs vergelijken met  andere list die je daarvan krijgt. als er mensen gelijk zijn add to
                                                newAdressen.add(adres.getAdres());
                                            }
                                        }
                                        if (!newAdressen.isEmpty()) {
                                            newRitten.add(rit);
                                            send = "added chauffeur";
                                        }
                                    }
                                    //}
                                }
                            }
                        }
                    }
//                }catch (Exception e){
//                    send = ""+e;
//                }
                if(!newRitten.isEmpty()) {
                    Rit ritToAdd = newRitten.get(0);
                    return ritToAdd;
                }
            } else {
                return null;
            }
            return null;
    }


    private Chauffeur findChauffeur(Date datum, long vrachtwagen_Id, long land_Id) {
        //check op werkdagen
        List<Chauffeur> allChauffeurs = new ArrayList<>();
        String rijbewijs = vrachtwagenTypeRepository.getRijbewijsById(vrachtwagenRepository.getTyp_IdById(vrachtwagen_Id));
        List<Rit> chauffeurList = ritRepository.getByDatum(datum);
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
        if(!allChauffeurs.isEmpty()){
            return allChauffeurs.get(0);
        }
        return null;
    }

    private float findPrijs(float km, String land, long palletAantal){
        long prijsLand = prijsRepository.getPrijsByWat(land);
        long prijsPallets = prijsRepository.getPrijsByWat(palletAantal+"Pallet");
        return (prijsPallets * km) + prijsLand;
    }

    private Vrachtwagen findVrachtwagen(Date datum, int palletAantal, long volg, long ruimte) {
        List<Vrachtwagen> allVrachtwagens = new ArrayList<>();
        long volgende = checkVolgorde(volg, palletAantal,ruimte);
        if (volgende != volg) {
            long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
            List<Rit> vrachtwagenRit = ritRepository.getByDatum(datum);
            for (Rit rit : vrachtwagenRit) {
                List<Vrachtwagen> vrachtwagenList = vrachtwagenRepository.getIdByTyp_IdAndBeschikbaarheidAndApk(typ_Id, datum, "beschikbaar");
                for (Vrachtwagen vrachtwagen : vrachtwagenList) {
                    if (Integer.parseInt(rit.getVrachtwagen_Id()) != vrachtwagen.getID()) {
                        allVrachtwagens.add(vrachtwagen);
                    } else{
                        send = "problem here";
                    }
                }
            }
        } else{
            send = "here is problem";
        }
        if(!allVrachtwagens.isEmpty()){
            return allVrachtwagens.get(0);
        } else {
            send = "still null";
        }
        return null;
    }

//    private Vrachtwagen findVrachtwagenFromRit(long volgende, Date datum, long vrachtwagen_IdRit){
//        long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
//        List<Vrachtwagen> allVrachtwagens = vrachtwagenRepository.getIdByTyp_IdAndBeschikbaarheidAndApk(typ_Id, datum, "beschikbaar");
//        List<Vrachtwagen> newVrachtwagens = new ArrayList<>();
//        for(Vrachtwagen vrachtwagen: allVrachtwagens){
//            long vrachtwagen_IdVrachtwagen = vrachtwagen.getID();
//                if(vrachtwagen_IdVrachtwagen != vrachtwagen_IdRit){
//                    newVrachtwagens.add(vrachtwagen);
//                }
//        }
//        if(!newVrachtwagens.isEmpty()){
//            return newVrachtwagens.get(0);
//        }
//        return null;
//    }

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

    //List 1 = first find dus miss met land die je al hebt van nieuwe order
    //
    //for(orders)
    //get list
    //if (list 1 vars == aan get list)
    //add vars to list 1

    //get list 1 vars(0) moet dus in alle landen zijn
    //(maar je krijgt hier meerdere zelde in list 1


}
