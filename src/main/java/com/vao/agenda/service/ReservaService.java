package com.vao.agenda.service;

import com.vao.agenda.dto.ReservaDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.entity.Reserva;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.exception.ResourceNotFoudException;
import com.vao.agenda.repository.IPatientRepository;
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

    @Autowired
    private IPatientRepository patientRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");


    public List<Tratamiento> getTratamientos() {

        return tratamientoRepository.findAll();
    }


    public List<Local> getLocales() {
        return localRepository.findAll();
    }


    public Iterable<Reserva> findAll(){
        return reservaRepository.findAll();
    }

    public Reserva findById(Integer id) {
        return reservaRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoudException());
    }

    public ReservaDTO getHorarios(String localName, String tratamientoName) {
        Tratamiento tratamientoSeleccionado = tratamientoRepository.findByNombre(tratamientoName);
        Local localSeleccionado = localRepository.findByNombre(localName);

        if (tratamientoSeleccionado == null || localSeleccionado == null) {
            return new ReservaDTO(localName, tratamientoName, Collections.emptyList(), null);
        }

        List<DayOfWeek> diasDisponibles = localSeleccionado.getDiasDisponibles();
        List<String> horariosDisponibles = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        int duracion = tratamientoSeleccionado.getDuracion();

        LocalDate currentDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusMonths(3);

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

        return new ReservaDTO(localName, tratamientoName, horariosDisponibles, null);
    }


    private boolean isDateTimeValidForLocal(Local local, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return local.getDiasDisponibles().contains(dayOfWeek);
    }


    @Transactional
    public Reserva create(ReservaDTO reservaDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(reservaDTO.getFechaHora().get(0), formatter);

        Local local = localRepository.findByNombre(reservaDTO.getLocal());
        Tratamiento tratamiento = tratamientoRepository.findByNombre(reservaDTO.getTratamiento());
        Patient patient = patientRepository.findById(reservaDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (!isDateTimeValidForLocal(local, dateTime)) {
            throw new RuntimeException("La fecha y hora no son válidas para los días disponibles del local");
        }

        Reserva reserva = new Reserva();
        reserva.setLocal(reservaDTO.getLocal());
        reserva.setTratamiento(reservaDTO.getTratamiento());
        reserva.setFechaHora(dateTime);
        reserva.setPatient(patient);

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva update(Long id, ReservaDTO reservaDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(reservaDTO.getFechaHora().get(0), formatter);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Local local = localRepository.findByNombre(reservaDTO.getLocal());
        Tratamiento tratamiento = tratamientoRepository.findByNombre(reservaDTO.getTratamiento());
        Patient patient = patientRepository.findById(reservaDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (!isDateTimeValidForLocal(local, dateTime)) {
            throw new RuntimeException("La fecha y hora no son válidas para los días disponibles del local");
        }

        reserva.setLocal(reservaDTO.getLocal());
        reserva.setTratamiento(reservaDTO.getTratamiento());
        reserva.setFechaHora(dateTime);
        reserva.setPatient(patient);

        return reservaRepository.save(reserva);
    }

    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reservaRepository.delete(reserva);
    }


}
