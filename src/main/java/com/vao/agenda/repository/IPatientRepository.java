package com.vao.agenda.repository;

import com.vao.agenda.entity.Patient;
import org.springframework.data.repository.CrudRepository;

public interface IPatientRepository extends CrudRepository<Patient, Integer> {
}
