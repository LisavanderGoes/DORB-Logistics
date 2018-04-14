package com.lisa.dorb.repository;

import com.lisa.dorb.model.Vrachtwagen;
import com.lisa.dorb.model.VrachtwagenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface VrachtwagenTypeRepository extends JpaRepository<VrachtwagenType, Long> {

    @Query(value = "SELECT * FROM typevrachtwagens", nativeQuery = true)
    List<VrachtwagenType> findAll();

    @Query(value = "SELECT ruimte FROM typevrachtwagens WHERE typ_Id = :id", nativeQuery = true)
    long getRuimteById(@Param("id") long id);

    @Query(value = "SELECT grootst FROM typevrachtwagens WHERE typ_Id = :id", nativeQuery = true)
    String getGrootstById(@Param("id") long id);

    @Query(value = "SELECT volgorde FROM typevrachtwagens WHERE typ_Id = :id", nativeQuery = true)
    long getVolgordeById(@Param("id") long id);

    @Query(value = "SELECT rijbewijs FROM typevrachtwagens WHERE typ_Id = :id", nativeQuery = true)
    String getRijbewijsById(@Param("id") long id);

    @Query(value = "SELECT typ_Id FROM typevrachtwagens WHERE volgorde = :volgorde", nativeQuery = true)
    long getIdByVolgorde(@Param("volgorde") long volgorde);


}
