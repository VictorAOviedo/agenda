package com.vao.agenda.service;

import com.vao.agenda.dto.PlaceDTO;
import com.vao.agenda.entity.Place;
import com.vao.agenda.exception.NotFoundException;
import com.vao.agenda.exception.ResourceNotFoudException;
import com.vao.agenda.repository.IPlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class PlaceService {

    private IPlaceRepository iPlaceRepository;

    public Iterable<Place> findAll(){
        return iPlaceRepository.findAll();
    }

    public Place findById(Integer id) {
        return iPlaceRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("ID Local no encontrado"));
    }

    public Place create(PlaceDTO placeDTO){
        Place place = new Place();

        place.setName(placeDTO.getName());
        place.setAvailableDays(placeDTO.getAvailableDays());

        return iPlaceRepository.save(place);
    }

    public Place update(Integer id, PlaceDTO placeDTO){
        Place placeFromDB = findById(id);

        placeFromDB.setName(placeDTO.getName());
        placeFromDB.setAvailableDays(placeDTO.getAvailableDays());

        return iPlaceRepository.save(placeFromDB);
    }

    public void delete (Integer id){
        Place placeFromDB = iPlaceRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException("ID Local no encontrado"));

        iPlaceRepository.delete(placeFromDB);
    }
}
