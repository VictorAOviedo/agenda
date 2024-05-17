package com.vao.agenda.service;

import com.vao.agenda.dto.PatientDTO;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.repository.IPatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;




@AllArgsConstructor
@Service
public class PatientService {

    private final IPatientRepository iPatientRepository;

    public Iterable<Patient> findAll(){
        return iPatientRepository.findAll();
    }

    public Patient findById(Integer id) {
        return iPatientRepository
                .findById(id)
                .orElse(null);
    }

    public Patient create(PatientDTO patientDTO){
        Patient patient = new Patient();

        patient.setName(patientDTO.getName());
        patient.setLastName(patientDTO.getLastName());
        patient.setCod(patientDTO.getCod());
        patient.setPhone(patientDTO.getPhone());
        patient.setEmail(patientDTO.getEmail());

        return iPatientRepository.save(patient);
    }

    public Patient update(Integer id, PatientDTO patientDTO){
        Patient patientFromDB = findById(id);

        patientFromDB.setName(patientDTO.getName());
        patientFromDB.setLastName(patientDTO.getLastName());
        patientFromDB.setCod(patientDTO.getCod());
        patientFromDB.setPhone(patientDTO.getPhone());
        patientFromDB.setEmail(patientDTO.getEmail());

        return iPatientRepository.save(patientFromDB);
    }

    public void delete (Integer id){
        Patient patientfromDB = iPatientRepository
                .findById(id)
                .orElse(null);

        iPatientRepository.delete(patientfromDB);
    }

}
