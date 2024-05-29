package com.vao.agenda.service;

import com.vao.agenda.dto.ReservaDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.entity.Reserva;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.repository.LocalRepository;
import com.vao.agenda.repository.ReservaRepository;
import com.vao.agenda.repository.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private ReservaRepository reservaRepository;


    public List<Tratamiento> getTratamientos() {

        return tratamientoRepository.findAll();
    }

    public List<Local> getLocales() {
        return localRepository.findAll();
    }


    public Iterable<Reserva> findAll(){
        return reservaRepository.findAll();
    }


    public ReservaDTO getHorarios(String localName, String tratamientoName) {
        Tratamiento tratamientoSeleccionado = tratamientoRepository.findAll().stream()
                .filter(t -> t.getNombre().equals(tratamientoName))
                .findFirst().orElse(null);

        Local localSeleccionado = localRepository.findAll().stream()
                .filter(l -> l.getNombre().equals(localName))
                .findFirst().orElse(null);

        if (tratamientoSeleccionado == null || localSeleccionado == null) {
            return new ReservaDTO(localName, tratamientoName, Collections.emptyList());
        }

        List<DayOfWeek> diasDisponibles = localSeleccionado.getDiasDisponibles();
        List<String> horariosDisponibles = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        int duracion = tratamientoSeleccionado.getDuracion(); // Duración en minutos

        LocalDate currentDate = LocalDate.now().plusDays(1); // Empezar desde mañana
        LocalDate endDate = LocalDate.now().plusMonths(3); // Hasta tres meses desde hoy

        while (!currentDate.isAfter(endDate)) {
            if (diasDisponibles.contains(currentDate.getDayOfWeek())) {
                LocalDateTime startTime = currentDate.atTime(9, 0);
                LocalDateTime endTime = currentDate.atTime(19, 0);

                while (startTime.plusMinutes(duracion).isBefore(endTime)) {
                    horariosDisponibles.add(startTime.format(dateTimeFormatter));
                    startTime = startTime.plusMinutes(duracion);
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return new ReservaDTO(localName, tratamientoName, horariosDisponibles);
    }


    private boolean isDateTimeValidForLocal(Local local, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return local.getDiasDisponibles().contains(dayOfWeek);
    }

    @Transactional
    public Reserva create(String localName, String tratamientoName, String fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(fechaHora, formatter);

        Local local = localRepository.findAll().stream()
                .filter(l -> l.getNombre().equals(localName))
                .findFirst().orElseThrow(() -> new RuntimeException("Local no encontrado"));

        Tratamiento tratamiento = tratamientoRepository.findAll().stream()
                .filter(t -> t.getNombre().equals(tratamientoName))
                .findFirst().orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));

        if (!isDateTimeValidForLocal(local, dateTime)) {
            throw new RuntimeException("La fecha y hora no son válidas para los días disponibles del local");
        }

        Reserva reserva = new Reserva();
        reserva.setLocal(localName);
        reserva.setTratamiento(tratamientoName);
        reserva.setFechaHora(dateTime);

        return reservaRepository.save(reserva);
    }


    @Transactional
    public Reserva update(Long id, String localName, String tratamientoName, String fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(fechaHora, formatter);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Local local = localRepository.findAll().stream()
                .filter(l -> l.getNombre().equals(localName))
                .findFirst().orElseThrow(() -> new RuntimeException("Local no encontrado"));

        Tratamiento tratamiento = tratamientoRepository.findAll().stream()
                .filter(t -> t.getNombre().equals(tratamientoName))
                .findFirst().orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));

        if (!isDateTimeValidForLocal(local, dateTime)) {
            throw new RuntimeException("La fecha y hora no son válidas para los días disponibles del local");
        }

        reserva.setLocal(localName);
        reserva.setTratamiento(tratamientoName);
        reserva.setFechaHora(dateTime);

        return reservaRepository.save(reserva);
    }


    public void delete (Integer id){
        Reserva reservafromDB = reservaRepository
                .findById(Long.valueOf(id))
                .orElse(null);

        reservaRepository.delete(reservafromDB);
    }

}
