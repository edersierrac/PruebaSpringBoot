package com.prueba.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.demo.model.Film;
import com.prueba.demo.service.SwapiClient;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private SwapiClient swapiClient;

     /**
     * Esta funcion (getFilmById) se encarga de mostrar y guardar en la BD el Film encontrado por medio del id
     * 
     * @return devuelve un ´ResponseEntity´ que contiene la informacion del film
     *         o en su defecto el mensaje de error que puedan surgir
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getFilmById(@PathVariable String id) {
        return swapiClient.getFilmById(id);
    }

     /**
     * Esta funcion (updateFilm) se encarga de actualizar en la BD MySQL el Film encontrado por medio del episode_id
     * 
     * @return devuelve un ´ResponseEntity´ que contiene el mensaje de actualizacion 
     *         o en su defecto el mensaje de error que puedan surgir
     */

    @PutMapping("/{id}")
     public ResponseEntity<?> updateFilm(@PathVariable String id,  @RequestBody Film updatedFilm) {
        return swapiClient.updateFilById(id,updatedFilm);
       
    }

    /**
     * Esta funcion (deleteFilm) se encarga de eliminar en la BD MySQL el Film encontrado por medio del episode_id
     * 
     * @return devuelve un ´ResponseEntity´ que contiene el mensaje de eliminacion
     *         o en su defecto el mensaje de error que puedan surgir
     */
    @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteFilm(@PathVariable String id  ) {
        return swapiClient.deleteFilById(id);
       
     }
}
