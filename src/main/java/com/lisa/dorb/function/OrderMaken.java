package com.lisa.dorb.function;

import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.model.Order;
import com.lisa.dorb.model.Rit;
import com.lisa.dorb.model.Vrachtwagen;
import com.lisa.dorb.repository.*;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Date;
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

    private List<Rit> newRitten = null;

    public boolean checkRit(int palletAantal, Date datum){
        try {
            List<Rit> allRitten = ritRepository.getByDatum(datum);
            Vrachtwagen vrachtwagen;
            Chauffeur chauffeur;
            for(Rit rit : allRitten){
                long rit_Id = rit.getID();
                long chauffeur_Id = Integer.parseInt(rit.getChauffeur_Id());
                long vrachtwagen_IdRit = Integer.parseInt(rit.getVrachtwagen_Id());
                int ruimteRit = Integer.parseInt(rit.getRuimte());
                long typ_Id = vrachtwagenRepository.getTyp_IdById(Integer.parseInt(rit.getVrachtwagen_Id()));
                long ruimteTyp = vrachtwagenTypeRepository.getRuimteById(typ_Id);
                if(ruimteRit + palletAantal <= ruimteTyp){
                    rit.setRuimte(ruimteRit + palletAantal);
                    newRitten.add(rit);
                } else{
                    String grootst = vrachtwagenTypeRepository.getGrootstById(typ_Id);
                    if (!grootst.equals("true")) {
                        long volgende = vrachtwagenTypeRepository.getVolgordeById(typ_Id);
                        volgende = checkVolgorde(volgende, palletAantal, ruimteRit);
                        if (volgende != vrachtwagenTypeRepository.getVolgordeById(typ_Id)) {
                            typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
                            if(findVrachtwagenFromRit(volgende, datum, vrachtwagen_IdRit) != null) {
                                vrachtwagen = findVrachtwagenFromRit(volgende, datum, vrachtwagen_IdRit);
                                String rijbewijsTypeVrachtwagen = vrachtwagenTypeRepository.getRijbewijsById(typ_Id);
                                String rijbewijsChauffeur = chauffeurRepository.getRijbewijsById(chauffeur_Id);
                                if (rijbewijsChauffeur.equals(rijbewijsTypeVrachtwagen)) {
                                    newRitten.add(rit);
                                } else {
                                    List<Order> adressen = orderRepository.getAdresByRit_Id(rit_Id);
                                    List<Order> newAdressen = null;
                                    for (Order adres : adressen) {
                                        long order_Id = adres.getID();
                                        long land_Id = orderRepository.getLand_IdById(order_Id);
                                        if(findChauffeur(datum, vrachtwagen.getID(), land_Id) != null) {
                                            chauffeur = findChauffeur(datum, vrachtwagen.getID(), land_Id);
                                            newAdressen.add(adres);
                                        }

                                    }
                                    if(newAdressen != null){
                                        newRitten.add(rit);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(newRitten != null){
                Rit ritToAdd = newRitten.get(0);
            }

        }catch(NullPointerException e){
            return false;
        }
        return false;
    }

    public Chauffeur findChauffeur(Date datum, long vrachtwagen_Id, long land_Id) {
        List<Chauffeur> allChauffeurs = null;
        if(allChauffeurs != null){
            return allChauffeurs.get(0);
        }
        return null;
    }

    private Vrachtwagen findVrachtwagenFromRit(long volgende, Date datum, long vrachtwagen_IdRit){
        long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
        List<Vrachtwagen> allVrachtwagens = vrachtwagenRepository.getIdByTyp_IdAndBeschikbaarheid(typ_Id);
        List<Vrachtwagen> newVrachtwagens = null;
        for(Vrachtwagen vrachtwagen: allVrachtwagens){
            long vrachtwagen_IdVrachtwagen = vrachtwagen.getID();
            if(vrachtwagen_IdVrachtwagen != vrachtwagen_IdRit){
                newVrachtwagens.add(vrachtwagen);
            }
        }
        if(newVrachtwagens != null){
            return newVrachtwagens.get(0);
        }
        return null;
    }

    private long checkVolgorde(long volgende, int palletAantal, long ruimteRit){
        volgende++;
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




}
