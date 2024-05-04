package com.vao.agenda.service;

import com.vao.agenda.entity.Patient;
import com.vao.agenda.repository.IPatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private IPatientRepository iPatientRepository;

    public Iterable<Patient> findAll(){
        return iPatientRepository.findAll();
    }

    public Patient findById(Integer id) {
        return iPatientRepository
                .findById(id)
                .orElse(null);
    }

    public Patient create(Patient patient){
        return iPatientRepository.save(patient);
    }

    public Patient update(Integer id, Patient formP){
        Patient patientFromDB = findById(id);

        patientFromDB.setName(formP.getName());
        patientFromDB.setLastName(formP.getLastName());
        patientFromDB.setPhone(formP.getPhone());

        return iPatientRepository.save(patientFromDB);
    }

    public void delete (Integer id){
        Patient patientfronDB = iPatientRepository
                .findById(id)
                .orElse(null);

        iPatientRepository.delete(patientfronDB);
    }

}
