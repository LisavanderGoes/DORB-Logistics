package com.lisa.dorb.repository;

import com.lisa.dorb.model.Prijs;
import com.lisa.dorb.model.Vrachtwagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface PrijsRepository extends JpaRepository<Prijs, Long> {

    @Query(value = "SELECT * FROM prijzen", nativeQuery = true)
    List<Prijs> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE prijzen SET wat = :wat WHERE prijs_Id = :id", nativeQuery = true)
    void updateWat(@Param("wat") String wat, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE prijzen SET prijs = :prijs WHERE prijs_Id = :id", nativeQuery = true)
    void updatePrijs(@Param("prijs") String prijs, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM prijzen WHERE prijs_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO prijzen (wat, prijs)" +
            "VALUES ('', 0);", nativeQuery = true)
    void addRow();

    @Query(value = "SELECT prijs_Id FROM prijzen ORDER BY prijs_Id DESC LIMIT 1", nativeQuery = true)
    long getId();

    @Query(value = "SELECT prijs FROM prijzen WHERE wat = :wat", nativeQuery = true)
    String getPrijsByWat(@Param("wat") String wat);
}
