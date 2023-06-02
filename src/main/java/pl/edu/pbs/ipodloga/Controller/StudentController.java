package pl.edu.pbs.ipodloga.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pbs.ipodloga.Model.Student;
import pl.edu.pbs.ipodloga.Model.StudentProjekt;
import pl.edu.pbs.ipodloga.Model.StudentZadanie;
import pl.edu.pbs.ipodloga.Service.StudentProjektService;
import pl.edu.pbs.ipodloga.Service.StudentService;
import pl.edu.pbs.ipodloga.Service.StudentZadanieService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentProjektService studentProjektService;
    private final StudentZadanieService studentZadanieService;

    @Autowired
    public StudentController(StudentService studentService, StudentProjektService studentProjektService, StudentZadanieService studentZadanieService) {
        this.studentProjektService = studentProjektService;
        this.studentZadanieService = studentZadanieService;
        this.studentService = studentService;
    }


    @GetMapping
    public ResponseEntity<List<Student>> pobierzWszystkichStudentow() {
        List<Student> studenty = studentService.pobierzWszystkichStudentow();
        return new ResponseEntity<>(studenty, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> dodajStudenta(@RequestBody Student student) {
        try {
            String id = studentService.dodajStudenta(student);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{studentId}/projekty/{projektId}")
    public ResponseEntity<String> przypiszProjekt(@PathVariable String studentId, @PathVariable String projektId) {
        try {
            StudentProjekt studentProjekt = new StudentProjekt();
            studentProjekt.setStudentId(studentId);
            studentProjekt.setProjektId(projektId);
            studentProjektService.przypiszProjektDoStudenta(studentProjekt);
            return new ResponseEntity<>("Projekt został przypisany do studenta", HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{studentId}/zadania/{zadanieId}")
    public ResponseEntity<String> przypiszZadanie(@PathVariable String studentId, @PathVariable String zadanieId) {
        try {
            StudentZadanie studentZadanie = new StudentZadanie();
            studentZadanie.setStudentId(studentId);
            studentZadanie.setZadanieId(zadanieId);
            studentZadanieService.przypiszZadanieDoStudenta(studentZadanie);
            return new ResponseEntity<>("Zadanie zostało przypisane do studenta", HttpStatus.OK);
        } catch (InterruptedException | ExecutionException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

