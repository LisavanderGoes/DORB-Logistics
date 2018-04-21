package com.lisa.dorb.repository;

import com.lisa.dorb.model.db.Natio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NatioRepository extends JpaRepository<Natio, Long> {

    @Query(value = "SELECT * FROM nationaliteit WHERE chauffeur_Id = :chauffeur_Id", nativeQuery = true)
    List<Natio> getAllByChauffeur_Id(@Param("chauffeur_Id") long chauffeur_Id);

}
