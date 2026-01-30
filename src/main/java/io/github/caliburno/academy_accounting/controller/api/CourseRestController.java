package io.github.caliburno.academy_accounting.controller.api;

import io.github.caliburno.academy_accounting.dto.CourseDTO;
import io.github.caliburno.academy_accounting.model.Course;
import io.github.caliburno.academy_accounting.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {

        List<Course> courses = courseService.findAll();
        List<CourseDTO> dtos = courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {

        Course course = courseService.findById(id)
                .orElseThrow(() -> new RuntimeException("No course with that id"));
        return ResponseEntity.ok(convertToDTO(course));

    }

    @GetMapping("/active-year")
    public ResponseEntity<List<CourseDTO>> getCoursesByActiveYear() {
        List<Course> courses = courseService.findActiveYearCourse();
        List<CourseDTO> dtos = courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course saved = courseService.save(course);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertToDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        course.setId(id);
        Course updated = courseService.save(course);
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CourseDTO convertToDTO(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .level(course.getLevel())
                .basePrice(course.getBasePrice())
                .academicYearId(course.getAcademicYear() != null ? course.getAcademicYear().getId() : null)
                .academicYear(course.getAcademicYear() != null ? course.getAcademicYear().getYear() : null)
                .build();
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setLevel(courseDTO.getLevel());
        course.setBasePrice(courseDTO.getBasePrice());

        return course;
    }


}
