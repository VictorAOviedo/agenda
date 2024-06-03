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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ITreatmentRepository iTreatmentRepository;

    @Autowired
    private IPlaceRepository iPlaceRepository;

    @Autowired
    private IPatientRepository iPatientRepository;

    @Autowired
    private IBookingRepository iBookingRepository;

    @Override
    public void run(String... args) throws Exception {
        loadTreatmentData();
        loadPlaceData();
        loadPatientData();
        loadBookingData();
    }

    private void loadTreatmentData() {
        if (iTreatmentRepository.count() == 0) {
            Treatment consulta = new Treatment("consulta", 10);
            Treatment cirujia = new Treatment("cirujia", 60);
            Treatment ortodoncia = new Treatment("ortodoncia", 30);

            iTreatmentRepository.saveAll(Arrays.asList(consulta, cirujia, ortodoncia));
        }
    }

    private void loadPlaceData() {
        if (iPlaceRepository.count() == 0) {
            Place local1 = new Place("LOCAL 1", Arrays.asList(DayOfWeek.MONDAY));
            Place local2 = new Place("LOCAL 2", Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY));
            Place local3 = new Place("LOCAL 3", Arrays.asList(DayOfWeek.WEDNESDAY));

            iPlaceRepository.saveAll(Arrays.asList(local1, local2, local3));
        }
    }

    private void loadPatientData() {
        if (iPatientRepository.count() == 0) {
            Patient patient1 = new Patient("Victor", "Oviedo", 351, 6715554, "victor@gmail.com");
            Patient patient2 = new Patient("Cecilia", "Janco", 11, 5555678, "cecilia@gmail.com");
            Patient patient3 = new Patient("Denise", "Elias", 388, 5559101, "denise@gmail.com");

            iPatientRepository.saveAll(Arrays.asList(patient1, patient2, patient3));
        }
    }

    private void loadBookingData() {
        if (iBookingRepository.count() == 0) {
            // Obtener las entidades requeridas
            Treatment consulta = iTreatmentRepository.findByName("consulta");
            Treatment cirujia = iTreatmentRepository.findByName("cirujia");
            Treatment ortodoncia = iTreatmentRepository.findByName("ortodoncia");

            Place local1 = iPlaceRepository.findByName("LOCAL 1");
            Place local2 = iPlaceRepository.findByName("LOCAL 2");

            // Convertir Iterable<Patient> a List<Patient>
            List<Patient> patients = StreamSupport
                    .stream(iPatientRepository.findAll().spliterator(), false)
                    .collect(Collectors.toList());


            // Crear bookings
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
            LocalDateTime dateTime1 = LocalDateTime.parse("lunes, 2024-06-24 12:00", formatter);
            LocalDateTime dateTime2 = LocalDateTime.parse("lunes, 2024-07-15 10:00", formatter);
            LocalDateTime dateTime3 = LocalDateTime.parse("martes, 2024-06-25 11:00", formatter);

            Booking booking1 = new Booking(local1.getName(), consulta.getName(), dateTime1, patients.get(0));
            Booking booking2 = new Booking(local1.getName(), cirujia.getName(), dateTime2, patients.get(1));
            Booking booking3 = new Booking(local2.getName(), ortodoncia.getName(), dateTime3, patients.get(2));

            // Guardar bookings
            iBookingRepository.saveAll(Arrays.asList(booking1, booking2, booking3));
        }
    }

}


