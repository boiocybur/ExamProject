package com.example.examproject.repository;

import com.example.examproject.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@ActiveProfiles("h2")
class ProjectListRepositoryTest {
    @Autowired
    ProjectListRepository projectListRepository;
}