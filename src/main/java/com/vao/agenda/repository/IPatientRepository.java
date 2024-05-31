package com.vao.agenda.repository;

import com.vao.agenda.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends CrudRepository<Patient, Long> {
}
