package com.HasanBerberkayar.contentManagementSystem.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OMDbMovieResponse {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Plot")
    private String plot;

    @JsonProperty("Poster")
    private String poster;

    @JsonProperty("Year")
    private String year;

    @JsonProperty("Language")
    private String language;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Director")
    private String director;

    @JsonProperty("Actors")
    private String actors;

    public String getTitle() {
        return title;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getYear() {
        return year;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }
}
