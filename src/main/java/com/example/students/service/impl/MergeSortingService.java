package com.example.students.service.impl;

import com.example.students.domain.SortType;
import com.example.students.domain.Student;
import com.example.students.service.SortingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.students.domain.SortType.MERGE;

@Service
public class MergeSortingService implements SortingService {

    @Override
    public List<Student> sortStudents(List<Student> students) {
        var sortedStudents = new ArrayList<>(students);
        mergeSort(sortedStudents, 0, sortedStudents.size() - 1);

        return sortedStudents;
    }

    private void mergeSort(List<Student> students, int left, int right) {
        if (left < right) {
            var mid = (left + right) / 2;

            mergeSort(students, left, mid);
            mergeSort(students, mid + 1, right);

            merge(students, left, mid, right);
        }
    }

    private void merge(List<Student> students, int left, int mid, int right) {
        var leftList = new ArrayList<>(students.subList(left, mid + 1));
        var rightList = new ArrayList<>(students.subList(mid + 1, right + 1));

        var i = 0;
        var j = 0;

        var k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getScore() <= rightList.get(j).getScore()) {
                students.set(k++, leftList.get(i++));
            } else {
                students.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) {
            students.set(k++, leftList.get(i++));
        }

        while (j < rightList.size()) {
            students.set(k++, rightList.get(j++));
        }
    }

    @Override
    public SortType getSortType() {
        return MERGE;
    }
}
