package com.vao.agenda.service;

import com.vao.agenda.dto.TreatmentDTO;
import com.vao.agenda.entity.Treatment;
import com.vao.agenda.exception.ResourceNotFoudException;
import com.vao.agenda.repository.ITreatmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class TreatmentService {

    private ITreatmentRepository iTreatmentRepository;


    public Iterable<Treatment> findAll(){
        return iTreatmentRepository.findAll();
    }

    public Treatment findById(Integer id) {
        return iTreatmentRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoudException());
    }

    public Treatment create(TreatmentDTO treatmentDTO){
        Treatment treatment = new Treatment();

        treatment.setName(treatmentDTO.getName());
        treatment.setDuration(treatmentDTO.getDuration());


        return iTreatmentRepository.save(treatment);
    }

    public Treatment update(Integer id, TreatmentDTO treatmentDTO){
        Treatment treatmentFromDB = findById(id);

        treatmentFromDB.setName(treatmentDTO.getName());
        treatmentFromDB.setDuration(treatmentDTO.getDuration());


        return iTreatmentRepository.save(treatmentFromDB);
    }

    public void delete (Integer id){
        Treatment treatmentFromDB = iTreatmentRepository
                .findById(Long.valueOf(id))
                .orElse(null);

        iTreatmentRepository.delete(treatmentFromDB);
    }

}
