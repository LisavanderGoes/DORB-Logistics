package com.lisa.dorb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lisa.dorb.model.db.users.Klant;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface KlantRepository extends CrudRepository<Klant, Long> {

    @Query(value = "SELECT * FROM klanten", nativeQuery = true)
    List<Klant> findAll();

    @Query(value = "SELECT * FROM klanten WHERE Id = :id", nativeQuery = true)
    Klant findAllById(@Param("id") long id);

    @Query(value = "SELECT * FROM klanten WHERE user_Id = :id", nativeQuery = true)
    Klant findAllByUser_Id(@Param("id") long id);
//
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM klanten WHERE user_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);
//
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO klanten (user_Id, rekeningnummer)" +
            "VALUES (:id, :rekeningnummer);", nativeQuery = true)
    void addRow(@Param("rekeningnummer") String rekeningummer, @Param("id") long id);
//
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE klanten SET rekeningnummer = :rekeningnummer WHERE user_Id = :id", nativeQuery = true)
    void updateRekeningummer(@Param("rekeningnummer") String rekeningummer, @Param("id") long id);

    @Query(value = "SELECT Id FROM klanten WHERE user_Id =:id", nativeQuery = true)
    long getIdByUser_Id(@Param("id") long id);

    @Query(value = "SELECT user_Id FROM klanten WHERE Id =:id", nativeQuery = true)
    long getUser_IdById(@Param("id") long id);
}
