package pl.edu.pbs.ipodloga.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/studenty")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> pobierzWszystkichStudentow() {
        return studentService.pobierzWszystkichStudentow();
    }
    @PostMapping
    public ResponseEntity<String> dodajStudenta(@RequestBody Student student) {
        try {
            System.out.println(student.getNazwisko());
            String studentId = studentService.dodajStudenta(student);
            return new ResponseEntity<>(studentId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
