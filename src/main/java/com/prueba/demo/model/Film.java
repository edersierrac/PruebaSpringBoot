package com.prueba.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Film {
   @Id
    private String episode_id;
    private String title;
    private String release_date;
    // Otros campos de la película
}
