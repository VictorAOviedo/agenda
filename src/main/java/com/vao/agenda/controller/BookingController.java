package com.vao.agenda.controller;


import com.vao.agenda.dto.BookingDTO;
import com.vao.agenda.entity.Booking;
import com.vao.agenda.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking")
    public Iterable<Booking> list(){
        return bookingService.findAll();
    }

    @GetMapping("/booking/{id}")
    public Booking get(@PathVariable Integer id) {
        return bookingService.findById(id);
    }


    @GetMapping("/schedules")
    public BookingDTO getSchedules(@RequestParam String place, @RequestParam String treatment) {
        return bookingService.getSchedules(place, treatment);
    }

    @PostMapping("/booking")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking create(@Validated @RequestBody BookingDTO bookingDTO) {
        return bookingService.create(bookingDTO);
    }

    @PutMapping("/booking/{id}")
    public Booking update(@PathVariable Long id, @Validated @RequestBody BookingDTO bookingDTO) {
        return bookingService.update(id, bookingDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("booking/{id}")
    public void delete(@PathVariable Long id) {
        bookingService.delete(id);
    }

    @GetMapping("/export-booking")
    public ResponseEntity<String> exportBooking(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        String filePath = "reservas_" + localDate.toString() + ".txt";
        bookingService.exportBookingToFile(localDate, filePath);

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return ResponseEntity.ok("Reservas exportada a " + filePath);
        } else {
            return ResponseEntity.status(500).body("Error al exportar reservas");
        }
    }

}
