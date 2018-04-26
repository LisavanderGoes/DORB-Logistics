package com.lisa.dorb.repository;

import com.lisa.dorb.model.db.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT * FROM users WHERE user_Id = :id", nativeQuery = true)
    User findAllById(@Param("id") long id);

    @Query(value = "SELECT user_Id FROM users WHERE inlognaam = :inlognaam", nativeQuery = true)
    Long getIdByInlognaam(@Param("inlognaam") String inlognaam);

    @Query(value = "SELECT * FROM users WHERE inlognaam = :inlognaam AND wachtwoord = :wachtwoord", nativeQuery = true)
    User findAllByInlognaamAndWachtwoord(@Param("inlognaam") String inlognaam, @Param("wachtwoord") String wachtwoord);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET voornaam = :voornaam WHERE user_Id = :id", nativeQuery = true)
    void updateVoornaam(@Param("voornaam") String voornaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET tussenvoegsel = :tussenvoegsel WHERE user_Id = :id", nativeQuery = true)
    void updateTussenvoegsel(@Param("tussenvoegsel") String tussenvoegsel, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET achternaam = :achternaam WHERE user_Id = :id", nativeQuery = true)
    void updateAchternaam(@Param("achternaam") String achternaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET inlognaam = :inlognaam WHERE user_Id = :id", nativeQuery = true)
    void updateInlognaam(@Param("inlognaam") String inlognaam, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET wachtwoord = :wachtwoord WHERE user_Id = :id", nativeQuery = true)
    void updateWachtwoord(@Param("wachtwoord") String wachtwoord, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE user_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (voornaam, tussenvoegsel, achternaam, inlognaam, wachtwoord)" +
            "VALUES (:voornaam, :tussenvoegsel, :achternaam, :inlognaam, :wachtwoord);", nativeQuery = true)
    void addRow(@Param("voornaam") String voornaam, @Param("tussenvoegsel") String tussenvoegsel, @Param("achternaam") String achternaam, @Param("inlognaam") String inlognaam, @Param("wachtwoord") String wachtwoord);

    @Query(value = "SELECT user_Id FROM users ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
    long getId();
}
