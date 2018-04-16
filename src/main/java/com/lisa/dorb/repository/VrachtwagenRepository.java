package com.lisa.dorb.repository;

import com.lisa.dorb.model.Order;
import com.lisa.dorb.model.Vrachtwagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface VrachtwagenRepository extends JpaRepository<Vrachtwagen, Long> {

    @Query(value = "SELECT * FROM vrachtwagens", nativeQuery = true)
    List<Vrachtwagen> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE vrachtwagens SET kenteken = :kenteken WHERE vrachtwagen_Id = :id", nativeQuery = true)
    void updateKenteken(@Param("kenteken") String kenteken, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE vrachtwagens SET status = :status WHERE vrachtwagen_Id = :id", nativeQuery = true)
    void updateStatus(@Param("status") String status, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE vrachtwagens SET typ_Id = :typ_Id WHERE vrachtwagen_Id = :id", nativeQuery = true)
    void updateTyp_Id(@Param("typ_Id") long typ_Id, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE vrachtwagens SET apk = :apk WHERE vrachtwagen_Id = :id", nativeQuery = true)
    void updateApk(@Param("apk") Date apk, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM vrachtwagens WHERE vrachtwagen_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO vrachtwagens (kenteken, apk, status)" +
            "VALUES ('', '', 'beschikbaar');", nativeQuery = true)
    void addRow();

    @Query(value = "SELECT vrachtwagen_Id FROM vrachtwagens ORDER BY vrachtwagen_Id DESC LIMIT 1", nativeQuery = true)
    long getId();

    @Query(value = "SELECT typ_Id FROM vrachtwagens WHERE vrachtwagen_Id =:id", nativeQuery = true)
    long getTyp_IdById(@Param("id") long id);

    @Query(value = "SELECT * FROM vrachtwagens WHERE typ_Id =:typ_Id AND apk !=:datum AND status =:status", nativeQuery = true)
    List<Vrachtwagen> getIdByTyp_IdAndBeschikbaarheidAndApk(@Param("typ_Id") long typ_Id, @Param("datum") Date datum, @Param("status") String status);

}
