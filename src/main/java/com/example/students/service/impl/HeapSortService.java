package com.example.students.service.impl;

import com.example.students.domain.SortType;
import com.example.students.domain.Student;
import com.example.students.service.SortingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.students.domain.SortType.HEAP;

@Service
public class HeapSortService implements SortingService {

    @Override
    public List<Student> sortStudents(List<Student> students) {

        var sortedStudents = new ArrayList<>(students);

        for (int i = sortedStudents.size() / 2 - 1; i >= 0; i--) {
            heapify(sortedStudents, sortedStudents.size(), i);
        }

        for (int i = sortedStudents.size() - 1; i > 0; i--) {
            var temp = sortedStudents.get(0);
            sortedStudents.set(0, sortedStudents.get(i));
            sortedStudents.set(i, temp);

            heapify(sortedStudents, i, 0);
        }

        return sortedStudents;
    }

    private void heapify(List<Student> students, int heapSize, int rootIndex) {
        var largest = rootIndex;
        var leftChild = 2 * rootIndex + 1;
        var rightChild = 2 * rootIndex + 2;

        if (leftChild < heapSize && students.get(leftChild).getScore() > students.get(largest).getScore()) {
            largest = leftChild;
        }

        if (rightChild < heapSize && students.get(rightChild).getScore() > students.get(largest).getScore()) {
            largest = rightChild;
        }

        if (largest != rootIndex) {
            Student swap = students.get(rootIndex);
            students.set(rootIndex, students.get(largest));
            students.set(largest, swap);

            heapify(students, heapSize, largest);
        }
    }

    @Override
    public SortType getSortType() {
        return HEAP;
    }
}
