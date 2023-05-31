package com.example.students.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Double.compare;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Comparable<Student> {

    private String name;
    private Double score;

    @Override
    public int compareTo(Student other) {
        return compare(this.score, other.score);
    }
}

