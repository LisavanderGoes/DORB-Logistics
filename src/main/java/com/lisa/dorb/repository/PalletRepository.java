package com.lisa.dorb.repository;

import com.lisa.dorb.model.DB.Pallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PalletRepository extends JpaRepository<Pallet, Long> {

    @Query(value = "SELECT * FROM pallets", nativeQuery = true)
    List<Pallet> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE pallets SET wat = :wat WHERE pallet_Id = :id", nativeQuery = true)
    void updateWat(@Param("wat") String wat, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE pallets SET order_Id = :order_Id WHERE pallet_Id = :id", nativeQuery = true)
    void updateOrder_Id(@Param("order_Id") long order_Id, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pallets WHERE pallet_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO pallets (wat, order_Id, aantal)" +
            "VALUES (:wat, :order_Id, :aantal);", nativeQuery = true)
    void addRow(@Param("wat") String wat, @Param("order_Id") long order_Id, @Param("aantal") long aantal);

    @Query(value = "SELECT pallet_Id FROM pallets ORDER BY pallet_Id DESC LIMIT 1", nativeQuery = true)
    long getId();

}
