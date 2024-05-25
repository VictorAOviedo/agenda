package com.vao.agenda.service;

import com.vao.agenda.dto.HorariosDTO;
import com.vao.agenda.entity.Local;
import com.vao.agenda.entity.Tratamiento;
import com.vao.agenda.repository.LocalRepository;
import com.vao.agenda.repository.TratamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class ReservasService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private LocalRepository localRepository;


    public List<Tratamiento> getTratamientos() {
        return tratamientoRepository.findAll();
    }

    public List<Local> getLocales() {
        return localRepository.findAll();
    }

    public HorariosDTO getHorarios(String localName, String tratamientoName) {
        Tratamiento tratamientoSeleccionado = tratamientoRepository.findAll().stream()
                .filter(t -> t.getNombre().equals(tratamientoName))
                .findFirst().orElse(null);

        Local localSeleccionado = localRepository.findAll().stream()
                .filter(l -> l.getNombre().equals(localName))
                .findFirst().orElse(null);

        if (tratamientoSeleccionado == null || localSeleccionado == null) {
            return new HorariosDTO(localName, tratamientoName, Collections.emptyList());
        }

        List<String> horariosDisponibles = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Empezar desde mañana

        Calendar fin = Calendar.getInstance();
        fin.add(Calendar.MONTH, 3); // Hasta tres meses desde hoy

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        //new
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");

        int duracion = tratamientoSeleccionado.getDuracion(); // Duración en minutos


        while (calendar.before(fin)) {
            if (localSeleccionado.getDiasDisponibles().contains(calendar.get(Calendar.DAY_OF_WEEK))) {
                String fecha = sdf.format(calendar.getTime());
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

        return new HorariosDTO(localName, tratamientoName, horariosDisponibles);
    }
}
