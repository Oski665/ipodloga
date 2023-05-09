package pl.edu.pbs.ipodloga.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pbs.ipodloga.Model.Projekt;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Service.ProjectService;
import pl.edu.pbs.ipodloga.Service.StudentService;

import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/studenty")
    public List<Student> pobierzWszystkichStudentow() {
        return studentService.pobierzWszystkichStudentow();
    }
}
