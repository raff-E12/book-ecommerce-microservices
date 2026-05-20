package com.student.orders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.student.orders.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmail(String email);

    @Query(value = "SELECT * FROM public.utenti WHERE email = :email", nativeQuery = true)
    Optional<UserModel> findByEmailNative(@Param("email") String email);

    @Query(value = "SELECT * FROM public.utenti", nativeQuery = true)
    List<UserModel> findAllNative();

    @Query(value = "SELECT * FROM public.utenti WHERE id = :id", nativeQuery = true)
    Optional<UserModel> findByIdNative(@Param("id") Integer id);

    @Query(value = "SELECT * FROM public.utenti WHERE role = :role", nativeQuery = true)
    List<UserModel> findByRoleNative(@Param("role") String role);

    @Modifying
    @Transactional
    @Query(value = "UPDATE public.utenti SET nome_completo = :nomeCompleto, email = :email, password = :password, role = :role, verified = :verified WHERE id = :id", nativeQuery = true)
    int updateUserNative(@Param("id") Integer id, @Param("nomeCompleto") String nomeCompleto,@Param("email") String email,@Param("password") String password,@Param("role") String role,@Param("verified") Boolean verified);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM public.utenti WHERE id = :id", nativeQuery = true)
    int deleteByIdNative(@Param("id") Integer id);
}
