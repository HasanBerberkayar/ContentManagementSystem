package com.HasanBerberkayar.contentManagementSystem.Configs;

import com.HasanBerberkayar.contentManagementSystem.Services.Imp.IImdbImportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final IImdbImportService imdbImportService;

    public DataLoader(IImdbImportService imdbImportService) {
        this.imdbImportService = imdbImportService;
    }

    @Override
    public void run(String... args) {
        List<String> top20Titles = List.of(
                "The Shawshank Redemption", "The Godfather", "The Dark Knight",
                "The Godfather Part II", "12 Angry Men", "Schindler's List",
                "The Lord of the Rings: The Return of the King", "Pulp Fiction",
                "The Good, the Bad and the Ugly", "Fight Club",
                "Forrest Gump", "Inception", "The Matrix", "Goodfellas",
                "Se7en", "The Silence of the Lambs", "City of God",
                "Saving Private Ryan", "Interstellar", "Spirited Away"
        );

        imdbImportService.importTopMovies(top20Titles);
    }
}