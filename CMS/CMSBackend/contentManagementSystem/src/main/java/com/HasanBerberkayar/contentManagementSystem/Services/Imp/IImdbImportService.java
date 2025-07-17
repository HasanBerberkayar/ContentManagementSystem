package com.HasanBerberkayar.contentManagementSystem.Services.Imp;

import com.HasanBerberkayar.contentManagementSystem.Configs.Roles;
import com.HasanBerberkayar.contentManagementSystem.DTO.OMDbMovieResponse;
import com.HasanBerberkayar.contentManagementSystem.Entities.Casts;
import com.HasanBerberkayar.contentManagementSystem.Entities.Content;
import com.HasanBerberkayar.contentManagementSystem.Entities.Metadata;
import com.HasanBerberkayar.contentManagementSystem.Repositories.CastRepository;
import com.HasanBerberkayar.contentManagementSystem.Repositories.ContentRepository;
import com.HasanBerberkayar.contentManagementSystem.Repositories.MetadataRepository;
import com.HasanBerberkayar.contentManagementSystem.Services.ImdbImportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class IImdbImportService implements ImdbImportService {

    @Value("${imdbUrl}")
    String imdbUrl;

    @Value("${omdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ContentRepository contentRepository;
    private final MetadataRepository metadataRepository;
    private final CastRepository castsRepository;

    public IImdbImportService(ContentRepository contentRepository, MetadataRepository metadataRepository, CastRepository castsRepository) {
        this.contentRepository = contentRepository;
        this.metadataRepository = metadataRepository;
        this.castsRepository = castsRepository;
    }
    public void importTopMovies(List<String> imdbMovieTitles) {
        for (String title : imdbMovieTitles) {
            importMovie(title);
        }
    }

    public String defaultIfNull(String value) {
        return (value == null || value.isBlank()) ? "No Information Found" : value;
    }


    public void  importMovie(String title){
        boolean isContentExist = false;
        for(Content content : contentRepository.findAll()){
            if(Objects.equals(title, content.getMetadata().getTitle())){
                isContentExist = true;
                System.out.println("Content already exist loading passed");
            }
        }
        if(!isContentExist) {
            try {
                String url = imdbUrl + "=" + apiKey + "&t=" + title;

                ResponseEntity<OMDbMovieResponse> response = restTemplate.getForEntity(url, OMDbMovieResponse.class);
                if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                    System.out.println("⚠️ Couldn't retrieve valid data from API: " + title);
                }
                OMDbMovieResponse movie = response.getBody();

                String movieTitle = defaultIfNull(movie.getTitle());
                String plot = defaultIfNull(movie.getPlot());
                String poster = defaultIfNull(movie.getPoster());
                String language = defaultIfNull(movie.getLanguage());
                String country = defaultIfNull(movie.getCountry());
                String directorName = defaultIfNull(movie.getDirector());
                String actorList = defaultIfNull(movie.getActors());

                int year = 0;
                try {
                    if (movie.getYear() != null && movie.getYear().length() >= 4) {
                        year = Integer.parseInt(movie.getYear().substring(0, 4));
                    }
                } catch (Exception e) {
                    System.out.println("Year couldn't assigned: " + movie.getYear() + " -> " + title);
                }

                Metadata metadata = new Metadata(
                        movieTitle,
                        plot,
                        poster,
                        year,
                        language,
                        country
                );
                metadataRepository.save(metadata);
                Casts director = null;
                for (Casts existingDirector : castsRepository.findAll()) {
                    if (Objects.equals(existingDirector.getName(), directorName)
                            && existingDirector.getRole() == Roles.director) {
                        director = existingDirector;
                        break;
                    }
                }
                if (director == null) {
                    director = new Casts(directorName, "", Roles.director);
                    director = castsRepository.save(director);
                }

                List<Casts> actors = new ArrayList<>();
                for (String actorName : actorList.split(",")) {
                    actorName = actorName.trim();
                    Casts actor = null;

                    for (Casts existingActor : castsRepository.findAll()) {
                        if (Objects.equals(existingActor.getName(), actorName)
                                && existingActor.getRole() == Roles.actor) {
                            actor = existingActor;
                            break;
                        }
                    }
                    if (actor == null) {
                        actor = new Casts(actorName, "", Roles.actor);
                        actor = castsRepository.save(actor);
                    }
                    actors.add(actor);
                }

                Content newContent = new Content(metadata, director, actors);
                contentRepository.save(newContent);

                System.out.println("Added: " + movieTitle);

            } catch (Exception e) {
                System.out.println("Error: " + title);
                e.printStackTrace();
            }
        }
    }
}