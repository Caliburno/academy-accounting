package io.github.caliburno.academy_accounting.model;

import io.github.caliburno.academy_accounting.model.enums.CourseLevel;
import lombok.Getter;

@Getter
public class Course {
    private int id;
    private CourseLevel level;
    private float price;

    public Course(int id, CourseLevel level, AcademicYear year, float price) {
        this.id = id;
        this.level = level;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(CourseLevel level) {
        this.level = level;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
