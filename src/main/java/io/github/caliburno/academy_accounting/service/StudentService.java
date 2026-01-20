package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.FamilyGroup;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Student> findByFamilyGroup(FamilyGroup familyGroup) {
        return studentRepository.findByFamilyGroup(familyGroup);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        Student student = findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        if (student.getEnrollmentList() != null && !student.getEnrollmentList().isEmpty()) {
            throw new RuntimeException("No se puede eliminar un alumno con inscripciones activas");
        }
        studentRepository.deleteById(id);
    }
}
