package com.HasanBerberkayar.contentManagementSystem.Services.Imp;

import com.HasanBerberkayar.contentManagementSystem.DTO.ContentRequest;
import com.HasanBerberkayar.contentManagementSystem.Entities.Casts;
import com.HasanBerberkayar.contentManagementSystem.Entities.Content;
import com.HasanBerberkayar.contentManagementSystem.Entities.Metadata;
import com.HasanBerberkayar.contentManagementSystem.Repositories.CastRepository;
import com.HasanBerberkayar.contentManagementSystem.Repositories.ContentRepository;
import com.HasanBerberkayar.contentManagementSystem.Repositories.MetadataRepository;
import com.HasanBerberkayar.contentManagementSystem.Services.ContentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IContentService implements ContentService {

    private final ContentRepository contentRepository;
    private final MetadataRepository metadataRepository;
    private final CastRepository castRepository;


    @Autowired
    public IContentService(ContentRepository contentRepository, MetadataRepository metadataRepository, CastRepository castRepository) {
        this.contentRepository = contentRepository;
        this.metadataRepository = metadataRepository;
        this.castRepository = castRepository;

    }

    public List<Content> getContents(){
        return contentRepository.findAll();
    }

    public void addNewContent(ContentRequest request){
        Metadata metadata = request.getMetadata();
        metadataRepository.save(metadata);
        Casts director = castRepository.findById(request.getDirectorId())
                .orElseThrow(() -> new RuntimeException("Director not found"));

        List<Casts> actors = castRepository.findAllById(request.getActorIds());
        Content content = new Content(metadata, director, actors);
        contentRepository.save(content);
    }

    public void deleteContent(long id){
        boolean isContentExist = contentRepository.existsById(id);
        if(isContentExist){
            long metadataId = contentRepository.findById(id).orElseThrow(
                    () -> new IllegalStateException("There is no Content with that id"))
                    .getMetadata().getId();
            contentRepository.deleteById(id);
            metadataRepository.deleteById(metadataId);
        }
        else{
            throw new IllegalStateException("There is no Content with that id");
        }
    }

    @Transactional
    public void editContent(long contentId, String title, String plot, String poster, Integer year, String language, String country, Long directorId, String castIds){
        Metadata metadata = contentRepository.findById(contentId).orElseThrow(
                () -> new IllegalStateException("There is no Content with that id"))
                .getMetadata();

        if(title != null){
            metadata.setTitle(title);
        }
        if(plot != null){
            metadata.setPlot(plot);
        }
        if (poster != null){
            metadata.setPoster(poster);
        }
        if (year != null){
            metadata.setYear(year);
        }
        if (language != null){
            metadata.setLanguage(language);
        }
        if (country != null){
            metadata.setCountry(country);
        }
        if (directorId != null){
            contentRepository.findById(contentId).orElseThrow(() ->
                    new IllegalStateException("Content not found")).setDirector(
                            castRepository.findById(directorId).orElseThrow(()->
                                    new IllegalStateException("Not valid director")));
        }
        if (castIds != null){
            List<Long> ids = Arrays.stream(castIds.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Casts> casts = new ArrayList<>();
            for(Long id : ids){
                casts.add(castRepository.findById(id).orElseThrow(()->
                        new IllegalStateException("Not valid director")));
            }
            contentRepository.findById(contentId).orElseThrow(() ->
                    new IllegalStateException("Content not found")).setActors(casts);
        }
    }
}

