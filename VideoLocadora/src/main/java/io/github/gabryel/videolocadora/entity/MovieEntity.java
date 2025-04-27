package io.github.gabryel.videolocadora.entity;

import io.github.gabryel.videolocadora.enums.MediaGenre;
import io.github.gabryel.videolocadora.enums.MediaType;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "movie")
public class MovieEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(name = "content_rating", nullable = false)
    private String contentRating;
    
    @Column(name = "internal_code", nullable = false, unique = true)
    private String internalCode;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(nullable = false)
    private boolean available;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType type;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaGenre genre;

    public MovieEntity() {
    }

    public MovieEntity(Long id, String title, String contentRating, String internalCode, Double price, Boolean available, MediaType type, MediaGenre genre) {
        this.id = id;
        this.title = title;
        this.contentRating = contentRating;
        this.internalCode = internalCode;
        this.price = price;
        this.available = available;
        this.type = type;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public MediaGenre getGenre() {
        return genre;
    }

    public void setGenre(MediaGenre genre) {
        this.genre = genre;
    }
}
