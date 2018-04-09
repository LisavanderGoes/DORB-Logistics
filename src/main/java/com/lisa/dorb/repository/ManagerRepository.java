package com.lisa.dorb.repository;

import com.lisa.dorb.model.Klant;
import com.lisa.dorb.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @Query(value = "SELECT * FROM managers", nativeQuery = true)
    List<Manager> findAll();

    @Query(value = "SELECT wachtwoord FROM managers WHERE inlognaam = :inlognaam", nativeQuery = true)
    String findWachtwoordByInlognaam(@Param("inlognaam")String inlognaam);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE managers SET voornaam = :voornaam WHERE user_Id = :id", nativeQuery = true)
    void updateVoornaam(@Param("voornaam") String voornaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE managers SET tussenvoegsel = :tussenvoegsel WHERE user_Id = :id", nativeQuery = true)
    void updateTussenvoegsel(@Param("tussenvoegsel") String voornaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE managers SET achternaam = :achternaam WHERE user_Id = :id", nativeQuery = true)
    void updateAchternaam(@Param("achternaam") String voornaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE managers SET inlognaam = :inlognaam WHERE user_Id = :id", nativeQuery = true)
    void updateInlognaam(@Param("inlognaam") String voornaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE managers SET wachtwoord = :wachtwoord WHERE user_Id = :id", nativeQuery = true)
    void updateWachtwoord(@Param("wachtwoord") String voornaam, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM managers WHERE user_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO managers (voornaam, tussenvoegsel, achternaam, inlognaam, wachtwoord)" +
            "VALUES ('', '', '', '', '');", nativeQuery = true)
    void addRow();

    @Query(value = "SELECT user_Id FROM managers ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
    long getId();
}
