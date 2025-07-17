package com.HasanBerberkayar.contentManagementSystem.Services;

import com.HasanBerberkayar.contentManagementSystem.DTO.ContentRequest;
import com.HasanBerberkayar.contentManagementSystem.Entities.Content;

import java.util.List;

public interface ContentService {

    List<Content> getContents();
    void addNewContent(ContentRequest request);
    void deleteContent(long id);
    void editContent(long contentId, String title, String plot, String poster, Integer year, String language, String country, Long directorId, String castIds);
}
