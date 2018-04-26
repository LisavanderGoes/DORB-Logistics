package com.lisa.dorb.repository;

import com.lisa.dorb.model.db.Landen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LandenRepository extends JpaRepository<Landen, Long> {

    @Query(value = "SELECT land_Id FROM landen WHERE land = :landen", nativeQuery = true)
    long getLand_IdByLanden(@Param("landen") String landen);

    @Query(value = "SELECT land FROM landen WHERE land_Id = :id", nativeQuery = true)
    String getLandenByLand_Id(@Param("id") long id);
}
