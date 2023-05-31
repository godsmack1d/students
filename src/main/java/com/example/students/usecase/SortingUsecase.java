package com.example.students.usecase;

import com.example.students.domain.SortType;
import com.example.students.domain.Student;
import com.example.students.exception.InvalidSortTypeException;
import com.example.students.service.SortingService;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
public class SortingUsecase {

    private final EnumMap<SortType, SortingService> typeToSortingService;

    public SortingUsecase(List<SortingService> services) {
        this.typeToSortingService = new EnumMap<>(SortType.class);
        services.forEach(service -> typeToSortingService.put(service.getSortType(), service));
    }

    public List<Student> sort(SortType type, List<Student> students) {
        return ofNullable(type)
                .map(typeToSortingService::get)
                .map(sortingService -> sortingService.sortStudents(students))
                .orElseThrow(() ->
                        new InvalidSortTypeException(
                                format("There is no processor for provided sorting type: %s", type)));
    }

}
