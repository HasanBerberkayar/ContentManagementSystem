package com.HasanBerberkayar.contentManagementSystem.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String plot;
    private String poster;
    private int year;
    private String language;
    private String country;

    public Metadata() {
    }

    public Metadata(String title, String plot, String poster, int year, String language, String country) {
        this.title = title;
        this.plot = plot;
        this.poster = poster;
        this.year = year;
        this.language = language;
        this.country = country;
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

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", year=" + year +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}