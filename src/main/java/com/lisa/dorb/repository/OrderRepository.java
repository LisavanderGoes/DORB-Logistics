package com.lisa.dorb.repository;

import com.lisa.dorb.model.Admin;
import com.lisa.dorb.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<Order> findAll();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET klant_Id = :klant_Id WHERE order_Id = :id", nativeQuery = true)
    void updateKlant_Id(@Param("klant_Id") long klant_Id, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET adres = :adres WHERE order_Id = :id", nativeQuery = true)
    void updateAdres(@Param("adres") String adres, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET prijs = :prijs WHERE order_Id = :id", nativeQuery = true)
    void updatePrijs(@Param("prijs") long prijs, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET datum = :datum WHERE order_Id = :id", nativeQuery = true)
    void updateDatum(@Param("datum") Date datum, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET pallet_Id = :pallet_Id WHERE order_Id = :id", nativeQuery = true)
    void updatePallet_Id(@Param("pallet_Id") long pallet_Id, @Param("id") long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET rit_Id = :rit_Id WHERE order_Id = :id", nativeQuery = true)
    void updateRit_Id(@Param("rit_Id") long rit_Id, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM orders WHERE order_Id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO orders (adres, prijs)" +
            "VALUES ('', 0);", nativeQuery = true)
    void addRow();

    @Query(value = "SELECT order_Id FROM orders ORDER BY order_Id DESC LIMIT 1", nativeQuery = true)
    long getId();
}