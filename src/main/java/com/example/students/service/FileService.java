package com.example.students.service;

import com.example.students.domain.Student;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<Student> read(MultipartFile file);

    InputStreamResource download(List<Student> students);

}
