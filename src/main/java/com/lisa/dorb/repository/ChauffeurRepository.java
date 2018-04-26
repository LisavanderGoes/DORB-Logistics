package com.lisa.dorb.repository;

import com.lisa.dorb.model.db.users.Chauffeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    @Query(value = "SELECT * FROM chauffeurs", nativeQuery = true)
    List<Chauffeur> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE chauffeurs SET rijbewijs = :rijbewijs WHERE user_Id = :id", nativeQuery = true)
    void updateRijbewijs(@Param("rijbewijs") String rijbewijs, @Param("id") long id);
//
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE chauffeurs SET werkdagen = :werkdagen WHERE user_Id = :id", nativeQuery = true)
    void updateWerkdagen(@Param("werkdagen") long werkdagen, @Param("id") long id);
//
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM chauffeurs WHERE user_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);
//
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO chauffeurs (user_Id, rijbewijs, werkdagen)" +
            "VALUES (:id, :rijbewijs, :werkdagen);", nativeQuery = true)
    void addRow(@Param("id") long id, @Param("rijbewijs") String rijbewijs, @Param("werkdagen") long werkdagen);

    @Query(value = "SELECT user_Id FROM chauffeurs ORDER BY user_Id DESC LIMIT 1", nativeQuery = true)
    long getId();

    @Query(value = "SELECT rijbewijs FROM chauffeurs WHERE id = :id", nativeQuery = true)
    String getRijbewijsById(@Param("id") long id);

    @Query(value = "SELECT * FROM chauffeurs WHERE rijbewijs = :rijbewijs", nativeQuery = true)
    List<Chauffeur> getAllByRijbewijs(@Param("rijbewijs") String rijbewijs);

    @Query(value = "SELECT * FROM chauffeurs WHERE id = :id", nativeQuery = true)
    Chauffeur findAllById(@Param("id") long id);

    @Query(value = "SELECT * FROM chauffeurs WHERE user_Id = :id", nativeQuery = true)
    Chauffeur findAllByUser_Id(@Param("id") long id);

    @Query(value = "SELECT Id FROM chauffeurs WHERE user_Id =:id", nativeQuery = true)
    long getIdByUser_Id(@Param("id") long id);

    @Query(value = "SELECT user_Id FROM chauffeurs WHERE id = :id", nativeQuery = true)
    long findUser_IdAllById(@Param("id") long id);
}
