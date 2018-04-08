package com.lisa.dorb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lisa.dorb.model.Klant;

import javax.naming.NamingException;

public interface KlantRepository extends JpaRepository<Klant, Long> {

    @Query(value = "SELECT wachtwoord FROM klanten WHERE inlognaam = ?1", nativeQuery = true)
    String findWachtwoordByInlognaam(String inlognaam);
}
