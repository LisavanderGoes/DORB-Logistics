package com.lisa.dorb.repository;

import com.lisa.dorb.model.db.Natio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NatioRepository extends JpaRepository<Natio, Long> {

    @Query(value = "SELECT * FROM nationaliteit WHERE chauffeur_Id = :chauffeur_Id", nativeQuery = true)
    List<Natio> getAllByChauffeur_Id(@Param("chauffeur_Id") long chauffeur_Id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO nationaliteit (chauffeur_Id, land_Id)" +
            "VALUES (:chauffeur_Id, :land_Id);", nativeQuery = true)
    void addRow(@Param("chauffeur_Id") long chauffeur_Id, @Param("land_Id") long land_Id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM nationaliteit WHERE chauffeur_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);
}
