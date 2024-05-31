//package com.vao.agenda.config;
//
//import com.vao.agenda.entity.Local;
//import com.vao.agenda.entity.Tratamiento;
//import com.vao.agenda.repository.LocalRepository;
//import com.vao.agenda.repository.TratamientoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
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
//}
