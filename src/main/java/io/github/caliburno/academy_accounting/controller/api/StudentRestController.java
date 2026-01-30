package io.github.caliburno.academy_accounting.controller.api;

import io.github.caliburno.academy_accounting.dto.StudentDTO;
import io.github.caliburno.academy_accounting.model.FamilyGroup;
import io.github.caliburno.academy_accounting.model.Student;
import io.github.caliburno.academy_accounting.service.FamilyGroupService;
import io.github.caliburno.academy_accounting.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
public class StudentRestController {

    private final StudentService studentService;
    private final FamilyGroupService familyGroupService;

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {

        List<Student> students = studentService.findAll();
        List<StudentDTO> dtos = students.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return ResponseEntity.ok(convertToDTO(student));

    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> searchStudents(@RequestParam String name) {
        List<Student> students = studentService.searchByName(name);
        List<StudentDTO> dtos = students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        if (studentDTO.getFamilyGroupId() != null) {
            FamilyGroup familyGroup = familyGroupService.findById(studentDTO.getFamilyGroupId())
                    .orElseThrow(() -> new RuntimeException("Family group not found"));
            student.setFamilyGroup(familyGroup);
        }

        Student saved = studentService.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {

        Student student = convertToEntity(studentDTO);
        student.setId(id);

        if (studentDTO.getFamilyGroupId() != null) {
            FamilyGroup familyGroup = familyGroupService.findById(studentDTO.getFamilyGroupId())
                    .orElseThrow(() -> new RuntimeException("Family group not found"));
            student.setFamilyGroup(familyGroup);
        }

        Student updated = studentService.save(student);
        return ResponseEntity.ok(convertToDTO(updated));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    private StudentDTO convertToDTO(Student student) {
        return StudentDTO.builder().id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .referentAdult(student.getReferentAdult())
                .dateOfBirth(student.getDateOfBirth())
                .familyGroupId(student.getFamilyGroup() != null ? student.getFamilyGroup().getId() : null)
                .familyGroupName(student.getFamilyGroup() != null ? student.getFamilyGroup().getName() : null)
                .build();

    }

    private Student convertToEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setPhone(dto.getPhone());
        student.setReferentAdult(dto.getReferentAdult());
        student.setDateOfBirth(dto.getDateOfBirth());
        return student;

    }
}