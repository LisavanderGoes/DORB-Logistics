package com.lisa.dorb.repository;

import com.lisa.dorb.model.DB.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    @Query(value = "SELECT * FROM chauffeurs", nativeQuery = true)
    List<Chauffeur> findAll();

//    @Query(value = "SELECT wachtwoord FROM chauffeurs WHERE inlognaam = ?1", nativeQuery = true)
//    String findWachtwoordByInlognaam(String inlognaam);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET voornaam = :voornaam WHERE user_Id = :id", nativeQuery = true)
//    void updateVoornaam(@Param("voornaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET tussenvoegsel = :tussenvoegsel WHERE user_Id = :id", nativeQuery = true)
//    void updateTussenvoegsel(@Param("tussenvoegsel") String tussenvoegsel, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET achternaam = :achternaam WHERE user_Id = :id", nativeQuery = true)
//    void updateAchternaam(@Param("achternaam") String achternaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET inlognaam = :inlognaam WHERE user_Id = :id", nativeQuery = true)
//    void updateInlognaam(@Param("inlognaam") String inlognaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET wachtwoord = :wachtwoord WHERE user_Id = :id", nativeQuery = true)
//    void updateWachtwoord(@Param("wachtwoord") String wachtwoord, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET rijbewijs = :rijbewijs WHERE user_Id = :id", nativeQuery = true)
//    void updateRijbewijs(@Param("rijbewijs") String rijbewijs, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE chauffeurs SET werkdagen = :werkdagen WHERE user_Id = :id", nativeQuery = true)
//    void updateWerkdagen(@Param("werkdagen") long werkdagen, @Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM chauffeurs WHERE user_Id =:id", nativeQuery = true)
//    void deleteRow(@Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO chauffeurs (voornaam, tussenvoegsel, achternaam, rijbewijs, werkdagen, inlognaam, wachtwoord)" +
//            "VALUES ('', '', '', 'C', 0, '', '');", nativeQuery = true)
//    void addRow();

    @Query(value = "SELECT user_Id FROM chauffeurs ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
    long getId();

    @Query(value = "SELECT rijbewijs FROM chauffeurs WHERE id = :id", nativeQuery = true)
    String getRijbewijsById(@Param("id") long id);

    @Query(value = "SELECT * FROM chauffeurs WHERE rijbewijs = :rijbewijs", nativeQuery = true)
    List<Chauffeur> getAllByRijbewijs(@Param("rijbewijs") String rijbewijs);

    @Query(value = "SELECT * FROM chauffeurs WHERE id = :id", nativeQuery = true)
    Chauffeur findAllById(@Param("id") long id);

    @Query(value = "SELECT Id FROM chauffeurs WHERE user_Id =:id", nativeQuery = true)
    long getIdByUser_Id(@Param("id") long id);
}
