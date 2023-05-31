package com.example.students.controller;

import com.example.students.domain.SortType;
import com.example.students.domain.Students;
import com.example.students.service.FileService;
import com.example.students.usecase.SortingUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final SortingUsecase sortingUsecase;
    private final FileService fileService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sortTypes", SortType.values());
        return "index";
    }

    @PostMapping("/sort")
    public ModelAndView sortStudents(
            @RequestParam("file") MultipartFile file,
            @RequestParam SortType sortType
    ) {
        var modelAndView = new ModelAndView("students");
        var students = fileService.read(file);
        long startTime = System.currentTimeMillis();
        var sortedStudents = sortingUsecase.sort(sortType, students);
        long endTime = System.currentTimeMillis();
        var sortingTime = endTime - startTime;
        modelAndView.addObject("students", sortedStudents);
        modelAndView.addObject("sortingTime", sortingTime);
        return modelAndView;
    }

    @PostMapping("/download")
    public ResponseEntity<Resource> downloadStudents(@ModelAttribute Students students) {
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=sortedStudents.txt")
                .contentType(TEXT_PLAIN)
                .body(fileService.download(students.getStudents()));
    }


}
