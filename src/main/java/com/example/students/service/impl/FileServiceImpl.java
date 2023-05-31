package com.example.students.service.impl;

import com.example.students.domain.Student;
import com.example.students.exception.FileParsingException;
import com.example.students.exception.FileProcessingException;
import com.example.students.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public List<Student> read(MultipartFile file) {

        Path tempFile = null;

        try {
            List<Student> students = new ArrayList<>();

            tempFile = Files.createTempFile("students", ".txt");
            Files.write(tempFile, file.getBytes());
            read(students, tempFile.toString());

            return students;
        } catch (IOException e) {
            log.error("Something went wrong when processing input file", e);
            throw new FileProcessingException(e);
        } finally {
            deleteTempFile(tempFile);
        }
    }

    private void read(List<Student> students, String path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Path.of(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    double score;
                    try {
                        score = Double.parseDouble(parts[1].trim());
                    } catch (NumberFormatException e) {
                        log.error("Invalid score format for student {}, skipping", name, e);
                        throw new FileParsingException(e);
                    }

                    students.add(new Student(name, score));
                } else {
                    log.error("Invalid line format: {}", line);
                    throw new FileParsingException("Invalid line format: " + line);
                }
            }
        }
    }

    private void deleteTempFile(Path tempFile) {
        if (tempFile != null) {
            try {
                Files.delete(tempFile);
            } catch (IOException e) {
                log.error("Error deleting temp file", e);
            }
        }
    }

    @Override
    public InputStreamResource download(List<Student> students) {

        Path tempFile = null;

        try {
            tempFile = Files.createTempFile("sortedStudents", ".txt");

            write(tempFile.toString(), students);

            return new InputStreamResource(Files.newInputStream(tempFile));
        } catch (IOException e) {
            log.error("Something went wrong when trying to create download file", e);
            throw new FileProcessingException(e);
        } finally {
            deleteTempFile(tempFile);
        }
    }

    private void write(String path, List<Student> students) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(path), StandardOpenOption.CREATE)) {
            for (Student student : students) {
                bw.write(student.getName() + "," + student.getScore());
                bw.newLine();
            }
        }
    }
}
