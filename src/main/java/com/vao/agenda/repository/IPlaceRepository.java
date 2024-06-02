package com.vao.agenda.repository;

import com.vao.agenda.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlaceRepository extends JpaRepository<Place, Long> {
    Place findByName(String name);
}
