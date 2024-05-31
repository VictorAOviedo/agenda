package com.vao.agenda.repository;

import com.vao.agenda.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    Local findByNombre(String nombre);
}
