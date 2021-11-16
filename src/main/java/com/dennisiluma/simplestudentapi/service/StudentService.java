package com.dennisiluma.simplestudentapi.service;

import com.dennisiluma.simplestudentapi.model.Student;
import com.dennisiluma.simplestudentapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        //return studentRepository.getStudents();
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC));
    }

    public void addStudent(Student student) {
        Optional<Student> findStudentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if (findStudentByEmail.isPresent()) {
            throw new IllegalStateException("Student Already Exist");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exist = studentRepository.existsById(studentId);
        if (!exist) {
            throw new IllegalStateException("Student with id" + studentId + " Doesn't Exist");
        } else {
            studentRepository.deleteById(studentId);
        }
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("Student with id" + studentId + " Doesn't Exist"));
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken"); //i.e if the email is already in the database
            }
            student.setEmail(email);
        }
    }

}
