package com.lisa.dorb.repository;

import com.lisa.dorb.model.db.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query(value = "SELECT * FROM rollen WHERE user_Id= :user_Id", nativeQuery = true)
    List<Rol> getAllByUser_Id(@Param("user_Id") long user_Id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE rollen SET rol = :rol WHERE user_Id = :id", nativeQuery = true)
    void updateRol(@Param("rol") String rol, @Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rollen WHERE id =:id", nativeQuery = true)
    void deleteRow(@Param("id") long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rollen WHERE user_Id =:id", nativeQuery = true)
    void deleteRowUser_Id(@Param("id") long id);


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO rollen (user_Id, rol)" +
            "VALUES (:id, :rol);", nativeQuery = true)
    void addRow(@Param("id") long id, @Param("rol") String rol);

}
