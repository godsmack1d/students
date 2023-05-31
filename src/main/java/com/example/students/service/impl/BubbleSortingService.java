package com.example.students.service.impl;

import com.example.students.domain.SortType;
import com.example.students.domain.Student;
import com.example.students.service.SortingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.students.domain.SortType.BUBBLE;
import static java.util.Collections.swap;

@Service
public class BubbleSortingService implements SortingService {

    @Override
    public List<Student> sortStudents(List<Student> students) {

        var sortedStudents = new ArrayList<>(students);

        for (int i = 0; i < sortedStudents.size(); i++) {
            for (int j = 0; j < sortedStudents.size() - i - 1; j++) {
                if (sortedStudents.get(j).compareTo(sortedStudents.get(j + 1)) > 0) {
                    swap(sortedStudents, j, j + 1);
                }
            }
        }

        return sortedStudents;
    }

    @Override
    public SortType getSortType() {
        return BUBBLE;
    }
}
