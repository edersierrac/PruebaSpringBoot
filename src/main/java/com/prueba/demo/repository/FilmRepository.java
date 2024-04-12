package com.prueba.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prueba.demo.model.Film;




@Repository
public interface FilmRepository extends JpaRepository<Film,String> {
    Optional<Film> findById(String id);
}