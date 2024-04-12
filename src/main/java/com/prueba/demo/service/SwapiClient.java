package com.prueba.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.prueba.demo.model.ErrorResponse;
import com.prueba.demo.model.Film;
import com.prueba.demo.repository.FilmRepository;

@Service
public class SwapiClient {
    @Value("${swapi.baseurl}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final FilmRepository filmRepository;

    public SwapiClient(RestTemplate restTemplate, FilmRepository filmRepository) {
        this.restTemplate = restTemplate;
        this.filmRepository = filmRepository;
    }

    public ResponseEntity<?> getFilmById( String id) {
        
        String regex = "\\d+";// Convertir el ID a String para validar longitud y formato
        if ( !id.matches(regex)||id.length() >= 3) {
            // El ID no es válido o es extenso, devuelve un ResponseEntity con el estado correspondiente
            ErrorResponse errorResponse = new ErrorResponse("Error en la solicitud");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        String url = baseUrl + "films/" + id + "/";
        try {
            Film film = restTemplate.getForObject(url, Film.class);
            // Se guarda la informacion en la BD
            filmRepository.save(film);
            // Retorna la informacion del film
            return ResponseEntity.ok(film);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Si La película no existe,se devuelve un ResponseEntity con el estado correspondiente
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                // Otro error HTTP, devolvemos un ResponseEntity con el estado correspondiente
                return ResponseEntity.status(e.getStatusCode()).build();
            }
        }
    }

    public ResponseEntity<?> updateFilById( String id, Film updatedFilm) {
       

        Optional<Film> optionalFilm =filmRepository.findById(id);
        if (!optionalFilm.isPresent()) {
            // Manejar el caso en que el registro no se encuentre
            ErrorResponse errorResponse = new ErrorResponse("Film no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }


       try {

        // Creacion del objeto tipo Film para actualizar la informacion 
        Film existingFilm = optionalFilm.get();
        existingFilm.setTitle(updatedFilm.getTitle());
        existingFilm.setRelease_date(updatedFilm.getRelease_date());
        existingFilm.setEpisode_id(id);

        filmRepository.save(existingFilm);

        ErrorResponse errorResponse = new ErrorResponse("Film Actualizado correctamente");
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        
       } catch (Exception e) {
        // Mensaje el caso en que el registro tenga un error al actualizar
        ErrorResponse errorResponse = new ErrorResponse("Error al actualizar Film");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
       }
       
       
    }

    public ResponseEntity<?> deleteFilById( String id) {
       

        Optional<Film> optionalFilm =filmRepository.findById(id);
        if (!optionalFilm.isPresent()) {
            // Mensaje el caso en que el registro no se encuentre
            ErrorResponse errorResponse = new ErrorResponse("Film no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }


       try {
        // Eliminacion del registro por medio del episode_id, con su respectivo mensaje
        filmRepository.deleteById(id);

        ErrorResponse errorResponse = new ErrorResponse("Film Eliminado correctamente");
        return ResponseEntity.status(HttpStatus.OK).body(errorResponse);
        
       } catch (Exception e) {
        // Mensaje el caso en que el registro tenga un error al eliminar
        ErrorResponse errorResponse = new ErrorResponse("Error al eliminar Film");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
       }
       
       
    }
}

