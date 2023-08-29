package com.example.myapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.myapp.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query("FROM Users u WHERE u.email = :email")
    public List<Users> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u")
    public List<Users> findAllUsers();
}