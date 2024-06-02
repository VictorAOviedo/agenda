package com.vao.agenda;

import com.vao.agenda.entity.Patient;
import com.vao.agenda.repository.IPatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class AgendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaApplication.class, args);}


//	@Bean
//	CommandLineRunner runner(IPatientRepository iPatientRepository) {
//		return args -> {
//			List<Patient> patients = Arrays.asList(
//					new Patient("Victor", "Oviedo", 351, 351671555, "b@gmail.com"),
//					new Patient("Cecilia", "Janco", 11, 35167155, "a@gmail.com"),
//					new Patient("Victoria", "Oviedo", 388, 35167155, "anos@sasaasa.com")
//			);
//			iPatientRepository.saveAll(patients);
//		};
//
//
//	}
}
