package io.github.caliburno.academy_accounting.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "family_groups")
public class FamilyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false)
    private BigDecimal discountPercentage;

    @OneToMany(mappedBy = "familyGroup")
    private List<Student> students = new ArrayList<>();

}
