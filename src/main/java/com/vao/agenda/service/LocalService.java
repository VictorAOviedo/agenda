package com.vao.agenda.service;

import com.vao.agenda.dto.LocalDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.exception.ResourceNotFoudException;
import com.vao.agenda.repository.LocalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class LocalService {

    private LocalRepository localRepository;

    public Iterable<Local> findAll(){
        return localRepository.findAll();
    }

    public Local findById(Integer id) {
        return localRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoudException());
    }

    public Local create(LocalDTO localDTO){
        Local local = new Local();

        local.setNombre(localDTO.getNombre());
        local.setDiasDisponibles(localDTO.getDiasDisponibles());


        return localRepository.save(local);
    }

    public Local update(Integer id, LocalDTO localDTO){
        Local localFromDB = findById(id);

        localFromDB.setNombre(localDTO.getNombre());
        localFromDB.setDiasDisponibles(localDTO.getDiasDisponibles());


        return localRepository.save(localFromDB);
    }

    public void delete (Integer id){
        Local localFromDB = localRepository
                .findById(Long.valueOf(id))
                .orElse(null);

        localRepository.delete(localFromDB);
    }
}
