package com.HasanBerberkayar.contentManagementSystem.Services;

import java.util.List;

public interface ImdbImportService {

    void importTopMovies(List<String> imdbMovieTitles);
    String defaultIfNull(String value);
    void importMovie(String title);
}
