package com.vao.agenda.service;

import com.vao.agenda.dto.BookingDTO;
import com.vao.agenda.entity.Place;
import com.vao.agenda.entity.Patient;
import com.vao.agenda.entity.Booking;
import com.vao.agenda.entity.Treatment;
import com.vao.agenda.exception.InvalidDateException;
import com.vao.agenda.exception.OutOfTimeRangeException;
import com.vao.agenda.exception.NotFoundException;
import com.vao.agenda.repository.IPatientRepository;
import com.vao.agenda.repository.IPlaceRepository;
import com.vao.agenda.repository.IBookingRepository;
import com.vao.agenda.repository.ITreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
    }

    public BookingDTO getSchedules(String placeName, String treatmentName) {
        Treatment treatmentSelected = iTreatmentRepository.findByName(treatmentName);
        if (treatmentSelected == null) {
            throw new NotFoundException("Tratamiento no encontrado");
        }
        Place placeSelected = iPlaceRepository.findByName(placeName);
        if (placeSelected == null) {
            throw new NotFoundException("Lugar no encontrado");
        }

//        if (treatmentSelected == null || placeSelected == null) {
//            return new BookingDTO(placeName, treatmentName, Collections.emptyList(), null);
//        }

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

        return new BookingDTO(placeName, treatmentName, availableSchedules, null);
    }


    // Verifica si el dia es valido para el local
    private boolean isDateTimeValidForLocal(Place place, LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return place.getAvailableDays().contains(dayOfWeek);
    }

    // Verifica que el rango de hora y fecha este permitido
    private boolean isWithinValidTimeRange(LocalDateTime dateTime) {
        LocalDate currentDate = LocalDate.now().plusDays(1); // desde el día de consulta + 1 (desde mañana)
        LocalDate endDate = LocalDate.now().plusMonths(3); // desde el día de consulta hasta 3 meses
        LocalTime startTime = LocalTime.of(9, 0); // horario de inicio
        LocalTime endTime = LocalTime.of(19, 0); // horario de fin

        return (dateTime.toLocalDate().isAfter(currentDate.minusDays(1)) && dateTime.toLocalDate().isBefore(endDate.plusDays(1))) &&
                (dateTime.toLocalTime().isAfter(startTime.minusSeconds(1)) && dateTime.toLocalTime().isBefore(endTime.plusSeconds(1)));
    }


    @Transactional
    public Booking create(BookingDTO bookingDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(bookingDTO.getDateHour().get(0), formatter);

//        Place place = iPlaceRepository.findByName(bookingDTO.getPlace());
//        Treatment treatment = iTreatmentRepository.findByName(bookingDTO.getTreatment());
        Place place = iPlaceRepository.findByName(bookingDTO.getPlace());
        if (place == null) {
            throw new NotFoundException("Lugar no encontrado");
        }

        Treatment treatment = iTreatmentRepository.findByName(bookingDTO.getTreatment());
        if (treatment == null) {
            throw new NotFoundException("Tipo de Tratamiento no encontrado");
        }

        Patient patient = iPatientRepository.findById(bookingDTO.getPatientId())
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        if (!isDateTimeValidForLocal(place, dateTime)) {
            throw new InvalidDateException("La fecha no es valida para los días disponibles del local");
        }

        if (!isWithinValidTimeRange(dateTime)) {
            throw new OutOfTimeRangeException("La fecha y hora están fuera del rango permitido");
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
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));

        Place place = iPlaceRepository.findByName(bookingDTO.getPlace());
        if (place == null) {
            throw new NotFoundException("Lugar no encontrado");
        }

        Treatment treatment = iTreatmentRepository.findByName(bookingDTO.getTreatment());
        if (treatment == null) {
            throw new NotFoundException("Tipo de Tratamiento no encontrado");
        }

        Patient patient = iPatientRepository.findById(bookingDTO.getPatientId())
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        if (!isDateTimeValidForLocal(place, dateTime)) {
            throw new InvalidDateException("La fecha no es válida para los días disponibles del local");
        }

        if (!isWithinValidTimeRange(dateTime)) {
            throw new OutOfTimeRangeException("La fecha y hora están fuera del rango permitido");
        }

        booking.setPlace(bookingDTO.getPlace());
        booking.setTreatment(bookingDTO.getTreatment());
        booking.setDateHour(dateTime);
        booking.setPatient(patient);

        return iBookingRepository.save(booking);
    }

    public void delete(Long id) {
        Booking booking = iBookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
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
