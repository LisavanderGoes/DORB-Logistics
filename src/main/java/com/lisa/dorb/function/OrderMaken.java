package com.lisa.dorb.function;

import com.lisa.dorb.model.db.users.Chauffeur;
import com.lisa.dorb.saved.UserInfo;
import com.lisa.dorb.layout.order.OrderUI;
import com.lisa.dorb.model.*;
import com.lisa.dorb.model.db.*;
import com.lisa.dorb.repository.*;
import com.lisa.dorb.values.strings;
import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    NatioRepository natioRepository;
    @Autowired
    PrijsRepository prijsRepository;
    @Autowired
    LandenRepository landenRepository;
    @Autowired
    KlantRepository klantRepository;
    @Autowired
    Route route;
    @Autowired
    OrderUI orderUI;

    private String send = "";

    /**
     * @param datum = datum van order
     * @param adr = adres van bestemming
     * @param land = land van bestemming
     * @param palletsList = list met wat en palletaantal
     * @return = NewOrder: order, rit, pallet(model) of null
     */
    public NewOrder makeOrder(Date datum, String adr, String land, List<StringLongModel> palletsList){
        long klant_Id = klantRepository.getIdByUser_Id(UserInfo.user_Id);
        String adres = "";
        try {
            adres = route.getAdres(strings.ADRES_FROM, adr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Pallet> pallet = new ArrayList<>();
        int palletAantal = 0;
        for(StringLongModel pallets: palletsList){
            palletAantal = (int) (palletAantal + pallets.get_long());
            pallet.add(new Pallet(0, 0, pallets.getString(), pallets.get_long()));
        }
        String prijs;
        Order order;
        Rit rit;

            long land_Id = landenRepository.getLand_IdByLanden(land);
            float km = 0;
            try {
                km = Float.valueOf(route.getDistance(strings.ADRES_FROM, adr));
            } catch (Exception e) {
                e.printStackTrace();
            }

            prijs = String.valueOf(round(findPrijs(km, land, palletAantal), 2));
            if (checkRit(palletAantal, datum, land_Id) != null) {
                rit = checkRit(palletAantal, datum, land_Id);
                order = new Order(0, klant_Id, adres, prijs, datum, 0, land_Id, palletAantal);
                return new NewOrder(order, rit, pallet);
            } else {
                if (findVrachtwagen(datum, palletAantal, 1 , 0) != null) {
                    Vrachtwagen vrachtwagen;
                    Chauffeur chauffeur;
                    long vrachtwagen_Id = findVrachtwagen(datum, palletAantal, 1 , 0).get(0);
                    if (findChauffeur(datum, vrachtwagen_Id, land_Id) != null) {
                        //get vars from chauffeur
                        vrachtwagen = vrachtwagenRepository.findAllById(findVrachtwagen(datum, palletAantal, 1, 0).get(0));
                        chauffeur = chauffeurRepository.findAllById(findChauffeur(datum, vrachtwagen_Id, land_Id).get(0));
                        order = new Order(0, klant_Id, adres, prijs, datum, 0, land_Id, palletAantal);
                        rit = new Rit(0, vrachtwagen.getID(), palletAantal, datum, chauffeur.getID());
                        return new NewOrder(order, rit, pallet);
                    } else {
                        orderUI.send.setValue(send);
                    }
                } else {
                    orderUI.send.setValue(send);
                }
            }
        return null;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * @param palletAantal = aantal pallets
     * @param datum = datum van order
     * @param landId = id van land van bestemming
     * @return rit(model) of null
     */
    private Rit checkRit(int palletAantal, Date datum, long landId){ //maak miss class
        List<Rit> newRitten = new ArrayList<>(); //Maak een vrachtwagenList nooit null!!!!
        List<Rit> allRitten = ritRepository.getByDatum(datum);
            Vrachtwagen vrachtwagen;
            Chauffeur chauffeur;
            if(!allRitten.isEmpty()) {
                    for (Rit rit : allRitten) {
                        long rit_Id = rit.getID();
                        long chauffeur_Id = Integer.parseInt(rit.getChauffeur_Id());
                        int ruimteRit = Integer.parseInt(rit.getRuimte());
                        long typ_Id = vrachtwagenRepository.getTyp_IdById(Integer.parseInt(rit.getVrachtwagen_Id()));
                        long ruimteTyp = vrachtwagenTypeRepository.getRuimteById(typ_Id);
                        if (ruimteRit + palletAantal <= ruimteTyp) {
                            rit.setRuimte(ruimteRit + palletAantal);
                            newRitten.add(rit);
                        } else {
                            String grootst = vrachtwagenTypeRepository.getGrootstById(typ_Id);
                            if (!grootst.equals("true")) {
                                long volgende = vrachtwagenTypeRepository.getVolgordeById(typ_Id);
                                if (findVrachtwagen(datum, palletAantal, volgende, ruimteRit) != null) {
                                    vrachtwagen = vrachtwagenRepository.findAllById(findVrachtwagen(datum, palletAantal, volgende, ruimteRit).get(0));
                                    rit.setVrachtwagen_Id(vrachtwagen.getID());
                                    typ_Id = Integer.parseInt(vrachtwagen.getTyp_Id());
                                    rit.setRuimte(ruimteRit + palletAantal);
                                    String rijbewijsTypeVrachtwagen = vrachtwagenTypeRepository.getRijbewijsById(typ_Id);
                                    String rijbewijsChauffeur = chauffeurRepository.getRijbewijsById(chauffeur_Id);
                                    if (rijbewijsChauffeur.equals(rijbewijsTypeVrachtwagen)) {
                                        newRitten.add(rit);
                                    } else if(rijbewijsChauffeur.equals("D")){
                                        newRitten.add(rit);
                                    } else {
                                        List<Chauffeur> allChauffeurs = null;
                                        List<Long> firstChauffeurs = findChauffeur(datum, vrachtwagen.getID(), landId);
                                        List<Order> adressen = orderRepository.getAllByRit_Id(rit_Id);
                                        if (!firstChauffeurs.isEmpty()) {
                                            for (Order adres : adressen) {
                                                long land_Id = adres.getLand_Id();
                                                if (findChauffeur(datum, vrachtwagen.getID(), land_Id) != null) {
                                                    List<Long> secondChauffeurs = findChauffeur(datum, vrachtwagen.getID(), land_Id);
                                                    allChauffeurs = new ArrayList<>();
                                                    for (Long second : secondChauffeurs) {
                                                        for (Long first : firstChauffeurs) {
                                                            if (second == first) {
                                                                allChauffeurs.add(chauffeurRepository.findAllById(second));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if(allChauffeurs != null) {
                                            if (!allChauffeurs.isEmpty()) {
                                                chauffeur = allChauffeurs.get(0);
                                                rit.setChauffeur_Id(chauffeur.getID());
                                                newRitten.add(rit);
                                            }
                                        }
                                    }
                                } else {
                                    return null;
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

    /**
     * @param datum = datum van order
     * @param vrachtwagen_Id = vrachtwagen_Id van rit
     * @param land_Id = id van land van bestemming
     * @return chauffeur(model)
     */
    public List<Long> findChauffeur(Date datum, long vrachtwagen_Id, long land_Id) {
        //check op werkdagen
        String rijbewijs = vrachtwagenTypeRepository.getRijbewijsById(vrachtwagenRepository.getTyp_IdById(vrachtwagen_Id));
        List<Rit> chauffeurList = ritRepository.getByDatum(datum);
        List<Chauffeur> user_IdByRijbewijs = chauffeurRepository.getAllByRijbewijs(rijbewijs);
        List<Long> newList = new ArrayList<>();
        if(!chauffeurList.isEmpty()) {
            for(Chauffeur chauffeur: user_IdByRijbewijs){
                List<Natio> landen = natioRepository.getAllByChauffeur_Id(chauffeur.getUser_Id());
                for(Natio natio : landen){
                    if(natio.getLand_Id() == land_Id){
                        newList.add(chauffeur.getID());
                    }
                }
            }
            for(Rit rit: chauffeurList){
                Long chauffeur_Id = Long.parseLong(rit.getChauffeur_Id());
                newList.remove(chauffeur_Id);
            }
        } else{
            for(Chauffeur chauffeur: user_IdByRijbewijs){
                List<Natio> landen = natioRepository.getAllByChauffeur_Id(chauffeur.getUser_Id());
                for(Natio natio : landen){
                    if(natio.getLand_Id() == land_Id){
                        newList.add(chauffeur.getID());
                    }
                }
            }
        }
        if(!newList.isEmpty()) {
            return newList;
        }
        return null;
    }

    /**
     * @param km = km tussen vertek en aankomst adres
     * @param land = land van bestemming
     * @param palletAantal = aantal pallets
     * @return prijs(double)
     */
    private double findPrijs(float km, String land, long palletAantal){
        double prijsLand = Double.valueOf(prijsRepository.getPrijsByWat(land));
        double prijsPallets;
        if(palletAantal<= 6) {
            prijsPallets = Double.valueOf(prijsRepository.getPrijsByWat(palletAantal + "Pallet"));
        } else {
            prijsPallets = Double.valueOf(prijsRepository.getPrijsByWat(6 + "Pallet"));
        }
        return (prijsPallets * km) + prijsLand;
    }

    /**
     * @param datum = datum van order
     * @param palletAantal = aantal pallets
     * @param volg = volgende in volgorde van vrachtwagen types
     * @param ruimte = aantal pallets in rit
     * @return vrachtwagen(model)
     */
    public List<Long> findVrachtwagen(Date datum, int palletAantal, long volg, long ruimte) {

        Long volgende = checkVolgorde(volg, palletAantal,ruimte);
        List<Long> newList = new ArrayList<>();
        if (volgende != null) {
            long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
            List<Rit> vrachtwagenRit = ritRepository.getByDatum(datum);
            List<Vrachtwagen> vrachtwagenList = vrachtwagenRepository.getAllByTyp_IdAndBeschikbaarheidAndApk(typ_Id, datum, "beschikbaar");
            if(!vrachtwagenRit.isEmpty()) {
                for(Vrachtwagen vrachtwagen : vrachtwagenList){
                    newList.add(vrachtwagen.getID());
                }
                    for (Rit rit : vrachtwagenRit) {
                        Long vrachtwagen_Id = Long.parseLong(rit.getVrachtwagen_Id());
                        newList.remove(vrachtwagen_Id);
                    }
            } else{
                List<Vrachtwagen> allVrachtwagens = vrachtwagenRepository.getAllByTyp_IdAndBeschikbaarheidAndApk(typ_Id, datum, "beschikbaar");
                for(Vrachtwagen vrachtwagen : allVrachtwagens){
                    Long vrachtwagen_Id = Long.valueOf(vrachtwagen.getID());
                    newList.add(vrachtwagen_Id);
                }
            }
        }
        if(!newList.isEmpty()){
            return newList;
        }
        return null;
    }

    /**
     * @param volgende = volgende in volgorde van typevrachtwagens
     * @param palletAantal = aantal pallets
     * @param ruimteRit = aantal pallets in rit
     * @return volgende in volgorde van typevrachtwagens
     */
    private Long checkVolgorde(Long volgende, int palletAantal, long ruimteRit){
        volgende = volgende + 1;
        long typ_Id = vrachtwagenTypeRepository.getIdByVolgorde(volgende);
        long ruimte = vrachtwagenTypeRepository.getRuimteById(typ_Id);
        if(ruimteRit + palletAantal <= ruimte){
            return volgende;
        } else {
            String grootst = vrachtwagenTypeRepository.getGrootstById(typ_Id);
            if (!grootst.equals("true")) {
                volgende = checkVolgorde(volgende, palletAantal, ruimteRit);
                return volgende;
            }
        }
        return null;
    }
}
