package com.lisa.dorb.repository;

import com.lisa.dorb.model.Chauffeur;
import com.lisa.dorb.model.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {

    @Query(value = "SELECT wachtwoord FROM planners WHERE inlognaam = ?1", nativeQuery = true)
    String findWachtwoordByInlognaam(String inlognaam);
}
