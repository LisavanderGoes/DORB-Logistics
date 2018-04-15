package com.lisa.dorb.repository;

import com.lisa.dorb.model.Rit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface RitRepository extends JpaRepository<Rit, Long> {

    @Query(value = "SELECT * FROM rit", nativeQuery = true)
    List<Rit> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE rit SET ruimte = :ruimte WHERE rit_Id = :id", nativeQuery = true)
    void updateRuimte(@Param("ruimte") long ruimte, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE rit SET datum = :datum WHERE rit_Id = :id", nativeQuery = true)
    void updateDatum(@Param("datum") Date datum, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE rit SET vrachtwagen_Id = :vrachtwagen_Id WHERE rit_Id = :id", nativeQuery = true)
    void updateVrachtwagen_Id(@Param("vrachtwagen_Id") long vrachtwagen_Id, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE rit SET chauffeur_Id = :chauffeur_Id WHERE rit_Id = :id", nativeQuery = true)
    void updateChauffeur_Id(@Param("chauffeur_Id") long chauffeur_Id, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rit WHERE rit_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO rit (ruimte, vrachtwagen_Id, chauffeur_Id)" +
            "VALUES (0, 0, 0);", nativeQuery = true)
    void addRow();

    @Query(value = "SELECT rit_Id FROM rit ORDER BY rit_Id DESC LIMIT 1", nativeQuery = true)
    long getId();

    @Query(value = "SELECT * FROM rit WHERE datum = :datum", nativeQuery = true) //als je in een list inlaad moet je alles selecten of andere list maken
    List<Rit> getByDatum(@Param("datum") Date datum);

}
