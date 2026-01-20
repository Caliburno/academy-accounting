package io.github.caliburno.academy_accounting.service;

import io.github.caliburno.academy_accounting.model.FamilyGroup;
import io.github.caliburno.academy_accounting.repository.FamilyGroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FamilyGroupService {

    @Autowired
    private FamilyGroupRepository familyGroupRepository;

    public List<FamilyGroup> findAll() {
        return familyGroupRepository.findAll();
    }

    public Optional<FamilyGroup> findById(Long id) {
        return familyGroupRepository.findById(id);
    }

    public FamilyGroup save(FamilyGroup familyGroup) {
        if (familyGroup.getDiscountPercentage() == null) {
            familyGroup.setDiscountPercentage(new BigDecimal("10.0"));
        }
        return familyGroupRepository.save(familyGroup);
    }

    public void deleteById(Long id) {
        familyGroupRepository.deleteById(id);
    }
}
