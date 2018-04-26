package com.lisa.dorb.atrash;

import com.lisa.dorb.atrash.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT * FROM admins", nativeQuery = true)
    List<Admin> findAll();

//    @Query(value = "SELECT wachtwoord FROM admins WHERE inlognaam = :inlognaam", nativeQuery = true)
//    String findWachtwoordByInlognaam(@Param("inlognaam")String inlognaam);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE admins SET voornaam = :voornaam WHERE user_Id = :id", nativeQuery = true)
//    void updateVoornaam(@Param("voornaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE admins SET tussenvoegsel = :tussenvoegsel WHERE user_Id = :id", nativeQuery = true)
//    void updateTussenvoegsel(@Param("tussenvoegsel") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE admins SET achternaam = :achternaam WHERE user_Id = :id", nativeQuery = true)
//    void updateAchternaam(@Param("achternaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE admins SET inlognaam = :inlognaam WHERE user_Id = :id", nativeQuery = true)
//    void updateInlognaam(@Param("inlognaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE admins SET wachtwoord = :wachtwoord WHERE user_Id = :id", nativeQuery = true)
//    void updateRol(@Param("wachtwoord") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM admins WHERE user_Id =:id", nativeQuery = true)
//    void deleteRow(@Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO admins (voornaam, tussenvoegsel, achternaam, inlognaam, wachtwoord)" +
//            "VALUES ('', '', '', '', '');", nativeQuery = true)
//    void addRow();

    @Query(value = "SELECT user_Id FROM admins ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
    long getId();
}
