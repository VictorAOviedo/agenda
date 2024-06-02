package com.vao.agenda.service;

import com.vao.agenda.dto.BookingDTO;
import com.vao.agenda.entity.Place;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.entity.Booking;
import com.vao.agenda.entity.Treatment;
import com.vao.agenda.exception.ResourceNotFoudException;
import com.vao.agenda.repository.IPatientRepository;
import com.vao.agenda.repository.IPlaceRepository;
import com.vao.agenda.repository.IBookingRepository;
import com.vao.agenda.repository.ITreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private ITreatmentRepository iTreatmentRepository;

    @Autowired
    private IPlaceRepository iPlaceRepository;

    @Autowired
    private IBookingRepository iBookingRepository;

    @Autowired
    private IPatientRepository iPatientRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");



    public Iterable<Booking> findAll(){
        return iBookingRepository.findAll();
    }

    public Booking findById(Integer id) {
        return iBookingRepository
                .findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoudException());
    }

    public BookingDTO getSchedules(String localName, String treatmentName) {
        Treatment treatmentSelected = iTreatmentRepository.findByName(treatmentName);
        Place placeSelected = iPlaceRepository.findByName(localName);

        if (treatmentSelected == null || placeSelected == null) {
            return new BookingDTO(localName, treatmentName, Collections.emptyList(), null);
        }

        List<DayOfWeek> availableDays = placeSelected.getAvailableDays();
        List<String> availableSchedules = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        int duration = treatmentSelected.getDuration();

        LocalDate currentDate = LocalDate.now().plusDays(1); // desde dia de consulta + 1 (desde mañana)
        LocalDate endDate = LocalDate.now().plusMonths(3); // desde dia consulta hasta 3 meses

        while (!currentDate.isAfter(endDate)) {
            if (availableDays.contains(currentDate.getDayOfWeek())) {
                LocalDateTime startTime = currentDate.atTime(9, 0); // horarios desde las 9hs
                LocalDateTime endTime = currentDate.atTime(19, 0); // horario hasta las 19hs

                while (startTime.plusMinutes(duration).isBefore(endTime)) {
                    availableSchedules.add(startTime.format(dateTimeFormatter));
                    startTime = startTime.plusMinutes(duration);
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return new BookingDTO(localName, treatmentName, availableSchedules, null);
    }


    // Verifica si el dia es valido para el local
    private boolean isDateTimeValidForLocal(Place place, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return place.getAvailableDays().contains(dayOfWeek);
    }


    @Transactional
    public Booking create(BookingDTO bookingDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(bookingDTO.getDateHour().get(0), formatter);

        Place place = iPlaceRepository.findByName(bookingDTO.getPlace());
        Treatment treatment = iTreatmentRepository.findByName(bookingDTO.getTreatment());
        Patient patient = iPatientRepository.findById(bookingDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (!isDateTimeValidForLocal(place, dateTime)) {
            throw new RuntimeException("La fecha no es valida para los días disponibles del local");
        }

        BookingDTO schedules = getSchedules(bookingDTO.getPlace(), bookingDTO.getTreatment());
        if (!schedules.getDateHour().contains(bookingDTO.getDateHour().get(0))) {
            throw new RuntimeException("El horario no está disponible para la reserva.");
        }

        Booking booking = new Booking();
        booking.setPlace(bookingDTO.getPlace());
        booking.setTreatment(bookingDTO.getTreatment());
        booking.setDateHour(dateTime);
        booking.setPatient(patient);

        return iBookingRepository.save(booking);
    }

    @Transactional
    public Booking update(Long id, BookingDTO bookingDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(bookingDTO.getDateHour().get(0), formatter);

        Booking booking = iBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Place place = iPlaceRepository.findByName(bookingDTO.getPlace());
        Treatment treatment = iTreatmentRepository.findByName(bookingDTO.getTreatment());
        Patient patient = iPatientRepository.findById(bookingDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        if (!isDateTimeValidForLocal(place, dateTime)) {
            throw new RuntimeException("La fecha no es válida para los días disponibles del local");
        }

        BookingDTO schedules = getSchedules(bookingDTO.getPlace(), bookingDTO.getTreatment());
        if (!schedules.getDateHour().contains(bookingDTO.getDateHour().get(0))) {
            throw new RuntimeException("El horario no está disponible para la reserva.");
        }

        booking.setPlace(bookingDTO.getPlace());
        booking.setTreatment(bookingDTO.getTreatment());
        booking.setDateHour(dateTime);
        booking.setPatient(patient);

        return iBookingRepository.save(booking);
    }

    public void delete(Long id) {
        Booking booking = iBookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        iBookingRepository.delete(booking);
    }

    public void exportBookingToFile(LocalDate date, String filePath) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(LocalTime.MAX);
        List<Booking> bookings = iBookingRepository.findByDateHourBetween(startDateTime, endDateTime);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (Booking booking : bookings) {
                writer.write(booking.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
