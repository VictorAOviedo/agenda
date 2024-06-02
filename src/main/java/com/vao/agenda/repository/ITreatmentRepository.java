package com.vao.agenda.repository;

import com.vao.agenda.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ITreatmentRepository extends JpaRepository<Treatment, Long> {
    Treatment findByName(String name);
}
