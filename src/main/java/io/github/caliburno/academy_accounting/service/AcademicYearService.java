package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.AcademicYear;
import io.github.caliburno.academy_accounting.repository.AcademicYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicYearService {

    @Autowired
    private AcademicYearRepository repository;

    public List<AcademicYear> findAll() {
        return repository.findAll();
    }


}
