package com.HasanBerberkayar.contentManagementSystem.Controllers;

import com.HasanBerberkayar.contentManagementSystem.DTO.ContentRequest;
import com.HasanBerberkayar.contentManagementSystem.Services.ContentService;
import com.HasanBerberkayar.contentManagementSystem.Entities.Content;
import com.HasanBerberkayar.contentManagementSystem.Services.ImdbImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    private final ContentService contentService;
    private final ImdbImportService imdbImportService;

    @Autowired
    public ContentController(ContentService contentService, ImdbImportService imdbImportService) {
        this.contentService = contentService;
        this.imdbImportService = imdbImportService;
    }

    @GetMapping
    public List<Content> getContents(){
        return contentService.getContents();
    }

    @PostMapping
    public void addNewContent(@RequestBody ContentRequest request){
        contentService.addNewContent(request);
    }

    @PostMapping(path = "/imdb")
    public  void addNewContentFromIMDB(
            @RequestParam() String title
    ){
        imdbImportService.importMovie(title);
    }

    @DeleteMapping(path = "{contentId}")
    public void deleteContent(@PathVariable("contentId") long id){
        contentService.deleteContent(id);
    }

    @PutMapping(path = "{contentId}")
    public void editContent(
            @PathVariable("contentId") long contentId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String plot,
            @RequestParam(required = false) String poster,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Long directorId,
            @RequestParam(required = false) String actorIds
            ){
        contentService.editContent(contentId, title, plot, poster, year, language, country, directorId, actorIds);
    }
}
