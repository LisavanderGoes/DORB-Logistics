package com.lisa.dorb.repository;

import com.lisa.dorb.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query(value = "SELECT * FROM rollen WHERE user_Id= :user_Id", nativeQuery = true)
    List<Rol> getAllByUser_Id(@Param("user_Id") long user_Id);

}
