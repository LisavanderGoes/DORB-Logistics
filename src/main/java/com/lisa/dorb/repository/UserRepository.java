package com.lisa.dorb.repository;

import com.lisa.dorb.model.Planner;
import com.lisa.dorb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT * FROM users WHERE user_Id = :id", nativeQuery = true)
    User findAllById(@Param("id") long id);

    @Query(value = "SELECT * FROM users WHERE inlognaam = :inlognaam AND wachtwoord = :wachtwoord", nativeQuery = true)
    User findAllByInlognaamAndWachtwoord(@Param("inlognaam") String inlognaam, @Param("wachtwoord") String wachtwoord);


//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE planners SET voornaam = :voornaam WHERE user_Id = :id", nativeQuery = true)
//    void updateVoornaam(@Param("voornaam") String voornaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE planners SET tussenvoegsel = :tussenvoegsel WHERE user_Id = :id", nativeQuery = true)
//    void updateTussenvoegsel(@Param("tussenvoegsel") String tussenvoegsel, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE planners SET achternaam = :achternaam WHERE user_Id = :id", nativeQuery = true)
//    void updateAchternaam(@Param("achternaam") String achternaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE planners SET inlognaam = :inlognaam WHERE user_Id = :id", nativeQuery = true)
//    void updateInlognaam(@Param("inlognaam") String inlognaam, @Param("id") long id);
//
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = "UPDATE planners SET wachtwoord = :wachtwoord WHERE user_Id = :id", nativeQuery = true)
//    void updateWachtwoord(@Param("wachtwoord") String wachtwoord, @Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "DELETE FROM planners WHERE user_Id =:id", nativeQuery = true)
//    void deleteRow(@Param("id") long id);
//
//    @Transactional
//    @Modifying
//    @Query(value = "INSERT INTO planners (voornaam, tussenvoegsel, achternaam, inlognaam, wachtwoord)" +
//            "VALUES ('', '', '', '', '');", nativeQuery = true)
//    void addRow();
//
//    @Query(value = "SELECT user_Id FROM planners ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
//    long getId();
}
