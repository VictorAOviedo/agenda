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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        List<String> horariosDisponibles = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Empezar desde mañana

        Calendar fin = Calendar.getInstance();
        fin.add(Calendar.MONTH, 3); // Hasta tres meses desde hoy

        //SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");

        int duracion = tratamientoSeleccionado.getDuracion(); // Duración en minutos


        while (calendar.before(fin)) {
            if (localSeleccionado.getDiasDisponibles().contains(calendar.get(Calendar.DAY_OF_WEEK))) {
                //String fecha = sdf.format(calendar.getTime());
                LocalDateTime startTime = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).withHour(9).withMinute(0);
                LocalDateTime endTime = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).withHour(19).withMinute(0);
                //horariosDisponibles.add(fecha);

                while (startTime.plusMinutes(duracion).isBefore(endTime)) {
                    horariosDisponibles.add(startTime.format(dateTimeFormatter));
                    startTime = startTime.plusMinutes(duracion);
                }
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return new ReservaDTO(localName, tratamientoName, horariosDisponibles);
    }

    public Reserva createReserva(String local, String tratamiento, String fechaHora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(fechaHora, formatter);

        Reserva reserva = new Reserva();
        reserva.setLocal(local);
        reserva.setTratamiento(tratamiento);
        reserva.setFechaHora(dateTime);

        return reservaRepository.save(reserva);
    }


    public Reserva update(Long id, String local, String tratamiento, String fechaHora) {
        Optional<Reserva> optionalReserva = reservaRepository.findById(id);

        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            reserva.setLocal(local);
            reserva.setTratamiento(tratamiento);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(fechaHora, formatter);
            reserva.setFechaHora(dateTime);

            return reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Reserva no encontrada");
        }
    }


    public void delete (Integer id){
        Reserva reservafromDB = reservaRepository
                .findById(Long.valueOf(id))
                .orElse(null);

        reservaRepository.delete(reservafromDB);
    }

}
