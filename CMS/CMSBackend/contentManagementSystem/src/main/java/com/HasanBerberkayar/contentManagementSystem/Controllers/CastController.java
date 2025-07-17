package com.HasanBerberkayar.contentManagementSystem.Controllers;

import com.HasanBerberkayar.contentManagementSystem.DTO.CastRequest;
import com.HasanBerberkayar.contentManagementSystem.Entities.Casts;
import com.HasanBerberkayar.contentManagementSystem.Services.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "${castUrl}")
public class CastController {
    private final CastService castService;

    @Autowired
    public CastController(CastService castService) {
        this.castService = castService;
    }

    @GetMapping
    public List<Casts> getCasts(){
        return castService.getCasts();
    }

    @PostMapping
    public ResponseEntity<Casts> addCast(@RequestBody CastRequest request) {
        Casts savedCast = castService.addCast(request);
        return ResponseEntity.ok(savedCast);
    }

    @DeleteMapping(path = "{castId}")
    public void deleteContent(@PathVariable("castId") long id){
        castService.deleteCast(id);
    }
}
