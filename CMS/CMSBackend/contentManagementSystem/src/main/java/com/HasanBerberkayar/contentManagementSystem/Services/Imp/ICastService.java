package com.HasanBerberkayar.contentManagementSystem.Services.Imp;

import com.HasanBerberkayar.contentManagementSystem.DTO.CastRequest;
import com.HasanBerberkayar.contentManagementSystem.Entities.Casts;
import com.HasanBerberkayar.contentManagementSystem.Repositories.CastRepository;
import com.HasanBerberkayar.contentManagementSystem.Services.CastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ICastService implements CastService {
    private final CastRepository castRepository;

    @Autowired
    public ICastService(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    public List<Casts> getCasts(){
        return castRepository.findAll();
    }

    public Casts addCast(CastRequest request){
        Casts newCasts = new Casts(request.getName(),request.getPoster(),request.getRole());
        return castRepository.save(newCasts);
    }

    public void deleteCast(long id){
        boolean isContentExist = castRepository.existsById(id);
        if(isContentExist){
            castRepository.deleteById(id);
        }
        else{
            throw new IllegalStateException("There is no Content with that id");
        }
    }

}
