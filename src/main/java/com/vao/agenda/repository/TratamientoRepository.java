package com.vao.agenda.repository;

import com.vao.agenda.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    Tratamiento findByNombre(String nombre);
}
