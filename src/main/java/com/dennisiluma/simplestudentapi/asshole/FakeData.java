package com.dennisiluma.simplestudentapi.asshole;

import com.dennisiluma.simplestudentapi.model.Student;
import com.dennisiluma.simplestudentapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class FakeData {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student dennis = new Student("minimi", "dennis@gmail.com", LocalDate.of(1994, Month.DECEMBER, 4));
            Student alex = new Student("hello", "alex@gmail.com", LocalDate.of(1934, Month.DECEMBER, 5));
            Student mum = new Student("who am i", "myu@gmail.com", LocalDate.of(1998, Month.DECEMBER, 13));
            studentRepository.saveAll(List.of(dennis, alex, mum));
        };
    }
}
