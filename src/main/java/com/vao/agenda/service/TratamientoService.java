package com.vao.agenda.service;

import com.vao.agenda.dto.TratamientoDTO;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.exception.ResourceNotFoudException;
import com.vao.agenda.repository.TratamientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class TratamientoService {

    private  TratamientoRepository tratamientoRepository;


    public Iterable<Tratamiento> findAll(){
        return tratamientoRepository.findAll();
    }

    public Tratamiento findById(Integer id) {
        return tratamientoRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoudException());
    }

    public Tratamiento create(TratamientoDTO tratamientoDTO){
        Tratamiento tratamiento = new Tratamiento();

        tratamiento.setNombre(tratamientoDTO.getNombre());
        tratamiento.setDuracion(tratamientoDTO.getDuracion());


        return tratamientoRepository.save(tratamiento);
    }

    public Tratamiento update(Integer id, TratamientoDTO tratamientoDTO){
        Tratamiento tratamientoFromDB = findById(id);

        tratamientoFromDB.setNombre(tratamientoDTO.getNombre());
        tratamientoFromDB.setDuracion(tratamientoDTO.getDuracion());


        return tratamientoRepository.save(tratamientoFromDB);
    }

    public void delete (Integer id){
        Tratamiento tratamientofromDB = tratamientoRepository
                .findById(Long.valueOf(id))
                .orElse(null);

        tratamientoRepository.delete(tratamientofromDB);
    }

}
