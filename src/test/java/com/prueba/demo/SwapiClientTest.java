package com.prueba.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.prueba.demo.model.Film;
import com.prueba.demo.repository.FilmRepository;
import com.prueba.demo.service.SwapiClient;

public class SwapiClientTest {
    @Test
    public void testGetFilmById() {
        
        RestTemplate restTemplate = mock(RestTemplate.class);
       
        FilmRepository filmRepository = mock(FilmRepository.class);

        SwapiClient swapiClient = new SwapiClient(restTemplate, filmRepository);

        String id = "1";
        String url = "https://api.example.com/films/1/";
        Film expectedFilm = new Film();
       
        expectedFilm.setTitle("Film title");
        expectedFilm.setRelease_date("2024-04-14");

        when(restTemplate.getForObject(url, Film.class)).thenReturn(expectedFilm);
        
        ResponseEntity<?> actualResponse = swapiClient.getFilmById(id);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        
    }

    @Test
    public void testUpdateFilById() {
        
        FilmRepository filmRepository = mock(FilmRepository.class);

        SwapiClient swapiClient = new SwapiClient(null, filmRepository);

        String id = "1";
        Optional<Film> optionalFilm = Optional.of(new Film());
        
        optionalFilm.get().setTitle("Film title");
        optionalFilm.get().setRelease_date("2024-04-14");

        when(filmRepository.findById(id)).thenReturn(optionalFilm);
        
        Film updatedFilm = new Film();
        updatedFilm.setTitle("Updated Film title");
        updatedFilm.setRelease_date("2024-04-15");
        ResponseEntity<?> actualResponse = swapiClient.updateFilById(id, updatedFilm);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        verify(filmRepository).save(optionalFilm.get());
    }

    @Test
    public void testDeleteFilById() {
       
        FilmRepository filmRepository = mock(FilmRepository.class);

        SwapiClient swapiClient = new SwapiClient(null, filmRepository);

        String id = "1";
        Optional<Film> optionalFilm = Optional.of(new Film());
        
        when(filmRepository.findById(id)).thenReturn(optionalFilm);

        ResponseEntity<?> actualResponse = swapiClient.deleteFilById(id);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

        verify(filmRepository).deleteById(id);
    }

    @Test
    public void testDeleteFilByIdFilmNotFound() {
        
        FilmRepository filmRepository = mock(FilmRepository.class);

        SwapiClient swapiClient = new SwapiClient(null, filmRepository);

        String id = "1";

        Optional<Film> optionalFilm = Optional.empty();
        
        when(filmRepository.findById(id)).thenReturn(optionalFilm);

        ResponseEntity<?> actualResponse = swapiClient.deleteFilById(id);

        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

    @Test
    public void testDeleteFilByIdError() {
    
      FilmRepository filmRepository = mock(FilmRepository.class);
    
      SwapiClient swapiClient = new SwapiClient(null, filmRepository);

      String id = "1";

      when(filmRepository.findById(id)).thenReturn(Optional.of(new Film()));

      doThrow(new RuntimeException("Error al eliminar Film")).when(filmRepository).deleteById(id);

      ResponseEntity<?> actualResponse = swapiClient.deleteFilById(id);

       assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }


    @Test
    public void testUpdateFilByIdFilmNotFound() {
        
        FilmRepository filmRepository = mock(FilmRepository.class);

        SwapiClient swapiClient = new SwapiClient(null, filmRepository);

        String id = "1";

        Optional<Film> optionalFilm = Optional.empty();
        
        Film updatedFilm = new Film();
        updatedFilm.setTitle("Updated Film title");
        updatedFilm.setRelease_date("2024-04-15");
        when(filmRepository.findById(id)).thenReturn(optionalFilm);

        ResponseEntity<?> actualResponse = swapiClient.updateFilById(id,updatedFilm );

        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
    }

   @Test
    public void testUpdateFilByIdError() {
    
      FilmRepository filmRepository = mock(FilmRepository.class);

      SwapiClient swapiClient = new SwapiClient(null, filmRepository);

    
      String id = "1";

  
      when(filmRepository.findById(id)).thenReturn(Optional.of(new Film()));

     
      doThrow(new RuntimeException("Error al actualizar Film")).when(filmRepository).save(any(Film.class));

  
      ResponseEntity<?> actualResponse = swapiClient.updateFilById(id, new Film());

    
      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualResponse.getStatusCode());
    }

    @Test
    public void testGetFilmByIdInvalidId() {
    
      FilmRepository filmRepository = mock(FilmRepository.class);
    
      SwapiClient swapiClient = new SwapiClient(null, filmRepository);

      String invalidId = "abc"; 

      ResponseEntity<?> actualResponse = swapiClient.getFilmById(invalidId);

      assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
    }

    @Test
    public void testGetFilmByIdHttpClientErrorException() {
  
      RestTemplate restTemplate = mock(RestTemplate.class);

      FilmRepository filmRepository = mock(FilmRepository.class);

      SwapiClient swapiClient = new SwapiClient(restTemplate, filmRepository);

      String id = "1";

      HttpStatus errorStatus = HttpStatus.INTERNAL_SERVER_ERROR;

      doThrow(new HttpClientErrorException(errorStatus)).when(restTemplate).getForObject(anyString(), eq(Film.class));

      ResponseEntity<?> actualResponse = swapiClient.getFilmById(id);

      assertEquals(errorStatus, actualResponse.getStatusCode());
    }
}
