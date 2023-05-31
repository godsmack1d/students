package com.example.students.service;

import com.example.students.domain.SortType;
import com.example.students.domain.Student;

import java.util.List;

public interface SortingService {
    List<Student> sortStudents(List<Student> students);

    SortType getSortType();

}

