package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.repository.AcademicYearRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AcademicYearService {

    @Autowired
    private AcademicYearRepository academicYearRepository;

    public List<AcademicYear> findAll() {
        return academicYearRepository.findAll();
    }

    public Optional<AcademicYear> findById(Long id) {
        return academicYearRepository.findById(id);
    }

    public AcademicYear getActiveYear() {
        return academicYearRepository.findByActiveTrue()
            .orElseThrow(() -> new RuntimeException("No hay año académico activo."));
    }

    public AcademicYear save(AcademicYear academicYear) {
        if (academicYear.getActive()) {
            academicYearRepository.findByActiveTrue().ifPresent(concurrentActive -> {
                concurrentActive.setActive(false);
                academicYearRepository.save(concurrentActive);
            });
        }
        return academicYearRepository.save(academicYear);
    }

    public AcademicYear activateYear(Long id) {
        AcademicYear year = findById(id).orElseThrow(() -> new RuntimeException("Year not found"));

        academicYearRepository.findByActiveTrue().ifPresent(current -> {
            current.setActive(false);
            academicYearRepository.save(current);
        });

        year.setActive(true);
        return academicYearRepository.save(year);
    }

    public void deleteById(Long id) {
        AcademicYear year = findById(id).orElseThrow(() -> new RuntimeException("Year not found"));

        if (year.getActive()) {
            throw new RuntimeException("You can't delete the active year");
        }

        academicYearRepository.deleteById(id);

    }

}
