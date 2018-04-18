package com.lisa.dorb.repository;

import java.util.List;

import com.lisa.dorb.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lisa.dorb.model.Klant;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;

public interface KlantRepository extends CrudRepository<Klant, Long> {

    @Query(value = "SELECT * FROM klanten", nativeQuery = true)
    List<Klant> findAll();

//    @Query(value = "SELECT wachtwoord FROM klanten WHERE inlognaam = ?1", nativeQuery = true)
//    String findWachtwoordByInlognaam(String inlognaam);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE klanten SET voornaam = :voornaam WHERE user_Id = :id", nativeQuery = true)
//    void updateVoornaam(@Param("voornaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE klanten SET tussenvoegsel = :tussenvoegsel WHERE user_Id = :id", nativeQuery = true)
//    void updateTussenvoegsel(@Param("tussenvoegsel") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE klanten SET achternaam = :achternaam WHERE user_Id = :id", nativeQuery = true)
//    void updateAchternaam(@Param("achternaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE klanten SET inlognaam = :inlognaam WHERE user_Id = :id", nativeQuery = true)
//    void updateInlognaam(@Param("inlognaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE klanten SET wachtwoord = :wachtwoord WHERE user_Id = :id", nativeQuery = true)
//    void updateWachtwoord(@Param("wachtwoord") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM klanten WHERE user_Id =:id", nativeQuery = true)
//    void deleteRow(@Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO klanten (voornaam, tussenvoegsel, achternaam, rekeningnummer, inlognaam, wachtwoord)" +
//            "VALUES ('', '', '', '', '', '');", nativeQuery = true)
//    void addRow();
//
//    @Query(value = "SELECT user_Id FROM klanten ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
//    long getId();
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE klanten SET rekeningnummer = :rekeningnummer WHERE user_Id = :id", nativeQuery = true)
//    void updateRekeningummer(@Param("rekeningnummer") String rekeningummer, @Param("id") long id);
}
