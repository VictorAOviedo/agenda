package com.vao.agenda.config;


import com.vao.agenda.entity.Booking;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.entity.Place;
import com.vao.agenda.entity.Treatment;
import com.vao.agenda.repository.IBookingRepository;
import com.vao.agenda.repository.IPatientRepository;
import com.vao.agenda.repository.IPlaceRepository;
import com.vao.agenda.repository.ITreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Arrays;


//@Component
//public class DataLoader implements CommandLineRunner {
//
//
//
//}



//    @Autowired
//    private TratamientoRepository tratamientoRepository;
//
//    @Autowired
//    private LocalRepository localRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Insertar tratamientos
//        Tratamiento consulta = new Tratamiento("consulta", 15);
//        Tratamiento extraccion = new Tratamiento("extraccion", 60);
//        Tratamiento ortodoncia = new Tratamiento("ortodoncia", 30);
//
//        tratamientoRepository.saveAll(Arrays.asList(consulta, extraccion, ortodoncia));
//
//        // Insertar locales
//        Local local1 = new Local("LOCAL 1", Arrays.asList(1, 4)); // Martes y Viernes
//        Local local2 = new Local("LOCAL 2", Arrays.asList(2, 3)); // Miércoles y Jueves
//
//        localRepository.saveAll(Arrays.asList(local1, local2));
//    }

