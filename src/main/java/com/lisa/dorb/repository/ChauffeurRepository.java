package com.lisa.dorb.repository;

import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.model.Klant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChauffeurRepository extends JpaRepository<Chauffeur, Long> {

    @Query(value = "SELECT wachtwoord FROM chauffeurs WHERE inlognaam = ?1", nativeQuery = true)
    String findWachtwoordByInlognaam(String inlognaam);
}
